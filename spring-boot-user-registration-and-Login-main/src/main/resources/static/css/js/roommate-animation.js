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
});