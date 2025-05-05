// Text animation for hero section
const changingText = document.querySelector('.changing-text');
const texts = [
    { text: 'Perfect Roommate', color: '#ffffff' },
    { text: 'Dream RoomPartner', color: '#ffd700' },
    { text: 'Ideal Match', color: '#ff69b4' },
    { text: 'Great Roommate', color: '#98fb98' }
];
let currentIndex = 0;

function changeText() {
    changingText.classList.add('fade');
    
    setTimeout(() => {
        currentIndex = (currentIndex + 1) % texts.length;
        changingText.textContent = texts[currentIndex].text;
        changingText.style.color = texts[currentIndex].color;
        changingText.classList.remove('fade');
    }, 300);
}

setInterval(changeText, 5000);

// Mobile menu toggle
const hamburger = document.querySelector('.hamburger');
const navLinks = document.querySelector('.nav-links');
const navAuth = document.querySelector('.nav-auth');

hamburger.addEventListener('click', () => {
    navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
    navAuth.style.display = navAuth.style.display === 'flex' ? 'none' : 'flex';
});

// Search functionality
const searchBtn = document.querySelector('.search-btn');
searchBtn.addEventListener('click', () => {
    const city = document.querySelector('input[placeholder="Enter city or area"]').value;
    const budget = document.querySelector('input[placeholder="Budget (max)"]').value;
    
    // For demonstration purposes, just log the search parameters
    console.log('Searching for rooms in:', city);
    console.log('Maximum budget:', budget);
});

// Contact form submission
const contactForm = document.querySelector('.contact-form');
contactForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const formData = new FormData(contactForm);
    // For demonstration purposes, just log the form data
    console.log('Form submitted:', Object.fromEntries(formData));
    contactForm.reset();
    alert('Thank you for your message! We will get back to you soon.');
});

// Newsletter subscription
const newsletterForm = document.querySelector('.newsletter-form');
newsletterForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const email = newsletterForm.querySelector('input[type="email"]').value;
    // For demonstration purposes, just log the email
    console.log('Newsletter subscription:', email);
    newsletterForm.reset();
    alert('Thank you for subscribing to our newsletter!');
});

// Responsive navigation
window.addEventListener('resize', () => {
    if (window.innerWidth > 968) {
        navLinks.style.display = 'flex';
        navAuth.style.display = 'flex';
    } else {
        navLinks.style.display = 'none';
        navAuth.style.display = 'none';
    }
});
// Add smooth scrolling to all links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', function (e) {
    e.preventDefault();
    document.querySelector(this.getAttribute('href')).scrollIntoView({
      behavior: 'smooth'
    });
  });
});

// Enhance modal interactions
document.addEventListener('DOMContentLoaded', function() {
  // Close modal when clicking outside
  document.querySelectorAll('dialog').forEach(dialog => {
    dialog.addEventListener('click', e => {
      const dialogDimensions = dialog.getBoundingClientRect();
      if (
        e.clientX < dialogDimensions.left ||
        e.clientX > dialogDimensions.right ||
        e.clientY < dialogDimensions.top ||
        e.clientY > dialogDimensions.bottom
      ) {
        dialog.close();
      }
    });
  });

  // Close modal with Escape key
  document.addEventListener('keydown', e => {
    if (e.key === 'Escape') {
      document.querySelectorAll('dialog[open]').forEach(dialog => dialog.close());
    }
  });

  // Add loading animation to images
  document.querySelectorAll('.buddy-image img').forEach(img => {
    img.style.opacity = '0';
    img.style.transition = 'opacity 0.3s ease-in';

    img.addEventListener('load', function() {
      this.style.opacity = '1';
    });
  });

  // Enhance button interactions
  document.querySelectorAll('button').forEach(button => {
    button.addEventListener('mousedown', function() {
      this.style.transform = 'scale(0.98)';
    });

    button.addEventListener('mouseup', function() {
      this.style.transform = 'scale(1)';
    });

    button.addEventListener('mouseleave', function() {
      this.style.transform = 'scale(1)';
    });
  });

  // Add intersection observer for card animations
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.style.opacity = '1';
        entry.target.style.transform = 'translateY(0)';
      }
    });
  }, {
    threshold: 0.1
  });

  document.querySelectorAll('.buddy-card').forEach(card => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(20px)';
    card.style.transition = 'opacity 0.5s ease-out, transform 0.5s ease-out';
    observer.observe(card);
  });

  // Create success message elements
  const successOverlay = document.createElement('div');
  successOverlay.className = 'success-overlay';

  const successMessage = document.createElement('div');
  successMessage.className = 'success-message';
  successMessage.innerHTML = `
    <div class="success-icon"></div>
    <h3 class="success-title">Successfully Connected!</h3>
    <p class="success-description">Your request has been sent to the roommate. They will be notified of your interest in connecting.</p>
    <button class="success-button">Got it!</button>
  `;

  successOverlay.appendChild(successMessage);
  document.body.appendChild(successOverlay);

  // Handle form submission
  document.querySelectorAll('form[action*="/roommate/connect"]').forEach(form => {
    form.addEventListener('submit', function(e) {
      e.preventDefault();

      // Show success message with animation
      successOverlay.classList.add('show');
      setTimeout(() => successMessage.classList.add('show'), 10);

      // Submit the form data
      fetch(this.action, {
        method: 'POST',
        body: new FormData(this)
      }).then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
      }).catch(error => {
        console.error('Error:', error);
      });
    });
  });

  // Close success message
  successMessage.querySelector('.success-button').addEventListener('click', () => {
    successOverlay.classList.remove('show');
    successMessage.classList.remove('show');
  });

  // Close on overlay click
  successOverlay.addEventListener('click', (e) => {
    if (e.target === successOverlay) {
      successOverlay.classList.remove('show');
      successMessage.classList.remove('show');
    }
  });

  // Close on escape key
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && successOverlay.classList.contains('show')) {
      successOverlay.classList.remove('show');
      successMessage.classList.remove('show');
    }
  });
});