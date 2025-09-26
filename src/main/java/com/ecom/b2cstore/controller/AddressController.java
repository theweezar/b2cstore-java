package com.ecom.b2cstore.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.form.AddressForm;
import com.ecom.b2cstore.model.CustomerModel;
import com.ecom.b2cstore.util.ErrorUtil;

import jakarta.validation.Valid;

@Controller
public class AddressController extends BaseController {

    @GetMapping("/address")
    public String getAddressPage(Model model) {
        initBaseModel(model);
        CustomerModel customerModel = getCurrentCustomerModel();
        model.addAttribute("customerModel", customerModel);
        model.addAttribute("addressForm", new AddressForm());
        model.addAttribute("pageTitle", "My Addresses");
        model.addAttribute("pageDescription", "Manage your shipping and billing addresses.");

        return "address";
    }

    @GetMapping("/address/create")
    public String getCreateAddressPage(Model model) {
        initBaseModel(model);
        CustomerModel customerModel = getCurrentCustomerModel();
        model.addAttribute("customerModel", customerModel);
        model.addAttribute("addressForm", new AddressForm());
        model.addAttribute("pageTitle", "Create New Address");
        model.addAttribute("pageDescription", "Add a new shipping or billing address to your account.");

        return "account/addressForm";
    }

    @PostMapping("/address/create")
    public ResponseEntity<Object> createAddress(@Valid @ModelAttribute AddressForm addressForm,
            BindingResult bindingResult) {
        Customer customer = getCurrentCustomer();
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", ErrorUtil.getBindingResultErrors(bindingResult)));
        }
        try {
            Address address = new Address();
            address.setCustomer(customer);
            address.copy(addressForm);

            addressService.create(address);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create address: " + e.getMessage()));
        }
    }

    @PostMapping("/address/update")
    public ResponseEntity<Object> updateAddress(@Valid @ModelAttribute AddressForm addressForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", ErrorUtil.getBindingResultErrors(bindingResult)));
        }

        Customer customer = getCurrentCustomer();
        try {
            Address existingAddress = addressService.findByIdAndCustomerId(addressForm.getId(),
                    customer.getCustomerId());
            if (existingAddress != null) {
                existingAddress.copy(addressForm);
                addressService.update(existingAddress);
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.status(403).body(Map.of("error", "Address not found or access denied"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update address: " + e.getMessage()));
        }
    }

    @PostMapping("/address/delete")
    public ResponseEntity<Object> deleteAddress(@RequestParam Long id) {
        Customer customer = getCurrentCustomer();
        try {
            Address address = addressService.findByIdAndCustomerId(id, customer.getCustomerId());
            if (address != null) {
                addressService.delete(id);
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.status(403).body(Map.of("error", "Address not found or access denied"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete address: " + e.getMessage()));
        }
    }
}