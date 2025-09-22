'use strict';

/**
 * Sends a POST request with form-encoded data to the specified URL.
 * @param {string} url - The URL to which the POST request is sent.
 * @param {Object} data - The data to be sent in the request body, encoded as `application/x-www-form-urlencoded`.
 * @returns {Promise<any>} A Promise that resolves with the response data on success, or rejects with an error on failure.
 */
export function postForm(url, data) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            method: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function (response) {
                if (response !== undefined && response !== null) {
                    resolve(response);
                } else {
                    resolve({});
                }
            },
            error: function (xhr, status, err) {
                const error = xhr.responseJSON?.error || { general: err || "An unexpected error occurred." };
                reject({ xhr, error });
            }
        });
    });
}

/**
 * Sends a POST request with JSON data to the specified URL.
 * @param {string} url - The URL to which the POST request is sent.
 * @param {Object} data - The data to be sent in the request body, encoded as JSON.
 * @returns {Promise<any>} A Promise that resolves with the response data on success, or rejects with an error on failure.
 */
export function postJSON(url, data) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            method: 'POST',
            data: typeof data === 'string' ? data : JSON.stringify(data),
            contentType: 'application/json',
            success: function (response) {
                if (response !== undefined && response !== null) {
                    resolve(response);
                } else {
                    resolve({});
                }
            },
            error: function (xhr, status, err) {
                const error = xhr.responseJSON?.error || { general: err || "An unexpected error occurred." };
                reject({ xhr, error });
            }
        });
    });
}
