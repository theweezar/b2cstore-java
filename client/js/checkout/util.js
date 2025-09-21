'use strict';

import { isMobile } from '../components/view.js';

/**
 * Updates the view with the latest basket model data.
 * @param {Object} basketModel - The cart model containing billing and shipping data.
 */
export function updateView(basketModel) {
    const shippingCard = $('.summary-section #shippingSummary');
    const billingCard = $('.summary-section #billingSummary');
    const billingForm = $('#billingForm');
    if (basketModel?.shipping?.address) {
        shippingCard.fill(basketModel.shipping.address);
    }
    if (basketModel?.billing?.address) {
        const billingAddress = basketModel.billing.address;
        billingCard.fill(billingAddress);

        if (billingForm.length === 0) return;

        for (let key in billingAddress) {
            $(`[name$="${key}"]`, billingForm).val(billingAddress[key]);
        }
    }
}

/**
 * Switches to the specified checkout step.
 * @param {number} step - The step number to switch to (1, 2, or 3). 
 */
export function switchToStep(step) {
    $('#checkoutMain').attr('data-step', step).data('step', step);
    if (isMobile()) {
        $('html, body').animate({
            scrollTop: $("#checkoutMain").offset().top
        }, 60);
    }
}