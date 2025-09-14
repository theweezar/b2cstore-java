
package com.ecom.b2cstore.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class CustomerForm {

    @Getter
    @Setter
    @NotBlank(message = "First name is required")
    private String firstName;

    @Getter
    @Setter
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Getter
    @Setter
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Getter
    @Setter
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @Getter
    @Setter
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Getter
    @Setter
    @NotBlank(message = "Confirm password is required")
    @Size(min = 6, message = "Confirm password must be at least 6 characters long")
    private String confirmPassword;

    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmPassword);
    }

    @Pattern(regexp = "(^\\+\\d{1,12})$", message = "Invalid phone number")
    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String country;
}
