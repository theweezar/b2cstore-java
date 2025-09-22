'use strict';

import '../scss/bootstrap.scss';
import '../scss/global.scss';
import 'bootstrap';
import $ from "jquery";
import { faker } from '@faker-js/faker';
import { initSpinnerHandler } from './components/spinner.js';
import { initCustomFn } from './components/custom.js';
import { initMiniCartButtons, initRemoveFromCartButtons } from './components/minicart.js';

// Make jQuery and faker globally available
window.$ = $;
window.jQuery = $;
window.faker = faker;
window.fk = {};

$(document).ready(function () {
    initSpinnerHandler();
    initCustomFn();
    initMiniCartButtons();
    initRemoveFromCartButtons();
});