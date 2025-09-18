package com.ecom.b2cstore.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Order;
import com.ecom.b2cstore.util.CheckoutUtil;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentUpdateParams;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StripeController extends BaseController {

    @GetMapping("/paymentdemo")
    public String showDemo() {
        return "paymentDemo";
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Object> createPaymentIntent() {
        StripeClient client = new StripeClient(env.getProperty("stripe.api.secret"));
        Basket basket = getCurrentBasket();
        if (basket == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No active basket found."));
        }
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams
                    .builder()
                    .setAmount((long) (basket.getTotalPrice() * 100))
                    .setCurrency("usd")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build())
                    .build();

            PaymentIntent paymentIntent = client.paymentIntents().create(params);
            return ResponseEntity.ok(Map.of(
                    "clientSecret", paymentIntent.getClientSecret(),
                    "paymentIntentId", paymentIntent.getId()));
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment intent");
        }
    }

    @PostMapping("/update-payment-intent")
    public ResponseEntity<Object> updatePaymentIntent(@RequestBody Map<String, Object> requestBody) {
        StripeClient client = new StripeClient(env.getProperty("stripe.api.secret"));
        Basket basket = getCurrentBasket();
        if (basket == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No active basket found."));
        }

        try {
            // Retrieve the payment intent
            String paymentIntentId = (String) requestBody.get("paymentIntentId");

            if (paymentIntentId == null || paymentIntentId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Payment Intent ID is required."));
            }

            PaymentIntent paymentIntent = client.paymentIntents().retrieve(paymentIntentId);

            // Update the payment intent with shipping data from the basket
            PaymentIntentUpdateParams params = PaymentIntentUpdateParams
                    .builder()
                    .setShipping(
                            PaymentIntentUpdateParams.Shipping
                                    .builder()
                                    .setName(basket.getShipFirstName() + " " + basket.getShipLastName())
                                    .setAddress(
                                            PaymentIntentUpdateParams.Shipping.Address.builder()
                                                    .setLine1(basket.getAddress())
                                                    .setCity(basket.getCity())
                                                    .setState(basket.getState())
                                                    .setPostalCode(basket.getZipCode())
                                                    .setCountry(basket.getCountry())
                                                    .build())
                                    .build())
                    .build();

            PaymentIntent updatedPaymentIntent = paymentIntent.update(params);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "updatedPaymentIntentId", updatedPaymentIntent.getId()));
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error updating payment intent"));
        }
    }

    @GetMapping("/stripe-return")
    public String processStripeReturn(@RequestParam("payment_intent") String paymentIntentId, Model model) {
        StripeClient client = new StripeClient(env.getProperty("stripe.api.secret"));
        try {
            Basket basket = getCurrentBasket();

            if (basket == null) {
                return "redirect:/";
            }

            PaymentIntent paymentIntent = client.paymentIntents().retrieve(paymentIntentId);
            String status = paymentIntent.getStatus();

            if ("succeeded".equals(status) || "processing".equals(status)) {
                CheckoutUtil.PlaceOrderStatus placeOrderStatus = checkoutUtil.placeOrder(basket);

                if (!placeOrderStatus.isSuccess()) {
                    model.addAttribute("error", placeOrderStatus.getError());
                    return "stripe/return";
                }

                Order order = placeOrderStatus.getOrder();
                orderService.setPaid(order);

                return "redirect:" + placeOrderStatus.getRedirect();
            } else if ("requires_payment_method".equals(status)) {
                paymentIntent.cancel();
                model.addAttribute("error", "Payment failed. Please try again.");
            } else {
                paymentIntent.cancel();
                model.addAttribute("error", "Unexpected payment status: " + status);
            }
            return "stripe/return";
        } catch (StripeException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error processing payment. Please contact support.");
            return "stripe/return";
        }
    }
}
