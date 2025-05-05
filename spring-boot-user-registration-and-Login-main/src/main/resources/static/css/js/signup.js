// Mobile menu toggle
const hamburger = document.querySelector('.hamburger');
const navLinks = document.querySelector('.nav-links');
const navAuth = document.querySelector('.nav-auth');

hamburger.addEventListener('click', () => {
    navLinks.classList.toggle('active');
    navAuth.classList.toggle('active');
});

// Close mobile menu when clicking outside
document.addEventListener('click', (e) => {
    if (!e.target.closest('.navbar')) {
        navLinks.classList.remove('active');
        navAuth.classList.remove('active');
    }
});

// Password visibility toggle
const togglePassword = document.querySelector('.toggle-password');
const passwordInput = document.querySelector('#password');

togglePassword.addEventListener('click', () => {
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordInput.setAttribute('type', type);
    togglePassword.classList.toggle('fa-eye');
    togglePassword.classList.toggle('fa-eye-slash');
});


// Handle window resize
window.addEventListener('resize', () => {
    if (window.innerWidth > 968) {
        navLinks.classList.remove('active');
        navAuth.classList.remove('active');
    }
});