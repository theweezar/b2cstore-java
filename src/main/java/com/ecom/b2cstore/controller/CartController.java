package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.BasketLineItem;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.model.CartModel;
import com.ecom.b2cstore.payload.AddToCartPayload;
import com.ecom.b2cstore.payload.AddToCartResponsePayload;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController extends BaseController {
    public CartController() {
        super();
    }

    @PostMapping("/addtocart")
    @ResponseBody
    public AddToCartResponsePayload addToCart(@RequestBody AddToCartPayload payload) {
        Product product = productService.getProductById(payload.getPid());
        AddToCartResponsePayload responsePayload = new AddToCartResponsePayload(false, Product.STATUS_NOT_FOUND);

        if (product == null) {
            return responsePayload;
        }

        if (product.getStatus() != Product.STATUS_VALID) {
            return responsePayload;
        }

        String guestUUID = getGuestUUID();
        Basket basket = basketService.getBasketByGuestUUID(guestUUID);

        if (basket == null) {
            basket = new Basket();
            basket.setGuestUUID(guestUUID);
        }

        BasketLineItem lineItem = basketService.createLineItem(product);
        lineItem.setBasket(basket);
        basket.getLineItems().add(lineItem);
        basketService.save(basket);

        responsePayload.setStatus(true);
        responsePayload.setCartModel(new CartModel(basket));

        return responsePayload;
    }

    @PostMapping("/removefromcart")
    @ResponseBody
    public CartModel removeFromCart(@RequestBody AddToCartPayload payload) {
        String guestUUID = getGuestUUID();
        Basket basket = basketService.getBasketByGuestUUID(guestUUID);

        if (basket != null) {
        }

        return new CartModel(basket);
    }

    @GetMapping("/minicart")
    public String showMinicart(Model model) {
        String guestUUID = getGuestUUID();
        Basket basket = basketService.getBasketByGuestUUID(guestUUID);
        CartModel cartModel = null;

        if (basket != null) {
            cartModel = cartUtil.createModel(basket, true);
        } else {
            cartModel = cartUtil.createModel(null);
        }

        model.addAttribute("cartModel", cartModel);

        return "global/minicart";
    }

}
