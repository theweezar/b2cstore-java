'use strict';

/**
 * Triggers the loading of the mini cart content.
 * @param {string} url - The URL to fetch the mini cart content from.
 */
function triggerMiniCartLoad(url) {
    if (!url) {
        console.error('URL is required to load minicart content.');
        return;
    }
    $.ajax({
        method: 'GET',
        url: url,
        success: function (response) {
            if (typeof response === 'string') {
                const html = $(response);
                const minicartRight = $('#minicartRight');
                minicartRight.find('.offcanvas-header').replaceWith(html.find('.offcanvas-header'));
                minicartRight.find('.offcanvas-body').replaceWith(html.find('.offcanvas-body'));
                minicartRight.find('.offcanvas-basket-data').replaceWith(html.find('.offcanvas-basket-data'));
            }
        },
        error: function () {
            console.error('Failed to fetch minicart content.');
        }
    });
}

/**
 * Initializes event listeners for minicart show event.
 */
export function initMiniCartButtons() {
    $('#minicartRight').on('show.bs.offcanvas', function (e) {
        const url = $(this).data('url');
        triggerMiniCartLoad(url);
    });
}

/**
 * Removes a product from the shopping cart.
 * @param {string} pid - The product ID to remove.
 * @param {string} uuid - The unique identifier of the line item.
 * @returns {Promise} - A Promise that resolves with the response or rejects with an error.
 */
function removeFromCart(pid, uuid) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/removefromcart',
            method: 'POST',
            data: JSON.stringify({ pid: pid, uuid: uuid }),
            contentType: 'application/json; charset=UTF-8',
            success: function (response) {
                if (response && response.itemCount !== undefined) {
                    resolve(response);
                } else {
                    reject(new Error('Failed to remove product from cart.'));
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                reject(new Error(`An error occurred: ${textStatus} - ${errorThrown}`));
            }
        });
    });
}

/**
 * Initializes event listeners for "Remove from Cart" buttons.
 */
export function initRemoveFromCartButtons() {
    $('body').on('click.removeFromCart', 'a.minicart-remove-btn', function (event) {
        event.preventDefault();
        const self = $(this);
        const wrapper = self.parents('.product-li-wrapper');
        const pid = wrapper.data('pid');
        const uuid = wrapper.data('uuid');
        const minicartRight = $('#minicartRight');
        const url = minicartRight.data('url');

        if (pid && uuid) {
            minicartRight.spinner().start();
            removeFromCart(pid, uuid)
                .then((response) => {
                    $('.minicart-show .cart-count').text(response.itemCount);
                    triggerMiniCartLoad(url);
                }).catch((error) => {
                    alert(error.message);
                }).finally(() => {
                    minicartRight.spinner().stop();
                });
        }
    });
}