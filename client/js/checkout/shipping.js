'use strict';

import { switchToStep, updateView } from './util.js';

/**
 * Handles the submission of the shipping form via AJAX.
 * Displays validation errors next to the corresponding fields.
 */
function initShippingForm() {
    $('#shippingForm').on('submit.submitShipping', function (event) {
        event.preventDefault();

        const form = $(this);

        $('[name="shippingAddress.email"', form).val($('[name="email"', form).val());
        $('[name="shippingAddress.phone"', form).val($('[name="phone"', form).val());

        form.clearErrors();

        $.ajax({
            url: form.attr('action'),
            method: 'POST',
            data: form.serialize(),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function (data) {
                if (data.redirect) {
                    window.location.href = data.redirect;
                    return;
                }
                if (data.success === true) {
                    updateView(data.basketModel);
                    switchToStep(2);
                } else {
                    form.showErrors(data.errors);
                }
            },
            error: function (xhr, status, error) {
                console.error('Error submitting form:', error);
                if (xhr.status === 400 && xhr.responseJSON) {
                    form.showErrors(xhr.responseJSON);
                }
            }
        });
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
    $('[name="firstName"]').val(faker.person.firstName());
    $('[name="lastName"]').val(faker.person.lastName());
    $('[name="email"]').val(faker.internet.email());
    $('[name="phone"]').val(faker.phone.number({ style: 'international' }));
    $('[name="shippingAddress.firstName"]').val(faker.person.firstName());
    $('[name="shippingAddress.lastName"]').val(faker.person.lastName());
    $('[name="shippingAddress.city"]').val(faker.location.city());
    $('[name="shippingAddress.state"]').val(faker.location.state());
    $('[name="shippingAddress.zipCode"]').val(faker.location.zipCode('#####'));
    $('[name="shippingAddress.address"]').val(faker.location.streetAddress());
}

export default {
    initShippingForm,
    fillShippingFormWithFakeData
};