'use strict';

import { updateView } from './util.js';
import { postForm } from '../components/ajax.js';

/**
 * Initializes the "checkout:submitPayment" event to handle payment form submissions.
 */
function initPaymentTrigger() {
    $('body').on('checkout:submitPayment', function (event, data) {
        const { url, formData, onSuccess, onError } = data;

        $.ajax({
            url: url,
            method: 'POST',
            data: formData,
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function (response) {
                if (onSuccess && typeof onSuccess === 'function') {
                    onSuccess(response);
                }
            },
            error: function (xhr, status, error) {
                if (onError && typeof onError === 'function') {
                    onError(xhr, error);
                }
            }
        });
    });
}

/**
 * Initializes the payment form submission handler.
 */
function initPaymentForm() {
    $('#paymentForm').on('submit.submitPayment', function (event) {
        event.preventDefault();

        const self = $(this);
        const actionUrl = self.attr('action');
        const formData = self.serialize();

        $('body').trigger('checkout:submitPayment', {
            url: actionUrl,
            formData: formData,
            onSuccess: (data) => {
                if (data.redirect) {
                    window.location.href = data.redirect;
                    return;
                }
                if (data.success === true) {
                    updateView(data.basketModel);
                } else {
                    self.showErrors(data.errors);
                }
            },
            onError: (xhr, error) => {
                console.error('Error submitting form:', error);
                if (xhr.status === 400 && xhr.responseJSON) {
                    self.showErrors(xhr.responseJSON);
                }
            }
        });
    });
}

/**
 * Triggers the "checkout:placeOrder" event to place the order.
 */
function initPlaceOrderTrigger() {
    $('body').on('checkout:placeOrder', function (event, data) {
        const { url } = data;
        $.ajax({
            url: url,
            method: 'POST',
            success: function (data) {
                if (data.onSuccess && typeof data.onSuccess === 'function') {
                    data.onSuccess(data);
                }
            },
            error: function (xhr, status, error) {
                if (data.onError && typeof data.onError === 'function') {
                    data.onError(error);
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
        $('body').trigger('checkout:placeOrder', {
            url: actionUrl,
            onSuccess: (data) => {
                if (data.redirect) {
                    window.location.href = data.redirect;
                }
            },
            onError: (error) => {
                console.error('Error placing order:', error);
            }
        });
    });
}

/**
 * Toggles the visibility of the billing form container and summary content.
 * @param {boolean} show - If true, shows the billing form; otherwise, shows the summary content.
 */
function toggleBillingFormVisibility(show) {
    $('.billing-form-container').toggleClass('d-none', !show);
    $('.summary-content').toggleClass('d-none', show);
}

/**
 * Initializes the billing form toggle functionality.
 */
function initBillingForm() {
    $('.edit-billing').on('click', function () {
        toggleBillingFormVisibility(true);
    });

    $('.cancel-billing-edit').on('click', function () {
        toggleBillingFormVisibility(false);
    });

    $('#billingForm').on('submit', async function (event) {
        event.preventDefault();

        const self = $(this);
        const actionUrl = self.attr('action');
        const formData = self.serialize();
        const updateBilling = await postForm(actionUrl, formData);

        if (updateBilling.redirect) {
            window.location.href = updateBilling.redirect;
            return;
        }
        if (!updateBilling.success) {
            self.showErrors(updateBilling?.error);
            return;
        }

        updateView(updateBilling.basketModel);
        toggleBillingFormVisibility(false);
    });
}

/**
 * Fills the billing form with random data using faker-js.
 */
function setFakeBilling() {
    const faker = window.faker;
    if (!faker) {
        console.warn('Faker library is not loaded.');
        return;
    }

    // Note: Keeping firstName, lastName, email, and phone in sync with customer information.
    // $('[name="billingAddress.firstName"]').val(faker.person.firstName());
    // $('[name="billingAddress.lastName"]').val(faker.person.lastName());
    // $('[name="billingAddress.email"]').val(faker.internet.email());
    // $('[name="billingAddress.phone"]').val(faker.phone.number({ style: 'international' }));
    $('[name="billingAddress.city"]').val(faker.location.city());
    $('[name="billingAddress.state"]').val(faker.location.state());
    $('[name="billingAddress.zipCode"]').val(faker.location.zipCode('#####'));
    $('[name="billingAddress.address"]').val(faker.location.streetAddress());
}

/**
 * Initializes the faker button to fill the billing form with random data.
 */
function initBillingFakerButton() {
    $('.faker-billing').on('click', function () {
        setFakeBilling();
    });
}

if (window.fk) window.fk.setFakeBilling = setFakeBilling;

export default {
    initPaymentTrigger,
    initPaymentForm,
    initPlaceOrderTrigger,
    handlePlaceOrder,
    initBillingForm,
    initBillingFakerButton
};