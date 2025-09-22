package com.ecom.b2cstore.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingForm {
    @Valid
    @NotNull(message = "Billing address is required")
    private AddressForm billingAddress;

    public BillingForm() {
        this.billingAddress = new AddressForm();
    }
}
