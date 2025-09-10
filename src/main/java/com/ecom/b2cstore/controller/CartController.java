package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.BasketLineItem;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.model.CartModel;
import com.ecom.b2cstore.payload.AddToCartPayload;
import com.ecom.b2cstore.payload.AddToCartResponsePayload;
import com.ecom.b2cstore.service.ProductStatus;

@Controller
public class CartController extends BaseController {
    public CartController() {
        super();
    }

    @PostMapping("/addtocart")
    @ResponseBody
    public AddToCartResponsePayload addToCart(@RequestBody AddToCartPayload payload) {
        ProductStatus productStatus = productService.getProductStatus(payload.getPid());
        AddToCartResponsePayload responsePayload = new AddToCartResponsePayload(false, productStatus.getStatus());
        String guestUUID = getGuestUUID();

        if (!productStatus.getStatus().equals(ProductStatus.VALID)) {
            return responsePayload;
        }

        Product product = productStatus.getProduct();
        Basket currentBasket = basketService.getBasketByGuestUUID(guestUUID);

        if (currentBasket == null) {
            currentBasket = new Basket();
            currentBasket.setGuestUUID(guestUUID);
        }

        BasketLineItem lineItem = basketService.createLineItem(product);
        lineItem.setBasket(currentBasket);
        currentBasket.getLineItems().add(lineItem);
        basketService.save(currentBasket);

        responsePayload.setStatus(true);
        responsePayload.setCartModel(new CartModel(currentBasket));

        return responsePayload;
    }

}
