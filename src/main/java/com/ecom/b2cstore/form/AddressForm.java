package com.ecom.b2cstore.form;

import lombok.Getter;
import lombok.Setter;
import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.model.AddressModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class AddressForm {
    private Long id;
    private boolean defaultAddress;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "(^\\+\\d{1,12})$", message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "\\d{4,6}?", message = "Invalid zip code format")
    private String zipCode;

    public AddressForm() {
        this.id = null;
        this.defaultAddress = false;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
        this.country = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
    }

    public AddressForm(Long id, boolean defaultAddress, String firstName, String lastName, String email, String phone,
            String country, String address, String city, String state, String zipCode) {
        this.id = id;
        this.defaultAddress = defaultAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public AddressForm(Address address) {
        copy(address);
    }

    public AddressForm(AddressModel model) {
        copy(model);
    }

    public AddressForm(AddressForm otherForm) {
        copy(otherForm);
    }

    public void copy(Address address) {
        if (address != null) {
            this.id = address.getId();
            this.defaultAddress = address.isDefaultAddress();
            this.firstName = address.getFirstName();
            this.lastName = address.getLastName();
            this.email = address.getEmail();
            this.phone = address.getPhone();
            this.country = address.getCountry();
            this.address = address.getAddress();
            this.city = address.getCity();
            this.state = address.getState();
            this.zipCode = address.getZipCode();
        }
    }

    public void copy(AddressModel model) {
        if (model != null) {
            this.id = null;
            this.defaultAddress = model.isDefaultAddress();
            this.firstName = model.getFirstName();
            this.lastName = model.getLastName();
            this.email = model.getEmail();
            this.phone = model.getPhone();
            this.country = model.getCountry();
            this.address = model.getAddress();
            this.city = model.getCity();
            this.state = model.getState();
            this.zipCode = model.getZipCode();
        }
    }

    public void copy(AddressForm form) {
        if (form != null) {
            this.id = form.getId();
            this.defaultAddress = form.isDefaultAddress();
            this.firstName = form.getFirstName();
            this.lastName = form.getLastName();
            this.email = form.getEmail();
            this.phone = form.getPhone();
            this.country = form.getCountry();
            this.address = form.getAddress();
            this.city = form.getCity();
            this.state = form.getState();
            this.zipCode = form.getZipCode();
        }
    }
}
