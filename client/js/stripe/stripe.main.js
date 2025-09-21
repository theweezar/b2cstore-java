'use strict';

// Initialize Stripe with your publishable key
let stripe;

/**
 * Initialize the Stripe Payment Element
 * This function fetches a client secret from the server and sets up the Payment Element
 */
export function initStripePaymentElement() {
    const paymentEl = $('#paymentElement');
    if (paymentEl.length === 0) {
        console.warn('Payment Element container not found.');
        return;
    }

    const stripeApiKey = paymentEl.data('stripe-api-key');
    if (!stripeApiKey) {
        console.error('Stripe API key is not set.');
        return;
    }

    stripe = Stripe(stripeApiKey);

    $.ajax({
        url: paymentEl.data('create-payment-intent-url'),
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({}),
        success: function (data) {
            setupPaymentElement(data);
        },
        error: function (xhr, status, error) {
            console.error('Error fetching client secret:', error);
        }
    });
}

/**
 * Confirms the payment using Stripe's confirmPayment method
 * @param {object} elements - The Stripe Elements instance
 * @param {object} basketModel - The cart model containing item details
 */
async function confirmPayment(elements, basketModel) {
    const paymentForm = $('#paymentForm');
    const returnUrl = new URL($('#paymentElement').data('return-url'), window.location.origin);
    const billingAddress = basketModel.billing.address;
    const billingDetails = {
        name: `${billingAddress.firstName} ${billingAddress.lastName}`,
        email: billingAddress.email,
        address: {
            line1: billingAddress.address,
            city: billingAddress.city,
            state: billingAddress.state,
            postal_code: billingAddress.zipCode,
            country: billingAddress.country,
        }
    };
    console.log('Billing Details:', billingDetails);

    // Confirm the payment with Stripe
    const { error } = await stripe.confirmPayment({
        elements,
        confirmParams: {
            return_url: returnUrl.href,
            payment_method_data: {
                billing_details: billingDetails
            }
        }
    });

    if (error) {
        paymentForm.showErrors({ stripe: error.message });
    }
}

/**
 * Submits the payment form data to the server
 * @param {string} url - The URL to submit the payment form to
 * @param {object} formData - The serialized form data
 * @returns {Promise} - A promise that resolves with the server response
 */
function submitPayment(url, formData) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            method: 'POST',
            data: formData,
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function (response) {
                resolve(response);
            },
            error: function (xhr, status, error) {
                reject({ xhr, error });
            }
        });
    });
}

/**
 * Updates the payment intent on the server
 * @param {string} url - The URL to update the payment intent
 * @param {object} data - The data to send in the request
 * @returns {Promise} - A promise that resolves with the server response
 */
function updatePaymentIntent(url, data) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                resolve(response);
            },
            error: function (xhr, status, error) {
                reject({ xhr, error });
            }
        });
    });
}

/**
 * Initializes the payment form submission handler.
 * @param {object} elements - The Stripe Elements instance
 */
function initPaymentForm(elements) {
    $('#paymentForm').off('submit').on('submit', async function (event) {
        event.preventDefault();

        const self = $(this);
        const actionUrl = self.attr('action');
        const formData = self.serialize();
        const result = await elements.submit();
        const paymentEl = $('#paymentElement')
        const paymentIntentId = paymentEl.data('payment-intent-id');
        const updatePaymentIntentUrl = paymentEl.data('update-payment-intent-url');
        const body = $('body');

        body.spinner().start();

        if (result.error) {
            body.spinner().stop();
            self.showErrors({ stripe: result.error.message });
            return;
        }

        const paymentResponse = await submitPayment(actionUrl, formData);

        if (paymentResponse.redirect) {
            window.location.href = paymentResponse.redirect;
            return;
        }

        if (!paymentResponse.success) {
            body.spinner().stop();
            self.showErrors({ stripe: paymentResponse.error || 'Payment failed. Please try again.' });
            return;
        }

        const updateResponse = await updatePaymentIntent(updatePaymentIntentUrl, { paymentIntentId });

        if (updateResponse.error) {
            body.spinner().stop();
            self.showErrors({ stripe: updateResponse.error });
            return;
        }

        body.spinner().stop();

        // Confirm the payment
        await confirmPayment(elements, paymentResponse.basketModel);
    });
}

/**
 * Sets up the Stripe Payment Element and handles form submission
 * @param {object} paymentIntentData - The payment data obtained from the server
 */
function setupPaymentElement(paymentIntentData) {
    // Create an elements instance with options
    const elements = stripe.elements({
        clientSecret: paymentIntentData.clientSecret,
        appearance: {
            theme: 'stripe',
            variables: {
                colorPrimary: '#0570de',
            }
        }
    });

    // Create and mount the Payment Element
    const paymentElement = elements.create('payment', {
        layout: 'tabs',
        business: {
            name: 'B2C DEMO'
        },
        fields: {
            billingDetails: {
                name: 'auto',
                email: 'auto',
                phone: 'auto',
                address: 'auto'
            }
        }
    });
    paymentElement.mount('#paymentElement');

    const paymentIntentId = paymentIntentData.paymentIntentId;

    $('#paymentElement').data('payment-intent-id', paymentIntentId).attr('data-payment-intent-id', paymentIntentId);

    initPaymentForm(elements, paymentIntentId);
}