'use strict';

/**
 * Appends the content of the source object to the target element.
 * @param {JQuery<HTMLElement>} target - The target element to which content will be appended.
 * @param {Object} source - The source object containing content to append.
 */
export function toElement(target, source) {
    for (let key in source) {
        const element = target.find(`.${key}`);
        if (element.length > 0) {
            element.text(source[key]);
        }
    }
}