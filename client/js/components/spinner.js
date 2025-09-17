'use strict';

/**
 * Initializes spinner functionality for jQuery elements and the page.
 */
export function initSpinnerHandler() {
    // Global start function
    function startSpinner($container, isPageLevel = false) {
        const $backdrop = $('<div>')
            .css({
                position: isPageLevel ? 'fixed' : 'absolute',
                top: 0,
                left: 0,
                width: isPageLevel ? '100%' : $container.outerWidth(),
                height: isPageLevel ? '100%' : $container.outerHeight(),
                backgroundColor: 'rgba(255, 255, 255, 0.8)',
                zIndex: 9998
            })
            .addClass('spinner-backdrop');

        const $spinner = $('<div>')
            .css({
                position: isPageLevel ? 'fixed' : 'absolute',
                top: '50%',
                left: '50%',
                width: '40px',
                height: '40px',
                margin: '-20px 0 0 -20px',
                border: '4px solid #000',
                borderTop: '4px solid transparent',
                borderRadius: '50%',
                animation: 'spin 1s linear infinite',
                zIndex: 9999
            })
            .addClass('spinner');

        $container.append($backdrop).append($spinner);
    }

    // Global stop function
    function stopSpinner($container) {
        $container.find('.spinner-backdrop, .spinner').remove();
    }

    // Element-level spinner
    $.fn.spinner = function () {
        return {
            start: () => {
                this.each(function () {
                    startSpinner($(this));
                });
            },
            stop: () => {
                this.each(function () {
                    stopSpinner($(this));
                });
            }
        };
    };

    // Page-level spinner
    $.spinner = function () {
        return {
            start: () => {
                startSpinner($('body'), true);
            },
            stop: () => {
                stopSpinner($('body'));
            }
        };
    };

    // Add keyframes for spinner animation
    const style = document.createElement('style');
    style.type = 'text/css';
    style.innerHTML = `
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    `;
    document.head.appendChild(style);
}
