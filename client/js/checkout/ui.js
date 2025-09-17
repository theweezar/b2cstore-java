'use strict';

import { switchToStep } from './util.js';

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
    const faker = window.faker;
    if (!faker) {
        console.warn('Faker library is not loaded.');
        return;
    }
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

export default {
    initCheckoutProgress,
    fillShippingFormWithFakeData
};
