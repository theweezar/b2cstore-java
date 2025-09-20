'use strict';

import { fillSummary } from './util.js';

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
                    fillSummary(data.basketModel);
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
 * Initializes the billing form toggle functionality.
 */
function initBillingForm() {
    $('.edit-billing').on('click', function () {
        $('.billing-form-container').toggleClass('d-none');
        $('.summary-content').toggleClass('d-none');
    });

    $('.cancel-billing-edit').on('click', function () {
        $('.billing-form-container').addClass('d-none');
        $('.summary-content').removeClass('d-none');
    });
}

export default {
    initPaymentTrigger,
    initPaymentForm,
    initPlaceOrderTrigger,
    handlePlaceOrder,
    initBillingForm
};