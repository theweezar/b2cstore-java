package com.ecom.b2cstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingModel {
    private AddressModel address;
    
    public BillingModel() {
        this.address = new AddressModel();
    }
}
