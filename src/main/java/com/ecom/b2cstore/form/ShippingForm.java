package com.ecom.b2cstore.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class ShippingForm {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2-50 characters")
    @Getter
    @Setter
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2-50 characters")
    @Getter
    @Setter
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Getter
    @Setter
    private String email;

    @Pattern(regexp = "(^1\\d{9}$)", message = "Invalid phone number")
    @Getter
    @Setter
    private String phone;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2-50 characters")
    @Getter
    @Setter
    private String shipFirstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2-50 characters")
    @Getter
    @Setter
    private String shipLastName;

    @NotBlank(message = "Country is required")
    @Getter
    @Setter
    private String country;

    @NotBlank(message = "Address is required")
    @Getter
    @Setter
    private String address;

    @NotBlank(message = "City is required")
    @Getter
    @Setter
    private String city;

    @NotBlank(message = "State is required")
    @Getter
    @Setter
    private String state;

    @Pattern(regexp = "\\d{4,6}", message = "Invalid zip code")
    @Getter
    @Setter
    private String zipCode;
}
