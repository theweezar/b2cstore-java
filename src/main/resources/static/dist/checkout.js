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

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _components_util_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./components/util.js */ \"./client/js/components/util.js\");\n/* harmony import */ var _checkout_ui_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./checkout/ui.js */ \"./client/js/checkout/ui.js\");\n/* harmony import */ var _checkout_shipping_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./checkout/shipping.js */ \"./client/js/checkout/shipping.js\");\n/* harmony import */ var _checkout_billing_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./checkout/billing.js */ \"./client/js/checkout/billing.js\");\n/* harmony import */ var _stripe_stripe_main_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./stripe/stripe.main.js */ \"./client/js/stripe/stripe.main.js\");\n\n\n\n\n\n\n\n$(document).ready(function () {\n  (0,_components_util_js__WEBPACK_IMPORTED_MODULE_0__.executeFunctions)(_checkout_ui_js__WEBPACK_IMPORTED_MODULE_1__[\"default\"]);\n  (0,_components_util_js__WEBPACK_IMPORTED_MODULE_0__.executeFunctions)(_checkout_shipping_js__WEBPACK_IMPORTED_MODULE_2__[\"default\"]);\n  (0,_components_util_js__WEBPACK_IMPORTED_MODULE_0__.executeFunctions)(_checkout_billing_js__WEBPACK_IMPORTED_MODULE_3__[\"default\"]);\n  (0,_components_util_js__WEBPACK_IMPORTED_MODULE_0__.executeFunctions)(_stripe_stripe_main_js__WEBPACK_IMPORTED_MODULE_4__.initStripePaymentElement);\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/checkout.js?\n}");

/***/ }),

/***/ "./client/js/checkout/billing.js":
/*!***************************************!*\
  !*** ./client/js/checkout/billing.js ***!
  \***************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _util_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./util.js */ \"./client/js/checkout/util.js\");\n\n\n\n\n/**\r\n * Initializes the \"checkout:submitPayment\" event to handle payment form submissions.\r\n */\nfunction initPaymentTrigger() {\n  $('body').on('checkout:submitPayment', function (event, data) {\n    var url = data.url,\n      formData = data.formData,\n      onSuccess = data.onSuccess,\n      onError = data.onError;\n    $.ajax({\n      url: url,\n      method: 'POST',\n      data: formData,\n      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',\n      success: function success(response) {\n        if (onSuccess && typeof onSuccess === 'function') {\n          onSuccess(response);\n        }\n      },\n      error: function error(xhr, status, _error) {\n        if (onError && typeof onError === 'function') {\n          onError(xhr, _error);\n        }\n      }\n    });\n  });\n}\n\n/**\r\n * Initializes the payment form submission handler.\r\n */\nfunction initPaymentForm() {\n  $('#paymentForm').on('submit.submitPayment', function (event) {\n    event.preventDefault();\n    var self = $(this);\n    var actionUrl = self.attr('action');\n    var formData = self.serialize();\n    $('body').trigger('checkout:submitPayment', {\n      url: actionUrl,\n      formData: formData,\n      onSuccess: function onSuccess(data) {\n        if (data.redirect) {\n          window.location.href = data.redirect;\n          return;\n        }\n        if (data.success === true) {\n          (0,_util_js__WEBPACK_IMPORTED_MODULE_0__.fillSummaryWithCartModel)(data.cartModel);\n        } else {\n          self.showErrors(data.errors);\n        }\n      },\n      onError: function onError(xhr, error) {\n        console.error('Error submitting form:', error);\n        if (xhr.status === 400 && xhr.responseJSON) {\n          self.showErrors(xhr.responseJSON);\n        }\n      }\n    });\n  });\n}\n\n/**\r\n * Triggers the \"checkout:placeOrder\" event to place the order.\r\n */\nfunction initPlaceOrderTrigger() {\n  $('body').on('checkout:placeOrder', function (event, data) {\n    var url = data.url;\n    $.ajax({\n      url: url,\n      method: 'POST',\n      success: function success(data) {\n        if (data.onSuccess && typeof data.onSuccess === 'function') {\n          data.onSuccess(data);\n        }\n      },\n      error: function error(xhr, status, _error2) {\n        if (data.onError && typeof data.onError === 'function') {\n          data.onError(_error2);\n        }\n      }\n    });\n  });\n}\n\n/**\r\n * Handles the \"Place Order\" button click event.\r\n * Sends an AJAX POST request to place the order.\r\n */\nfunction handlePlaceOrder() {\n  $('.place-order').on('click.placeOrder', function (event) {\n    event.preventDefault();\n    var actionUrl = $(this).data('action');\n    $('body').trigger('checkout:placeOrder', {\n      url: actionUrl,\n      onSuccess: function onSuccess(data) {\n        if (data.redirect) {\n          window.location.href = data.redirect;\n        }\n      },\n      onError: function onError(error) {\n        console.error('Error placing order:', error);\n      }\n    });\n  });\n}\n/* harmony default export */ __webpack_exports__[\"default\"] = ({\n  initPaymentTrigger: initPaymentTrigger,\n  initPaymentForm: initPaymentForm,\n  initPlaceOrderTrigger: initPlaceOrderTrigger,\n  handlePlaceOrder: handlePlaceOrder\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/checkout/billing.js?\n}");

/***/ }),

/***/ "./client/js/checkout/shipping.js":
/*!****************************************!*\
  !*** ./client/js/checkout/shipping.js ***!
  \****************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _util_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./util.js */ \"./client/js/checkout/util.js\");\n\n\n\n\n/**\r\n * Handles the submission of the shipping form via AJAX.\r\n * Displays validation errors next to the corresponding fields.\r\n */\nfunction handleShippingFormSubmit() {\n  $('#shippingForm').on('submit.submitShipping', function (event) {\n    event.preventDefault();\n    var form = $(this);\n    $.ajax({\n      url: form.attr('action'),\n      method: 'POST',\n      data: form.serialize(),\n      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',\n      success: function success(data) {\n        if (data.redirect) {\n          window.location.href = data.redirect;\n          return;\n        }\n        if (data.success === true) {\n          (0,_util_js__WEBPACK_IMPORTED_MODULE_0__.fillSummaryWithCartModel)(data.cartModel);\n          (0,_util_js__WEBPACK_IMPORTED_MODULE_0__.switchToStep)(2);\n        } else {\n          form.showErrors(data.errors);\n        }\n      },\n      error: function error(xhr, status, _error) {\n        console.error('Error submitting form:', _error);\n        if (xhr.status === 400 && xhr.responseJSON) {\n          form.showErrors(xhr.responseJSON);\n        }\n      }\n    });\n  });\n}\n/* harmony default export */ __webpack_exports__[\"default\"] = ({\n  handleShippingFormSubmit: handleShippingFormSubmit\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/checkout/shipping.js?\n}");

/***/ }),

/***/ "./client/js/checkout/ui.js":
/*!**********************************!*\
  !*** ./client/js/checkout/ui.js ***!
  \**********************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _util_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./util.js */ \"./client/js/checkout/util.js\");\n\n\n\n\n/**\r\n * Initializes the checkout progress bar, allowing users to navigate to previous steps.\r\n */\nfunction initCheckoutProgress() {\n  $('.checkout-progress .step').on('click', function () {\n    var step = parseInt($(this).data('step'), 10);\n    var currentStep = parseInt($('#checkoutMain').data('step'), 10);\n    if (step < currentStep) {\n      (0,_util_js__WEBPACK_IMPORTED_MODULE_0__.switchToStep)(step);\n    }\n  });\n}\n\n/**\r\n * Fills the shipping form with random data using faker-js.\r\n */\nfunction fillShippingFormWithFakeData() {\n  var faker = window.faker;\n  if (!faker) {\n    console.warn('Faker library is not loaded.');\n    return;\n  }\n  $('#firstName').val(faker.name.firstName());\n  $('#lastName').val(faker.name.lastName());\n  $('#email').val(faker.internet.email());\n  $('#phone').val(faker.phone.number({\n    style: 'international'\n  }));\n  $('#shipFirstName').val(faker.name.firstName());\n  $('#shipLastName').val(faker.name.lastName());\n  $('#city').val(faker.location.city());\n  $('#state').val(faker.location.state());\n  $('#zipCode').val(faker.location.zipCode('#####'));\n  $('#address').val(faker.location.streetAddress());\n}\n/* harmony default export */ __webpack_exports__[\"default\"] = ({\n  initCheckoutProgress: initCheckoutProgress,\n  fillShippingFormWithFakeData: fillShippingFormWithFakeData\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/checkout/ui.js?\n}");

/***/ }),

/***/ "./client/js/checkout/util.js":
/*!************************************!*\
  !*** ./client/js/checkout/util.js ***!
  \************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   fillSummaryWithCartModel: function() { return /* binding */ fillSummaryWithCartModel; },\n/* harmony export */   switchToStep: function() { return /* binding */ switchToStep; }\n/* harmony export */ });\n/* harmony import */ var _components_view_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../components/view.js */ \"./client/js/components/view.js\");\n\n\n\n\n/**\r\n * Fills the billing and shipping address data in the summary section.\r\n * @param {Object} cartModel - The cart model containing billing and shipping data.\r\n */\nfunction fillSummaryWithCartModel(cartModel) {\n  var billingCard = $('.review-summary-card .billing-card');\n  var shippingCard = $('.review-summary-card .shipping-card');\n  if (cartModel !== null && cartModel !== void 0 && cartModel.billing) {\n    billingCard.fill(cartModel.billing);\n    billingCard.fill(cartModel.billing.address);\n  }\n  if (cartModel !== null && cartModel !== void 0 && cartModel.shipping) {\n    shippingCard.fill(cartModel.shipping);\n    shippingCard.fill(cartModel.shipping.address);\n  }\n}\n\n/**\r\n * Switches to the specified checkout step.\r\n * @param {number} step - The step number to switch to (1, 2, or 3). \r\n */\nfunction switchToStep(step) {\n  $('#checkoutMain').attr('data-step', step).data('step', step);\n  if ((0,_components_view_js__WEBPACK_IMPORTED_MODULE_0__.isMobile)()) {\n    $('html, body').animate({\n      scrollTop: $(\"#checkoutMain\").offset().top\n    }, 60);\n  }\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/checkout/util.js?\n}");

/***/ }),

/***/ "./client/js/components/util.js":
/*!**************************************!*\
  !*** ./client/js/components/util.js ***!
  \**************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   executeFunctions: function() { return /* binding */ executeFunctions; }\n/* harmony export */ });\n\n\n/**\r\n * Executes all function properties of an object.\r\n * @param {Object} obj - The object containing functions as properties. \r\n */\nfunction executeFunctions(obj) {\n  if (typeof obj === 'function') {\n    obj();\n    return;\n  }\n  for (var key in obj) {\n    if (typeof obj[key] === 'function') {\n      obj[key]();\n    }\n  }\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/components/util.js?\n}");

/***/ }),

/***/ "./client/js/components/view.js":
/*!**************************************!*\
  !*** ./client/js/components/view.js ***!
  \**************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   isMobile: function() { return /* binding */ isMobile; }\n/* harmony export */ });\n\n\n/**\r\n * Checks if the device is mobile.\r\n * @returns {boolean} True if the device is mobile (screen width <= 991px), false otherwise.\r\n */\nfunction isMobile() {\n  return window.matchMedia(\"only screen and (max-width: 991px)\").matches;\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/components/view.js?\n}");

/***/ }),

/***/ "./client/js/stripe/stripe.main.js":
/*!*****************************************!*\
  !*** ./client/js/stripe/stripe.main.js ***!
  \*****************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   initStripePaymentElement: function() { return /* binding */ initStripePaymentElement; }\n/* harmony export */ });\n\n\n// Initialize Stripe with your publishable key\nfunction _regenerator() { /*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/babel/babel/blob/main/packages/babel-helpers/LICENSE */ var e, t, r = \"function\" == typeof Symbol ? Symbol : {}, n = r.iterator || \"@@iterator\", o = r.toStringTag || \"@@toStringTag\"; function i(r, n, o, i) { var c = n && n.prototype instanceof Generator ? n : Generator, u = Object.create(c.prototype); return _regeneratorDefine2(u, \"_invoke\", function (r, n, o) { var i, c, u, f = 0, p = o || [], y = !1, G = { p: 0, n: 0, v: e, a: d, f: d.bind(e, 4), d: function d(t, r) { return i = t, c = 0, u = e, G.n = r, a; } }; function d(r, n) { for (c = r, u = n, t = 0; !y && f && !o && t < p.length; t++) { var o, i = p[t], d = G.p, l = i[2]; r > 3 ? (o = l === n) && (u = i[(c = i[4]) ? 5 : (c = 3, 3)], i[4] = i[5] = e) : i[0] <= d && ((o = r < 2 && d < i[1]) ? (c = 0, G.v = n, G.n = i[1]) : d < l && (o = r < 3 || i[0] > n || n > l) && (i[4] = r, i[5] = n, G.n = l, c = 0)); } if (o || r > 1) return a; throw y = !0, n; } return function (o, p, l) { if (f > 1) throw TypeError(\"Generator is already running\"); for (y && 1 === p && d(p, l), c = p, u = l; (t = c < 2 ? e : u) || !y;) { i || (c ? c < 3 ? (c > 1 && (G.n = -1), d(c, u)) : G.n = u : G.v = u); try { if (f = 2, i) { if (c || (o = \"next\"), t = i[o]) { if (!(t = t.call(i, u))) throw TypeError(\"iterator result is not an object\"); if (!t.done) return t; u = t.value, c < 2 && (c = 0); } else 1 === c && (t = i[\"return\"]) && t.call(i), c < 2 && (u = TypeError(\"The iterator does not provide a '\" + o + \"' method\"), c = 1); i = e; } else if ((t = (y = G.n < 0) ? u : r.call(n, G)) !== a) break; } catch (t) { i = e, c = 1, u = t; } finally { f = 1; } } return { value: t, done: y }; }; }(r, o, i), !0), u; } var a = {}; function Generator() {} function GeneratorFunction() {} function GeneratorFunctionPrototype() {} t = Object.getPrototypeOf; var c = [][n] ? t(t([][n]())) : (_regeneratorDefine2(t = {}, n, function () { return this; }), t), u = GeneratorFunctionPrototype.prototype = Generator.prototype = Object.create(c); function f(e) { return Object.setPrototypeOf ? Object.setPrototypeOf(e, GeneratorFunctionPrototype) : (e.__proto__ = GeneratorFunctionPrototype, _regeneratorDefine2(e, o, \"GeneratorFunction\")), e.prototype = Object.create(u), e; } return GeneratorFunction.prototype = GeneratorFunctionPrototype, _regeneratorDefine2(u, \"constructor\", GeneratorFunctionPrototype), _regeneratorDefine2(GeneratorFunctionPrototype, \"constructor\", GeneratorFunction), GeneratorFunction.displayName = \"GeneratorFunction\", _regeneratorDefine2(GeneratorFunctionPrototype, o, \"GeneratorFunction\"), _regeneratorDefine2(u), _regeneratorDefine2(u, o, \"Generator\"), _regeneratorDefine2(u, n, function () { return this; }), _regeneratorDefine2(u, \"toString\", function () { return \"[object Generator]\"; }), (_regenerator = function _regenerator() { return { w: i, m: f }; })(); }\nfunction _regeneratorDefine2(e, r, n, t) { var i = Object.defineProperty; try { i({}, \"\", {}); } catch (e) { i = 0; } _regeneratorDefine2 = function _regeneratorDefine(e, r, n, t) { function o(r, n) { _regeneratorDefine2(e, r, function (e) { return this._invoke(r, n, e); }); } r ? i ? i(e, r, { value: n, enumerable: !t, configurable: !t, writable: !t }) : e[r] = n : (o(\"next\", 0), o(\"throw\", 1), o(\"return\", 2)); }, _regeneratorDefine2(e, r, n, t); }\nfunction asyncGeneratorStep(n, t, e, r, o, a, c) { try { var i = n[a](c), u = i.value; } catch (n) { return void e(n); } i.done ? t(u) : Promise.resolve(u).then(r, o); }\nfunction _asyncToGenerator(n) { return function () { var t = this, e = arguments; return new Promise(function (r, o) { var a = n.apply(t, e); function _next(n) { asyncGeneratorStep(a, r, o, _next, _throw, \"next\", n); } function _throw(n) { asyncGeneratorStep(a, r, o, _next, _throw, \"throw\", n); } _next(void 0); }); }; }\nvar stripe;\n\n/**\r\n * Initialize the Stripe Payment Element\r\n * This function fetches a client secret from the server and sets up the Payment Element\r\n */\nfunction initStripePaymentElement() {\n  var paymentEl = $('#paymentElement');\n  if (paymentEl.length === 0) {\n    console.warn('Payment Element container not found.');\n    return;\n  }\n  var stripeApiKey = paymentEl.data('stripe-api-key');\n  if (!stripeApiKey) {\n    console.error('Stripe API key is not set.');\n    return;\n  }\n  stripe = Stripe(stripeApiKey);\n  $.ajax({\n    url: paymentEl.data('create-payment-intent-url'),\n    method: 'POST',\n    contentType: 'application/json',\n    data: JSON.stringify({}),\n    success: function success(data) {\n      setupPaymentElement(data.clientSecret);\n    },\n    error: function error(xhr, status, _error) {\n      console.error('Error fetching client secret:', _error);\n    }\n  });\n}\n\n/**\r\n * Confirms the payment using Stripe's confirmPayment method\r\n * @param {object} elements - The Stripe Elements instance\r\n * @param {object} cartModel - The cart model containing item details\r\n */\nfunction confirmPayment(_x, _x2) {\n  return _confirmPayment.apply(this, arguments);\n}\n/**\r\n * Submits the payment form data to the server\r\n * @param {string} url - The URL to submit the payment form to\r\n * @param {object} formData - The serialized form data\r\n * @returns {Promise} - A promise that resolves with the server response\r\n */\nfunction _confirmPayment() {\n  _confirmPayment = _asyncToGenerator(/*#__PURE__*/_regenerator().m(function _callee2(elements, cartModel) {\n    var paymentForm, returnUrl, _yield$stripe$confirm, error;\n    return _regenerator().w(function (_context2) {\n      while (1) switch (_context2.n) {\n        case 0:\n          paymentForm = $('#paymentForm');\n          returnUrl = new URL($('#paymentElement').data('return-url'), window.location.origin); // Confirm the payment with Stripe\n          _context2.n = 1;\n          return stripe.confirmPayment({\n            elements: elements,\n            confirmParams: {\n              return_url: returnUrl.href\n            }\n          });\n        case 1:\n          _yield$stripe$confirm = _context2.v;\n          error = _yield$stripe$confirm.error;\n          if (error) {\n            paymentForm.showErrors({\n              stripe: error.message\n            });\n          }\n        case 2:\n          return _context2.a(2);\n      }\n    }, _callee2);\n  }));\n  return _confirmPayment.apply(this, arguments);\n}\nfunction submitPayment(url, formData) {\n  return new Promise(function (resolve, reject) {\n    $.ajax({\n      url: url,\n      method: 'POST',\n      data: formData,\n      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',\n      success: function success(response) {\n        resolve(response);\n      },\n      error: function error(xhr, status, _error2) {\n        reject({\n          xhr: xhr,\n          error: _error2\n        });\n      }\n    });\n  });\n}\n\n/**\r\n * Initializes the payment form submission handler.\r\n * @param {object} elements - The Stripe Elements instance\r\n */\nfunction initPaymentForm(elements) {\n  $('#paymentForm').off('submit').on('submit', /*#__PURE__*/function () {\n    var _ref = _asyncToGenerator(/*#__PURE__*/_regenerator().m(function _callee(event) {\n      var self, actionUrl, formData, result, response;\n      return _regenerator().w(function (_context) {\n        while (1) switch (_context.n) {\n          case 0:\n            event.preventDefault();\n            self = $(this);\n            actionUrl = self.attr('action');\n            formData = self.serialize();\n            _context.n = 1;\n            return elements.submit();\n          case 1:\n            result = _context.v;\n            if (!result.error) {\n              _context.n = 2;\n              break;\n            }\n            self.showErrors({\n              stripe: result.error.message\n            });\n            return _context.a(2);\n          case 2:\n            _context.n = 3;\n            return submitPayment(actionUrl, formData);\n          case 3:\n            response = _context.v;\n            if (!response.redirect) {\n              _context.n = 4;\n              break;\n            }\n            window.location.href = response.redirect;\n            return _context.a(2);\n          case 4:\n            if (!(response.success === true)) {\n              _context.n = 6;\n              break;\n            }\n            _context.n = 5;\n            return confirmPayment(elements, response.cartModel);\n          case 5:\n            _context.n = 7;\n            break;\n          case 6:\n            self.showErrors({\n              stripe: response.error || 'Payment failed. Please try again.'\n            });\n          case 7:\n            return _context.a(2);\n        }\n      }, _callee, this);\n    }));\n    return function (_x3) {\n      return _ref.apply(this, arguments);\n    };\n  }());\n}\n\n/**\r\n * Sets up the Stripe Payment Element and handles form submission\r\n * @param {string} clientSecret - The client secret obtained from the server\r\n */\nfunction setupPaymentElement(clientSecret) {\n  // Create an elements instance with options\n  var elements = stripe.elements({\n    clientSecret: clientSecret,\n    appearance: {\n      theme: 'stripe',\n      variables: {\n        colorPrimary: '#0570de'\n      }\n    }\n  });\n\n  // Create and mount the Payment Element\n  var paymentElement = elements.create('payment', {\n    layout: 'tabs'\n  });\n  paymentElement.mount('#paymentElement');\n  initPaymentForm(elements);\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/stripe/stripe.main.js?\n}");

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