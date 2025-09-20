'use strict';

import { isMobile } from '../components/view.js';

/**
 * Fills the billing and shipping address data in the summary section.
 * @param {Object} basketModel - The cart model containing billing and shipping data.
 */
export function fillSummary(basketModel) {
    const shippingCard = $('.summary-section #shippingSummary');
    const billingCard = $('.summary-section #billingSummary');
    if (basketModel?.shipping?.address) {
        shippingCard.fill(basketModel.shipping.address);
    }
    if (basketModel?.billing?.address) {
        billingCard.fill(basketModel.billing.address);
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