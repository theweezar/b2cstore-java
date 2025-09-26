'use strict';

import { switchToStep, updateView } from './util.js';
import { postForm } from '../components/ajax.js';

/**
 * Handles the submission of the shipping form via AJAX.
 * Displays validation errors next to the corresponding fields.
 */
function initShippingForm() {
    $('#shippingForm').on('submit.submitShipping', async function (event) {
        event.preventDefault();

        const form = $(this);
        form.clearErrors();

        $('[name="shippingAddress.email"', form).val($('[name="email"', form).val());
        $('[name="shippingAddress.phone"', form).val($('[name="phone"', form).val());

        const submitShipping = await postForm(form.attr('action'), form.serialize());

        if (submitShipping.redirect) {
            window.location.href = submitShipping.redirect;
            return;
        }
        if (!submitShipping.success) {
            form.showErrors(submitShipping.error);
            return;
        }

        updateView(submitShipping.basketModel);
        switchToStep(2);

        if (submitShipping.savedAddressesHtml) {
            const savedAddressSection = $('.saved-address-list');
            savedAddressSection.replaceWith(submitShipping.savedAddressesHtml);
            showShippingAddressSection(false);
        }
    });
}

/**
 * Shows or hides the shipping address section.
 * @param {boolean} show 
 */
function showShippingAddressSection(show) {
    const shippingAddressSection = $('.shipping-address-section');
    const savedAddressSection = $('.saved-address-list');
    shippingAddressSection.toggleClass('d-none', !show);
    savedAddressSection.toggleClass('d-none', show);
}

/**
 * Initializes the handler for the "Use New Address" checkbox.
 */
function initNewAddressHandler() {
    $('body').on('change', '[name="selectedAddressId"]', function () {
        const self = $(this);
        const val = self.val();
        
        if (val === 'new') {
            showShippingAddressSection(true);
        } else if (val === 'existing') {
            showShippingAddressSection(false);
            $('.saved-address-list input[type="radio"]:first').prop('checked', true).trigger('change');
        }
    });
}

/**
 * Initializes the handler for selecting a saved address.
 * Fills the shipping form with the selected address details.
 */
function initSelectSavedAddressHandler() {
    $('body').on('change', '.saved-address-list input[type="radio"]', function () {
        const self = $(this);
        const addressObj = {
            firstName: self.data('first-name'),
            lastName: self.data('last-name'),
            address: self.data('address'),
            city: self.data('city'),
            state: self.data('state'),
            zipCode: self.data('zip')
        };
        const shippingAddressSection = $('.shipping-address-section');

        for (const key in addressObj) {
            shippingAddressSection.find(`[name$="${key}"]`).val(addressObj[key]).attr('value', addressObj[key]);
        }
    });
}

/**
 * Fills the shipping form with random data using faker-js.
 */
function setFakeShipping() {
    const faker = window.faker;
    if (!faker) {
        console.warn('Faker library is not loaded.');
        return;
    }
    $('[name="firstName"]:not([readonly="readonly"])').val(faker.person.firstName());
    $('[name="lastName"]:not([readonly="readonly"])').val(faker.person.lastName());
    $('[name="email"]:not([readonly="readonly"])').val(faker.internet.email());
    $('[name="phone"]:not([readonly="readonly"])').val(faker.phone.number({ style: 'international' }));

    $('[name="shippingAddress.firstName"]').val(faker.person.firstName());
    $('[name="shippingAddress.lastName"]').val(faker.person.lastName());
    $('[name="shippingAddress.city"]').val(faker.location.city());
    $('[name="shippingAddress.state"]').val(faker.location.state());
    $('[name="shippingAddress.zipCode"]').val(faker.location.zipCode('#####'));
    $('[name="shippingAddress.address"]').val(faker.location.streetAddress());
}

/**
 * Initializes the faker button to fill the shipping form with random data.
 */
function initShippingFakerButton() {
    $('.faker-shipping').on('click', function () {
        setFakeShipping();
    });
}

if (window.fk) window.fk.setFakeShipping = setFakeShipping;

export default {
    initShippingForm,
    initNewAddressHandler,
    initSelectSavedAddressHandler,
    initShippingFakerButton
};