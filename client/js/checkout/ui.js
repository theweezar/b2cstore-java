'use strict';

import { switchToStep } from './util.js';

/**
 * Initializes the checkout progress bar, allowing users to navigate to previous steps.
 */
function initCheckoutProgress() {
    $('.checkout-progress .progress-step').on('click', function () {
        const step = parseInt($(this).data('step'), 10);
        const currentStep = parseInt($('#checkoutMain').data('step'), 10);
        if (step < currentStep) {
            switchToStep(step);
        }
    });
}

export default {
    initCheckoutProgress
};
