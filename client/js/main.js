'use strict';

import '../scss/bootstrap.scss';
import 'bootstrap';
import $ from "jquery";

window.$ = $;
window.jQuery = $;

function initAddToCartButtons() {
    $('a.minicart-show').on('click.minicartShow', function (e) {
        e.preventDefault();
        const url = $(this).attr('href');

        $.ajax({
            method: 'GET',
            url: url,
            success: function (response) {
                if (typeof response === 'string') {
                    let html = $(response);
                    $('#minicartRight .offcanvas-header').replaceWith(html.find('.offcanvas-header'));
                    $('#minicartRight .offcanvas-body').replaceWith(html.find('.offcanvas-body'));
                    $('#minicartRight .offcanvas-basket-data').replaceWith(html.find('.offcanvas-basket-data'));
                }
            },
            error: function () {
                console.error('Failed to fetch minicart content.');
            }
        });
    });
}

$(document).ready(function () {
    initAddToCartButtons();
});