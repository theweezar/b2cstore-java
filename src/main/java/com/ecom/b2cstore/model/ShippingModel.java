package com.ecom.b2cstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingModel {
    private AddressModel address;
    
    public ShippingModel() {
        this.address = new AddressModel();
    }

    // Applicable shipping methods can be added here
}
