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
            setupPaymentElement(data.clientSecret);
        },
        error: function (xhr, status, error) {
            console.error('Error fetching client secret:', error);
        }
    });
}

/**
 * Confirms the payment using Stripe's confirmPayment method
 * @param {object} elements - The Stripe Elements instance
 * @param {object} cartModel - The cart model containing item details
 */
async function confirmPayment(elements, cartModel) {
    const paymentForm = $('#paymentForm');
    const returnUrl = new URL($('#paymentElement').data('return-url'), window.location.origin);

    // Confirm the payment with Stripe
    const { error } = await stripe.confirmPayment({
        elements,
        confirmParams: {
            return_url: returnUrl.href
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

        if (result.error) {
            self.showErrors({ stripe: result.error.message });
            return;
        }

        const response = await submitPayment(actionUrl, formData);

        if (response.redirect) {
            window.location.href = response.redirect;
            return;
        }

        if (response.success === true) {
            await confirmPayment(elements, response.cartModel);
        } else {
            self.showErrors({ stripe: response.error || 'Payment failed. Please try again.' });
        }
    });
}

/**
 * Sets up the Stripe Payment Element and handles form submission
 * @param {string} clientSecret - The client secret obtained from the server
 */
function setupPaymentElement(clientSecret) {
    // Create an elements instance with options
    const elements = stripe.elements({
        clientSecret,
        appearance: {
            theme: 'stripe',
            variables: {
                colorPrimary: '#0570de',
            }
        }
    });

    // Create and mount the Payment Element
    const paymentElement = elements.create('payment', {
        layout: 'tabs'
    });
    paymentElement.mount('#paymentElement');

    initPaymentForm(elements);
}