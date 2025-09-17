'use strict';

/**
 * Initializes custom jQuery functions.
 */
export function initCustomFn() {
    $.fn.showErrors = function (errors) {
        this.each(function () {
            const form = $(this);
            for (let field in errors) {
                if (errors[field]) {
                    $("#" + field + "Error", form).text(errors[field]);
                }
            }
        });
    }

    $.fn.clearErrors = function () {
        this.each(function () {
            const form = $(this);
            $(".error", form).empty();
        });
    }

    $.fn.fill = function (data) {
        this.each(function () {
            const self = $(this);
            for (let key in data) {
                $(`.${key}`, self).text(data[key]);
            }
        });
    }
}