package com.ecom.b2cstore.controller;

import java.util.Map;
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
import com.ecom.b2cstore.service.ProductStatus;
import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping("/minicart")    
    public String showMinicart(Model model) {
        String guestUUID = getGuestUUID();
        Basket basket = basketService.getBasketByGuestUUID(guestUUID);
        Map<String, Product> productMap = null;
        CartModel cartModel = null;

        if (basket != null) {
            productMap = basketService.getProductMap(basket);
            cartModel = new CartModel(basket);
            cartModel.setItems(cartModel.createItemList(productMap));
        } else {
            cartModel = new CartModel();
        }

        model.addAttribute("cartModel", cartModel);

        return "global/minicart/list";
    }

}
