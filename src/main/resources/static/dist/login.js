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

/***/ "./client/js/components/error.js":
/*!***************************************!*\
  !*** ./client/js/components/error.js ***!
  \***************************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   showErrors: function() { return /* binding */ showErrors; }\n/* harmony export */ });\n\n\n/**\r\n * Displays validation errors for the shipping form.\r\n * @param {*} errors \r\n */\nfunction showErrors(errors) {\n  for (var field in errors) {\n    $(\"#\" + field + \"Error\").text(errors[field]);\n  }\n}\n\n//# sourceURL=webpack://b2cstore-java/./client/js/components/error.js?\n}");

/***/ }),

/***/ "./client/js/login.js":
/*!****************************!*\
  !*** ./client/js/login.js ***!
  \****************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("{__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _components_error_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./components/error.js */ \"./client/js/components/error.js\");\n\n\n\n\n/**\r\n * Initializes the registration form submission handler.\r\n */\nfunction initRegisterForm() {\n  $(\"form#registerForm\").on(\"submit\", function (event) {\n    event.preventDefault();\n    var self = $(this);\n    var formData = self.serialize();\n    $(\".error\").text(\"\");\n    $.ajax({\n      url: self.attr(\"action\"),\n      type: \"POST\",\n      data: formData,\n      success: function success(response) {\n        if (response.success) {\n          window.location.href = response.redirect;\n        } else if (response.errors) {\n          (0,_components_error_js__WEBPACK_IMPORTED_MODULE_0__.showErrors)(response.errors);\n        }\n      },\n      error: function error(xhr) {\n        var _xhr$responseJSON;\n        var errors = ((_xhr$responseJSON = xhr.responseJSON) === null || _xhr$responseJSON === void 0 ? void 0 : _xhr$responseJSON.errors) || {\n          general: \"An unexpected error occurred.\"\n        };\n        (0,_components_error_js__WEBPACK_IMPORTED_MODULE_0__.showErrors)(errors);\n      }\n    });\n  });\n}\n\n/**\r\n * Fills the registration form with dummy data for testing purposes.\r\n */\nfunction fillRegisterFormWithDummyData() {\n  var faker = window.faker;\n  if (!faker) {\n    console.warn('Faker library is not loaded.');\n    return;\n  }\n  if ($(\"#registerForm\").length === 0) {\n    return;\n  }\n  var password = \"Testing@123\";\n  var firstName = faker.person.firstName();\n  var lastName = faker.person.lastName();\n  var username = faker.internet.displayName({\n    firstName: firstName,\n    lastName: lastName\n  });\n  var fakerData = {\n    firstName: firstName,\n    lastName: lastName,\n    email: faker.internet.email(),\n    username: username,\n    password: password,\n    confirmPassword: password,\n    phone: faker.phone.number({\n      style: 'international'\n    })\n  };\n  $(\"#firstName\").val(fakerData.firstName);\n  $(\"#lastName\").val(fakerData.lastName);\n  $(\"#email\").val(fakerData.email);\n  $(\"#username\").val(fakerData.username);\n  $(\"#password\").val(fakerData.password);\n  $(\"#confirmPassword\").val(fakerData.confirmPassword);\n  $(\"#phone\").val(fakerData.phone);\n}\n$(document).ready(function () {\n  initRegisterForm();\n  fillRegisterFormWithDummyData();\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/login.js?\n}");

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
/******/ 	var __webpack_exports__ = __webpack_require__("./client/js/login.js");
/******/ 	
/******/ })()
;