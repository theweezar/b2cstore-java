'use strict';

/**
 * Executes all function properties of an object.
 * @param {Object} obj - The object containing functions as properties. 
 */
export function executeFunctions(obj) {
    if (typeof obj === 'function') {
        obj();
        return;
    }
    for (const key in obj) {
        if (typeof obj[key] === 'function') {
            obj[key]();
        }
    }
}