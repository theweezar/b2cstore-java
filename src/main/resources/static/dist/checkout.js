/*
 * ATTENTION: The "eval" devtool has been used (maybe by default in mode: "development").
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
/******/ (function() { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./client/js/checkout.js":
/*!*******************************!*\
  !*** ./client/js/checkout.js ***!
  \*******************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _components_view_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./components/view.js */ \"./client/js/components/view.js\");\n/* harmony import */ var _components_error_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./components/error.js */ \"./client/js/components/error.js\");\n/* harmony import */ var _components_fill_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./components/fill.js */ \"./client/js/components/fill.js\");\n\n\n\n\n\n\n/**\r\n * Switches to the specified checkout step.\r\n * @param {number} step - The step number to switch to (1, 2, or 3). \r\n */\nfunction switchToStep(step) {\n  $('#checkoutMain').attr('data-step', step).data('step', step);\n  if ((0,_components_view_js__WEBPACK_IMPORTED_MODULE_0__.isMobile)()) {\n    $('html, body').animate({\n      scrollTop: $(\"#checkoutMain\").offset().top\n    }, 60);\n  }\n}\n\n/**\r\n * Handles the submission of the shipping form via AJAX.\r\n * Displays validation errors next to the corresponding fields.\r\n */\nfunction handleShippingFormSubmit() {\n  $('#shippingForm').on('submit.submitShipping', function (event) {\n    event.preventDefault();\n    var self = $(this);\n    $.ajax({\n      url: self.attr('action'),\n      method: 'POST',\n      data: self.serialize(),\n      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',\n      success: function success(data) {\n        if (data.redirect) {\n          window.location.href = data.redirect;\n          return;\n        }\n        if (data.success === true || data.success === 'true') {\n          switchToStep(2);\n        } else {\n          (0,_components_error_js__WEBPACK_IMPORTED_MODULE_1__.showErrors)(data.errors);\n        }\n      },\n      error: function error(xhr, status, _error) {\n        console.error('Error submitting form:', _error);\n        if (xhr.status === 400 && xhr.responseJSON) {\n          (0,_components_error_js__WEBPACK_IMPORTED_MODULE_1__.showErrors)(xhr.responseJSON);\n        }\n      }\n    });\n  });\n}\n\n/**\r\n * Fills the billing and shipping address data in the summary section.\r\n * @param {Object} cartModel - The cart model containing billing and shipping data.\r\n */\nfunction fillSummaryWithCartModel(cartModel) {\n  var billingCard = $('.review-summary-card .billing-card');\n  var shippingCard = $('.review-summary-card .shipping-card');\n  if (cartModel !== null && cartModel !== void 0 && cartModel.billing) {\n    (0,_components_fill_js__WEBPACK_IMPORTED_MODULE_2__.toElement)(billingCard, cartModel.billing);\n    (0,_components_fill_js__WEBPACK_IMPORTED_MODULE_2__.toElement)(billingCard, cartModel.billing.address);\n  }\n  if (cartModel !== null && cartModel !== void 0 && cartModel.shipping) {\n    (0,_components_fill_js__WEBPACK_IMPORTED_MODULE_2__.toElement)(shippingCard, cartModel.shipping);\n    (0,_components_fill_js__WEBPACK_IMPORTED_MODULE_2__.toElement)(shippingCard, cartModel.shipping.address);\n  }\n}\n\n/**\r\n * Handles the submission of the payment form via AJAX.\r\n */\nfunction handlePaymentFormSubmit() {\n  $('#paymentForm').on('submit.submitPayment', function (event) {\n    event.preventDefault();\n    var self = $(this);\n    $.ajax({\n      url: self.attr('action'),\n      method: 'POST',\n      data: self.serialize(),\n      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',\n      success: function success(data) {\n        if (data.redirect) {\n          window.location.href = data.redirect;\n          return;\n        }\n        if (data.success === true || data.success === 'true') {\n          if (data.cartModel) {\n            fillSummaryWithCartModel(data.cartModel);\n          }\n          switchToStep(3);\n        } else {\n          (0,_components_error_js__WEBPACK_IMPORTED_MODULE_1__.showErrors)(data.errors);\n        }\n      },\n      error: function error(xhr, status, _error2) {\n        console.error('Error submitting form:', _error2);\n        if (xhr.status === 400 && xhr.responseJSON) {\n          (0,_components_error_js__WEBPACK_IMPORTED_MODULE_1__.showErrors)(xhr.responseJSON);\n        }\n      }\n    });\n  });\n}\n\n/**\r\n * Handles the \"Place Order\" button click event.\r\n * Sends an AJAX POST request to place the order.\r\n */\nfunction handlePlaceOrder() {\n  $('.place-order').on('click.placeOrder', function (event) {\n    event.preventDefault();\n    var actionUrl = $(this).data('action');\n    $.ajax({\n      url: actionUrl,\n      method: 'POST',\n      success: function success(data) {\n        if (data.redirect) {\n          window.location.href = data.redirect;\n        } else if (data.success === true || data.success === 'true') {\n          alert('Order placed successfully!');\n        } else {\n          alert('Failed to place the order. Please try again.');\n        }\n      },\n      error: function error(xhr, status, _error3) {\n        console.error('Error placing order:', _error3);\n        alert('An error occurred while placing the order. Please try again.');\n      }\n    });\n  });\n}\n\n/**\r\n * Initializes the checkout progress bar, allowing users to navigate to previous steps.\r\n */\nfunction initCheckoutProgress() {\n  $('.checkout-progress .step').on('click', function () {\n    var step = parseInt($(this).data('step'), 10);\n    var currentStep = parseInt($('#checkoutMain').data('step'), 10);\n    if (step < currentStep) {\n      switchToStep(step);\n    }\n  });\n}\n\n/**\r\n * Fills the shipping form with random data using faker-js.\r\n */\nfunction fillShippingFormWithFakeData() {\n  var faker = window.faker;\n  if (!faker) {\n    console.warn('Faker library is not loaded.');\n    return;\n  }\n  $('#firstName').val(faker.name.firstName());\n  $('#lastName').val(faker.name.lastName());\n  $('#email').val(faker.internet.email());\n  $('#phone').val(faker.phone.number({\n    style: 'international'\n  }));\n  $('#shipFirstName').val(faker.name.firstName());\n  $('#shipLastName').val(faker.name.lastName());\n  $('#city').val(faker.location.city());\n  $('#state').val(faker.location.state());\n  $('#zipCode').val(faker.location.zipCode('#####'));\n  $('#address').val(faker.location.streetAddress());\n}\n$(document).ready(function () {\n  handleShippingFormSubmit();\n  handlePaymentFormSubmit();\n  handlePlaceOrder();\n  initCheckoutProgress();\n  fillShippingFormWithFakeData();\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/checkout.js?\n}");

/***/ }),

/***/ "./client/js/components/error.js":
/*!***************************************!*\
  !*** ./client/js/components/error.js ***!
  \***************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   showErrors: function() { return /* binding */ showErrors; }\n/* harmony export */ });\n\n\n/**\r\n * Displays validation errors for the shipping form.\r\n * @param {*} errors \r\n */\nfunction showErrors(errors) {\n  for (var field in errors) {\n    $(\"#\" + field + \"Error\").text(errors[field]);\n  }\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/components/error.js?\n}");

/***/ }),

/***/ "./client/js/components/fill.js":
/*!**************************************!*\
  !*** ./client/js/components/fill.js ***!
  \**************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   toElement: function() { return /* binding */ toElement; }\n/* harmony export */ });\n\n\n/**\r\n * Appends the content of the source object to the target element.\r\n * @param {JQuery<HTMLElement>} target - The target element to which content will be appended.\r\n * @param {Object} source - The source object containing content to append.\r\n */\nfunction toElement(target, source) {\n  for (var key in source) {\n    var element = target.find(\".\".concat(key));\n    if (element.length > 0) {\n      element.text(source[key]);\n    }\n  }\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/components/fill.js?\n}");

/***/ }),

/***/ "./client/js/components/view.js":
/*!**************************************!*\
  !*** ./client/js/components/view.js ***!
  \**************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   isMobile: function() { return /* binding */ isMobile; }\n/* harmony export */ });\n\n\n/**\r\n * Checks if the device is mobile.\r\n * @returns {boolean} True if the device is mobile (screen width <= 991px), false otherwise.\r\n */\nfunction isMobile() {\n  return window.matchMedia(\"only screen and (max-width: 991px)\").matches;\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/components/view.js?\n}");

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/define property getters */
/******/ 	!function() {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = function(exports, definition) {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	}();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	!function() {
/******/ 		__webpack_require__.o = function(obj, prop) { return Object.prototype.hasOwnProperty.call(obj, prop); }
/******/ 	}();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	!function() {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = function(exports) {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	}();
/******/ 	
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval devtool is used.
/******/ 	var __webpack_exports__ = __webpack_require__("./client/js/checkout.js");
/******/ 	
/******/ })()
;