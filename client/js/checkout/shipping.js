'use strict';

import { switchToStep, fillSummaryWithCartModel } from './util.js';

/**
 * Handles the submission of the shipping form via AJAX.
 * Displays validation errors next to the corresponding fields.
 */
function handleShippingFormSubmit() {
    $('#shippingForm').on('submit.submitShipping', function (event) {
        event.preventDefault();

        const form = $(this);

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
                    fillSummaryWithCartModel(data.cartModel);
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

export default {
    handleShippingFormSubmit
};