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

/***/ "./client/js/search.js":
/*!*****************************!*\
  !*** ./client/js/search.js ***!
  \*****************************/
/***/ (function() {

eval("{\n\n/**\r\n * Adds a product to the shopping cart.\r\n * @param {string} pid - The product ID to add.\r\n */\nfunction addToCart(pid) {\n  $('body').spinner().start();\n  $.ajax({\n    url: '/addtocart',\n    method: 'POST',\n    data: JSON.stringify({\n      pid: pid,\n      quantity: 1\n    }),\n    contentType: 'application/json; charset=UTF-8',\n    success: function success(response) {\n      $('body').spinner().stop();\n      if (response.status) {\n        // alert('Product added to cart successfully.');\n        $('.minicart-show .cart-count').text(response.cartModel.itemCount);\n      } else if (response.productStatus) {\n        alert(\"Product status: \".concat(response.productStatus));\n      } else {\n        alert('Failed to add product to cart.');\n      }\n    },\n    error: function error() {\n      $('body').spinner().stop();\n      alert('An error occurred while adding the product to cart.');\n    }\n  });\n}\n\n/**\r\n * Initializes event listeners for \"Add to Cart\" buttons.\r\n */\nfunction initAddToCartButtons() {\n  var addToCartBtn = $('.btn.btn-atc');\n  addToCartBtn.on('click.addToCart', function (event) {\n    event.preventDefault();\n    var self = $(this);\n    var pid = self.parents('.product-tile-card').data('pid');\n    if (pid) {\n      addToCart(pid);\n    }\n  });\n}\n$(document).ready(function () {\n  initAddToCartButtons();\n});\n\n//# sourceURL=webpack://b2cstore-java/./client/js/search.js?\n}");

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval devtool is used.
/******/ 	var __webpack_exports__ = {};
/******/ 	__webpack_modules__["./client/js/search.js"]();
/******/ 	
/******/ })()
;