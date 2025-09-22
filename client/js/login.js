'use strict';

/**
 * Initializes the registration form submission handler.
 */
function initRegisterForm() {
    $("form#registerForm").on("submit", function (event) {
        event.preventDefault();

        const self = $(this);
        const formData = self.serialize();

        self.clearErrors();

        $.ajax({
            url: self.attr("action"),
            type: "POST",
            data: formData,
            success: function (response) {
                if (response.success) {
                    window.location.href = response.redirect;
                } else {
                    self.showErrors(response.error);
                }
            },
            error: function (xhr) {
                self.showErrors(xhr.responseJSON?.error || { general: "An unexpected error occurred." });
            }
        });
    });
}

/**
 * Fills the registration form with dummy data for testing purposes.
 */
function setFakeRegistration() {
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

if (window.fk) window.fk.setFakeRegistration = setFakeRegistration;

$(document).ready(function () {
    initRegisterForm();
    setFakeRegistration();
});