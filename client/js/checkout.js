'use strict';

import { executeFunctions } from './components/util.js';
import ui from './checkout/ui.js';
import shipping from './checkout/shipping.js';
import billing from './checkout/billing.js';
import { initStripePaymentElement } from './stripe/stripe.main.js';

$(document).ready(function () {
    executeFunctions(ui);
    executeFunctions(shipping);
    executeFunctions(billing);
    executeFunctions(initStripePaymentElement);
});
