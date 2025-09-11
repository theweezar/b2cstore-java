'use strict';

import '../scss/bootstrap.scss';
import 'bootstrap';
import $ from "jquery";

window.$ = $;
window.jQuery = $;

function initAddToCartButtons() {
    $('body').on('click', 'a.minicart-show', function (e) {
        e.preventDefault();
        const url = $(this).attr('href');

        $.ajax({
            method: 'GET',
            url: url,
            success: function (response) {
                $('#minicartRight .offcanvas-body').empty().html(response);
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