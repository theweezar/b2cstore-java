'use strict';

/**
 * Displays validation errors for the shipping form.
 * @param {*} errors 
 */
export function showErrors(errors) {
    for (let field in errors) {
        $("#" + field + "Error").text(errors[field]);
    }
}
