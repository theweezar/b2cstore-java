'use strict';

/**
 * Checks if the device is mobile.
 * @returns {boolean} True if the device is mobile (screen width <= 991px), false otherwise.
 */
export function isMobile() {
    return window.matchMedia("only screen and (max-width: 991px)").matches;
}