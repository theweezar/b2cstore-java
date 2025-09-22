'use strict';

import { postForm, postJSON } from "../components/ajax";

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
    if (!window.Stripe) {
        console.error('Stripe.js library is not loaded.');
        return;
    }

    stripe = Stripe(stripeApiKey);

    postJSON(paymentEl.data('create-payment-intent-url'), {})
        .then(data => {
            setupPaymentElement(data);
        }).catch(error => {
            console.error('Error fetching client secret:', error);
        });
}

function finalizeBillingDetails(basketModel) {
    const billingAddress = basketModel.billing.address;
    return {
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
}

/**
 * Confirms the payment using Stripe's confirmPayment method
 * @param {object} elements - The Stripe Elements instance
 * @param {object} basketModel - The cart model containing item details
 */
async function confirmPayment(elements, basketModel) {
    const paymentForm = $('#paymentForm');
    const returnUrl = new URL($('#paymentElement').data('return-url'), window.location.origin);
    const billingDetails = finalizeBillingDetails(basketModel);

    console.log('Billing Details:', billingDetails);

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
 * Initializes the payment form submission handler.
 * @param {object} elements - The Stripe Elements instance
 */
function initPaymentForm(elements) {
    $('#paymentForm').off('submit').on('submit', async function (event) {
        event.preventDefault();

        const self = $(this);
        const actionUrl = self.attr('action');
        const formData = self.serialize();
        const paymentEl = $('#paymentElement')
        const paymentIntentId = paymentEl.data('payment-intent-id');
        const updatePaymentIntentUrl = paymentEl.data('update-payment-intent-url');
        const body = $('body');
        const { error } = await elements.submit();

        if (error) {
            self.showErrors({ stripe: error.message });
            return;
        }

        body.spinner().start();

        // Submit the payment form data to the server
        const submitPayment = await postForm(actionUrl, formData);

        if (submitPayment.redirect) {
            window.location.href = submitPayment.redirect;
            return;
        }
        if (!submitPayment.success) {
            body.spinner().stop();
            self.showErrors(submitPayment?.error);
            return;
        }

        // Update the payment intent on the server
        const updatePI = await postJSON(updatePaymentIntentUrl, { paymentIntentId });

        if (updatePI.error) {
            body.spinner().stop();
            self.showErrors(updatePI.error);
            return;
        }

        body.spinner().stop();

        // Confirm the payment
        await confirmPayment(elements, submitPayment.basketModel);
    });
}

/**
 * Creates a Stripe Elements instance.
 * @param {string} clientSecret - The client secret for the PaymentIntent
 * @returns {object} - The Stripe Elements instance
 */
function createElements(clientSecret) {
    return stripe.elements({
        clientSecret: clientSecret,
        appearance: {
            theme: 'stripe',
            variables: {
                colorPrimary: '#0570de',
            }
        }
    });
}

/**
 * Creates a Stripe Payment Element.
 * @param {object} elements - The Stripe Elements instance
 * @returns {object} - The Stripe Payment Element
 */
function createPaymentElement(elements) {
    return elements.create('payment', {
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
}

/**
 * Sets up the Stripe Payment Element and handles form submission
 * @param {object} paymentIntentData - The payment data obtained from the server
 */
function setupPaymentElement(paymentIntentData) {
    const elements = createElements(paymentIntentData.clientSecret);
    const paymentElement = createPaymentElement(elements);
    const paymentIntentId = paymentIntentData.paymentIntentId;

    paymentElement.mount('#paymentElement');

    $('#paymentElement')
        .data('payment-intent-id', paymentIntentId)
        .attr('data-payment-intent-id', paymentIntentId);

    initPaymentForm(elements);
}