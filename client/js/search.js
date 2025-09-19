'use strict';

/**
 * Adds a product to the shopping cart.
 * @param {string} pid - The product ID to add.
 */
function addToCart(pid) {
    $('body').spinner().start();
    $.ajax({
        url: '/addtocart',
        method: 'POST',
        data: JSON.stringify({ pid: pid, quantity: 1 }),
        contentType: 'application/json; charset=UTF-8',
        success: function (response) {
            $('body').spinner().stop();
            if (response.status) {
                // alert('Product added to cart successfully.');
                $('.minicart-show .cart-count').text(response.basketModel.itemCount);
            } else if (response.productStatus) {
                alert(`Product status: ${response.productStatus}`);
            } else {
                alert('Failed to add product to cart.');
            }
        },
        error: function () {
            $('body').spinner().stop();
            alert('An error occurred while adding the product to cart.');
        }
    });
}

/**
 * Initializes event listeners for "Add to Cart" buttons.
 */
function initAddToCartButtons() {
    $('.add-to-cart-btn').on('click.addToCart', function (event) {
        event.preventDefault();
        const self = $(this);
        const pid = self.parents('.product-tile-card').data('pid');

        if (pid) {
            addToCart(pid);
        }
    });
}

$(document).ready(function () {
    initAddToCartButtons();
});
