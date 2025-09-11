'use strict';

/**
 * Adds a product to the shopping cart.
 * @param {string} pid - The product ID to add.
 */
function addToCart(pid) {
    $.ajax({
        url: '/addtocart',
        method: 'POST',
        data: JSON.stringify({ pid: pid, quantity: 1 }),
        contentType: 'application/json; charset=UTF-8',
        success: function (response) {
            if (response.status) {
                // alert('Product added to cart successfully.');
                $('.minicart-show .cart-count').text(response.cartModel.itemCount);
            } else if (response.productStatus) {
                alert(`Product status: ${response.productStatus}`);
            } else {
                alert('Failed to add product to cart.');
            }
        },
        error: function () {
            alert('An error occurred while adding the product to cart.');
        }
    });
}

/**
 * Initializes event listeners for "Add to Cart" buttons.
 */
function initAddToCartButtons() {
    const addToCartBtn = $('.btn.btn-atc');
    addToCartBtn.on('click.addToCart', function (event) {
        event.preventDefault();
        const self = $(this);
        const pid = self.parents('.product-tile-card').data('pid');

        addToCart(pid);
    });
}

$(document).ready(function () {
    initAddToCartButtons();
});
