package com.ecom.b2cstore.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.util.CheckoutUtil;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
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
            Map<String, Object> responseData = Map.of(
                    "clientSecret", paymentIntent.getClientSecret());
            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment intent");
        }
    }

    @GetMapping("/stripe-return")
    public String captureReturnResponse(@RequestParam("payment_intent") String paymentIntentId, Model model) {
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
            model.addAttribute("error", "Error processing payment. Please try again.");
            return "stripe/return";
        }
    }
}
