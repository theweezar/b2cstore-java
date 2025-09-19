// Mobile Menu Toggle
const menuToggle = document.getElementById('menu-toggle');
const nav = document.getElementById('nav');

menuToggle.addEventListener('click', () => {
    nav.classList.toggle('active');

    // Change hamburger icon to X when menu is open
    const icon = menuToggle.querySelector('i');
    if (nav.classList.contains('active')) {
        icon.classList.remove('fa-bars');
        icon.classList.add('fa-times');
    } else {
        icon.classList.remove('fa-times');
        icon.classList.add('fa-bars');
    }
});

// Close menu when clicking on nav links (mobile)
const navLinks = nav.querySelectorAll('a');
navLinks.forEach(link => {
    link.addEventListener('click', () => {
        nav.classList.remove('active');
        const icon = menuToggle.querySelector('i');
        icon.classList.remove('fa-times');
        icon.classList.add('fa-bars');
    });
});

// Add to Cart functionality
const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');
const cartBadge = document.querySelector('.action-btn .badge');
let cartCount = 0;

addToCartButtons.forEach(button => {
    button.addEventListener('click', (e) => {
        e.preventDefault();
        cartCount++;
        cartBadge.textContent = cartCount;

        // Visual feedback
        button.style.backgroundColor = '#10b981';
        button.textContent = 'Added!';

        setTimeout(() => {
            button.style.backgroundColor = '#2563eb';
            button.textContent = 'Add to Cart';
        }, 1000);
    });
});

// Newsletter form submission
const newsletterForm = document.querySelector('.newsletter-form');
newsletterForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const email = e.target.querySelector('input[type="email"]').value;

    if (email) {
        alert('Thank you for subscribing!');
        e.target.reset();
    }
});

// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Header scroll effect
let lastScrollTop = 0;
const header = document.querySelector('.header');

window.addEventListener('scroll', () => {
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

    // Add shadow on scroll
    if (scrollTop > 10) {
        header.style.boxShadow = '0 2px 10px rgba(0,0,0,0.1)';
    } else {
        header.style.boxShadow = 'none';
    }

    lastScrollTop = scrollTop;
});

// Search functionality (basic)
const searchInput = document.querySelector('.search-bar input');
const searchButton = document.querySelector('.search-bar button');

searchButton.addEventListener('click', () => {
    const searchTerm = searchInput.value.trim();
    if (searchTerm) {
        alert(`Searching for: ${searchTerm}`);
        // In a real application, this would trigger a search API call
    }
});

searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        searchButton.click();
    }
});

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    console.log('B2C Store homepage loaded successfully!');

    // Set initial cart count
    cartBadge.textContent = cartCount;

    // Add loading animation end
    document.body.style.opacity = '1';
});

$(document).ready(function () {
    console.log('B2C DEMO homepage loaded successfully!');

    // Initialize variables
    let cartCount = 0;
    let currentSlide = 0;
    const totalSlides = $('.product-slide').length;

    // Mobile Search Toggle
    $('#searchToggle').on('click', function () {
        $('#mobileSearch').toggleClass('show');
    });

    // Mobile Sidebar Toggle
    $('#sidebarToggle').on('click', function () {
        $('#sidebarOverlay').addClass('show');
        $('#mobileSidebar').addClass('show');
        $('body').addClass('overflow-hidden');
    });

    // Close Sidebar
    function closeSidebar() {
        $('#sidebarOverlay').removeClass('show');
        $('#mobileSidebar').removeClass('show');
        $('body').removeClass('overflow-hidden');
    }

    $('#sidebarClose, #sidebarOverlay').on('click', closeSidebar);

    // Close sidebar when clicking nav links
    $('.mobile-sidebar .nav-link').on('click', function () {
        closeSidebar();
    });

    // Product Slider Functionality
    function updateSlider() {
        const translateX = -currentSlide * 100;
        $('.product-slider').css('transform', `translateX(${translateX}%)`);
    }

    $('#nextSlide').on('click', function () {
        currentSlide = (currentSlide + 1) % totalSlides;
        updateSlider();
    });

    $('#prevSlide').on('click', function () {
        currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
        updateSlider();
    });

    // Auto-slide products every 5 seconds
    setInterval(function () {
        currentSlide = (currentSlide + 1) % totalSlides;
        updateSlider();
    }, 5000);

    // Add to Cart Functionality
    $('.add-to-cart-btn').on('click', function (e) {
        e.preventDefault();

        const $button = $(this);
        const originalText = $button.text();

        // Update cart count
        cartCount++;
        $('.cart-badge').text(cartCount);

        // Visual feedback
        $button.removeClass('btn-outline-primary')
            .addClass('btn-success')
            .text('Added!')
            .prop('disabled', true);

        // Reset button after 1 second
        setTimeout(function () {
            $button.removeClass('btn-success')
                .addClass('btn-outline-primary')
                .text(originalText)
                .prop('disabled', false);
        }, 1000);

        // Add bounce animation to cart icon
        $('.action-btn .fas.fa-shopping-cart').parent().addClass('animate__animated animate__bounce');
        setTimeout(function () {
            $('.action-btn .fas.fa-shopping-cart').parent().removeClass('animate__animated animate__bounce');
        }, 1000);
    });

    // Newsletter Form Submission
    $('.newsletter-form').on('submit', function (e) {
        e.preventDefault();

        const email = $(this).find('input[type="email"]').val();
        const $button = $(this).find('.subscribe-btn');
        const originalText = $button.text();

        if (email) {
            // Show loading state
            $button.text('Subscribing...').prop('disabled', true);

            // Simulate API call
            setTimeout(function () {
                $button.text('Subscribed!').removeClass('subscribe-btn').addClass('btn-success');

                // Reset form and button after 2 seconds
                setTimeout(function () {
                    $button.text(originalText).removeClass('btn-success').addClass('subscribe-btn').prop('disabled', false);
                    $('.newsletter-form')[0].reset();
                }, 2000);
            }, 1000);
        }
    });

    // Search Functionality
    $('.search-btn').on('click', function () {
        const searchTerm = $(this).siblings('.search-input').val().trim();
        if (searchTerm) {
            // In a real application, this would trigger a search API call
            console.log(`Searching for: ${searchTerm}`);
            // You can replace this with actual search functionality
            alert(`Searching for: ${searchTerm}`);
        }
    });

    // Search on Enter key
    $('.search-input').on('keypress', function (e) {
        if (e.which === 13) { // Enter key
            $(this).siblings('.search-btn').click();
        }
    });

    // Smooth Scrolling for Navigation Links
    $('a[href^="#"]').on('click', function (e) {
        const target = $(this.getAttribute('href'));
        if (target.length) {
            e.preventDefault();
            $('html, body').animate({
                scrollTop: target.offset().top - 80 // Account for sticky header
            }, 800);
        }
    });

    // Header Scroll Effect
    let lastScrollTop = 0;
    $(window).on('scroll', function () {
        const scrollTop = $(this).scrollTop();

        // Add shadow on scroll
        if (scrollTop > 10) {
            $('.header').addClass('scrolled');
        } else {
            $('.header').removeClass('scrolled');
        }

        lastScrollTop = scrollTop;
    });

    // Category Card Hover Effects
    $('.category-card').on('mouseenter', function () {
        $(this).find('.category-icon').addClass('animate__animated animate__pulse');
    }).on('mouseleave', function () {
        $(this).find('.category-icon').removeClass('animate__animated animate__pulse');
    });

    // Product Card Hover Effects
    $('.product-card').on('mouseenter', function () {
        $(this).addClass('shadow-lg');
    }).on('mouseleave', function () {
        $(this).removeClass('shadow-lg');
    });

    // Initialize Hero Carousel
    $('#heroCarousel').carousel({
        interval: 4000,
        ride: 'carousel'
    });

    // Window Resize Handler
    $(window).on('resize', function () {
        // Close mobile elements on resize to desktop
        if ($(window).width() >= 992) {
            closeSidebar();
            $('#mobileSearch').removeClass('show');
        }
    });

    // Initialize cart badge
    $('.cart-badge').text(cartCount);

    // Add custom CSS for scrolled header
    const style = document.createElement('style');
    style.textContent = `
        .header.scrolled {
            box-shadow: 0 4px 20px rgba(0,0,0,0.1) !important;
        }
    `;
    document.head.appendChild(style);

    console.log('All JavaScript functionality initialized successfully!');
});

// Additional utility functions
function showNotification(message, type = 'success') {
    // Simple notification system
    const notification = $(`
        <div class="alert alert-${type} alert-dismissible fade show position-fixed" 
             style="top: 100px; right: 20px; z-index: 9999;">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `);

    $('body').append(notification);

    // Auto-remove after 3 seconds
    setTimeout(function () {
        notification.alert('close');
    }, 3000);
}

// Handle loading states
function setLoadingState(element, isLoading, originalText) {
    const $element = $(element);
    if (isLoading) {
        $element.prop('disabled', true)
            .data('original-text', originalText)
            .html('<i class="fas fa-spinner fa-spin me-2"></i>Loading...');
    } else {
        $element.prop('disabled', false)
            .html($element.data('original-text'));
    }
}
