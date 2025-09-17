'use strict';

import { isMobile } from '../components/view.js';

/**
 * Fills the billing and shipping address data in the summary section.
 * @param {Object} cartModel - The cart model containing billing and shipping data.
 */
export function fillSummaryWithCartModel(cartModel) {
    const billingCard = $('.review-summary-card .billing-card');
    const shippingCard = $('.review-summary-card .shipping-card');
    if (cartModel?.billing) {
        billingCard.fill(cartModel.billing);
        billingCard.fill(cartModel.billing.address);
    }
    if (cartModel?.shipping) {
        shippingCard.fill(cartModel.shipping);
        shippingCard.fill(cartModel.shipping.address);
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