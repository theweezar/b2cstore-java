'use strict';

import { showErrors } from './components/error.js';

/**
 * Initializes the registration form submission handler.
 */
function initRegisterForm() {
    $("form#registerForm").on("submit", function (event) {
        event.preventDefault();

        const self = $(this);
        const formData = self.serialize();
        $(".error").text("");

        $.ajax({
            url: self.attr("action"),
            type: "POST",
            data: formData,
            success: function (response) {
                if (response.success) {
                    window.location.href = response.redirect;
                } else if (response.errors) {
                    showErrors(response.errors);
                }
            },
            error: function (xhr) {
                const errors = xhr.responseJSON?.errors || { general: "An unexpected error occurred." };
                showErrors(errors);
            }
        });
    });
}

/**
 * Fills the registration form with dummy data for testing purposes.
 */
function fillRegisterFormWithDummyData() {
    const faker = window.faker;
    if (!faker) {
        console.warn('Faker library is not loaded.');
        return;
    }
    if ($("#registerForm").length === 0) {
        return;
    }
    const password = "Testing@123";
    const firstName = faker.person.firstName();
    const lastName = faker.person.lastName();
    const username = faker.internet.displayName({ firstName: firstName, lastName: lastName });

    const fakerData = {
        firstName: firstName,
        lastName: lastName,
        email: faker.internet.email(),
        username: username,
        password: password,
        confirmPassword: password,
        phone: faker.phone.number({ style: 'international' })
    };

    $("#firstName").val(fakerData.firstName);
    $("#lastName").val(fakerData.lastName);
    $("#email").val(fakerData.email);
    $("#username").val(fakerData.username);
    $("#password").val(fakerData.password);
    $("#confirmPassword").val(fakerData.confirmPassword);
    $("#phone").val(fakerData.phone);
}

$(document).ready(function () {
    initRegisterForm();
    fillRegisterFormWithDummyData();
});