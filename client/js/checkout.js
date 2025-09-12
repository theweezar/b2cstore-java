'use strict';

import { faker } from '@faker-js/faker';

/**
 * Displays validation errors for the shipping form.
 * @param {*} errors 
 */
function showErrors(errors) {
    for (let field in errors) {
        $("#" + field + "Error").text(errors[field]);
    }
}



/**
 * Handles the submission of the shipping form via AJAX.
 * Displays validation errors next to the corresponding fields.
 */
function handleShippingFormSubmit() {
    $('#shippingForm').on('submit.submitShipping', function (event) {
        event.preventDefault();

        const self = $(this);

        $.ajax({
            url: self.attr('action'),
            method: 'POST',
            data: self.serialize(),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function (data) {
                if (data.redirect) {
                    window.location.href = data.redirect;
                    return;
                }
                if (data.success === true || data.success === 'true') {
                    // Handle success (e.g., redirect or show success message)
                    console.log('Form submitted successfully');
                    $('#checkoutMain').attr('data-step', '2').data('step', 2);
                } else {
                    // Handle validation errors
                    showErrors(data.errors);
                }
            },
            error: function (xhr, status, error) {
                console.error('Error submitting form:', error);
                if (xhr.status === 400 && xhr.responseJSON) {
                    showErrors(xhr.responseJSON);
                }
            }
        });
    });
}

/**
 * Handles the submission of the payment form via AJAX.
 */
function handlePaymentFormSubmit() {
    $('#paymentForm').on('submit.submitPayment', function (event) {
        event.preventDefault();

        const self = $(this);

        $.ajax({
            url: self.attr('action'),
            method: 'POST',
            data: self.serialize(),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function (data) {
                if (data.redirect) {
                    window.location.href = data.redirect;
                    return;
                }
                if (data.success === true || data.success === 'true') {
                    // Handle success (e.g., redirect or show success message)
                    console.log('Form submitted successfully');
                    $('#checkoutMain').attr('data-step', '3').data('step', 3);
                } else {
                    // Handle validation errors
                    showErrors(data.errors);
                }
            },
            error: function (xhr, status, error) {
                console.error('Error submitting form:', error);
                if (xhr.status === 400 && xhr.responseJSON) {
                    showErrors(xhr.responseJSON);
                }
            }
        });
    });
}

/**
 * Fills the shipping form with random data using faker-js.
 */
function fillShippingFormWithFakeData() {
    $('#firstName').val(faker.name.firstName());
    $('#lastName').val(faker.name.lastName());
    $('#email').val(faker.internet.email());
    $('#phone').val(faker.phone.number({ style: 'international' }));
    $('#shipFirstName').val(faker.name.firstName());
    $('#shipLastName').val(faker.name.lastName());
    $('#city').val(faker.location.city());
    $('#state').val(faker.location.state());
    $('#zipCode').val(faker.location.zipCode('#####'));
    $('#address').val(faker.location.streetAddress());
}

$(document).ready(function () {
    handleShippingFormSubmit();
    handlePaymentFormSubmit();
    fillShippingFormWithFakeData();
});
