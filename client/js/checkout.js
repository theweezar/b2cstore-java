'use strict';

import { faker } from '@faker-js/faker';
import { isMobile } from './components/view.js';
import { showErrors } from './components/error.js';
import { toElement } from './components/fill.js';

/**
 * Switches to the specified checkout step.
 * @param {number} step - The step number to switch to (1, 2, or 3). 
 */
function switchToStep(step) {
    $('#checkoutMain').attr('data-step', step).data('step', step);
    if (isMobile()) {
        $('html, body').animate({
            scrollTop: $("#checkoutMain").offset().top
        }, 60);
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
                    switchToStep(2);
                } else {
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
 * Fills the billing and shipping address data in the summary section.
 * @param {Object} cartModel - The cart model containing billing and shipping data.
 */
function fillSummaryWithCartModel(cartModel) {
    const billingCard = $('.review-summary-card .billing-card');
    const shippingCard = $('.review-summary-card .shipping-card');
    if (cartModel?.billing) {
        toElement(billingCard, cartModel.billing);
        toElement(billingCard, cartModel.billing.address);
    }
    if (cartModel?.shipping) {
        toElement(shippingCard, cartModel.shipping);
        toElement(shippingCard, cartModel.shipping.address);
    }
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
                    if (data.cartModel) {
                        fillSummaryWithCartModel(data.cartModel);
                    }
                    switchToStep(3);
                } else {
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
 * Handles the "Place Order" button click event.
 * Sends an AJAX POST request to place the order.
 */
function handlePlaceOrder() {
    $('.place-order').on('click.placeOrder', function (event) {
        event.preventDefault();
        const actionUrl = $(this).data('action');
        $.ajax({
            url: actionUrl,
            method: 'POST',
            success: function (data) {
                if (data.redirect) {
                    window.location.href = data.redirect;
                } else if (data.success === true || data.success === 'true') {
                    alert('Order placed successfully!');
                } else {
                    alert('Failed to place the order. Please try again.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error placing order:', error);
                alert('An error occurred while placing the order. Please try again.');
            }
        });
    });
}

/**
 * Initializes the checkout progress bar, allowing users to navigate to previous steps.
 */
function initCheckoutProgress() {
    $('.checkout-progress .step').on('click', function () {
        const step = parseInt($(this).data('step'), 10);
        const currentStep = parseInt($('#checkoutMain').data('step'), 10);
        if (step < currentStep) {
            switchToStep(step);
        }
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
    handlePlaceOrder();
    initCheckoutProgress();
    fillShippingFormWithFakeData();
});
