// Particle system
class Particle {
  constructor(canvas, options = {}) {
    this.canvas = canvas;
    this.x = options.x || Math.random() * canvas.width;
    this.y = options.y || Math.random() * canvas.height;
    this.size = Math.random() * 2 + 1;
    this.speedX = Math.random() * 1 - 0.5;
    this.speedY = Math.random() * 1 - 0.5;
    this.color = options.color || '#007AFF';
    this.alpha = Math.random() * 0.5 + 0.1;
    this.originalAlpha = this.alpha;
  }

  draw(ctx) {
    ctx.beginPath();
    ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
    ctx.fillStyle = this.color + Math.floor(this.alpha * 255).toString(16).padStart(2, '0');
    ctx.fill();
  }

  update(mouseX, mouseY) {
    this.x += this.speedX;
    this.y += this.speedY;

    // Boundary check
    if (this.x > this.canvas.width || this.x < 0) {
      this.speedX = -this.speedX;
    }
    if (this.y > this.canvas.height || this.y < 0) {
      this.speedY = -this.speedY;
    }

    // Mouse interaction
    if (mouseX && mouseY) {
      const dx = mouseX - this.x;
      const dy = mouseY - this.y;
      const distance = Math.sqrt(dx * dx + dy * dy);
      const maxDistance = 100;

      if (distance < maxDistance) {
        const force = (maxDistance - distance) / maxDistance;
        const angle = Math.atan2(dy, dx);
        this.speedX = -Math.cos(angle) * force * 0.5;
        this.speedY = -Math.sin(angle) * force * 0.5;
        this.alpha = this.originalAlpha + force * 0.5;
      } else {
        this.alpha = this.originalAlpha;
      }
    }
  }
}

// Initialize particles
const initParticles = () => {
  const particlesContainer = document.getElementById('particles');
  const canvas = document.createElement('canvas');
  const ctx = canvas.getContext('2d');

  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;

  particlesContainer.appendChild(canvas);

  const particles = [];
  const particleCount = Math.min(Math.floor(window.innerWidth * window.innerHeight / 10000), 100);

  for (let i = 0; i < particleCount; i++) {
    particles.push(new Particle(canvas, {
      color: '#007AFF'
    }));
  }

  let mouseX = undefined;
  let mouseY = undefined;

  window.addEventListener('mousemove', (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;
  });

  window.addEventListener('touchmove', (e) => {
    if (e.touches[0]) {
      mouseX = e.touches[0].clientX;
      mouseY = e.touches[0].clientY;
    }
  });

  window.addEventListener('mouseout', () => {
    mouseX = undefined;
    mouseY = undefined;
  });

  window.addEventListener('resize', () => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
  });

  function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    particles.forEach(particle => {
      particle.update(mouseX, mouseY);
      particle.draw(ctx);
    });

    // Connect particles with lines if they're close
    particles.forEach((a, i) => {
      particles.slice(i + 1).forEach(b => {
        const dx = a.x - b.x;
        const dy = a.y - b.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 100) {
          ctx.beginPath();
          ctx.strokeStyle = `rgba(0, 122, 255, ${0.1 * (1 - distance / 100)})`;
          ctx.lineWidth = 0.5;
          ctx.moveTo(a.x, a.y);
          ctx.lineTo(b.x, b.y);
          ctx.stroke();
        }
      });
    });

    requestAnimationFrame(animate);
  }

  animate();
};

// Error code generator and typing effect
const generateErrorCode = () => {
  const errorCodeElement = document.getElementById('errorCode');
  const characters = '0123456789ABCDEF';
  const codeLength = 8;
  let errorCode = '';

  for (let i = 0; i < codeLength; i++) {
    errorCode += characters.charAt(Math.floor(Math.random() * characters.length));
  }

  // Typing effect
  let index = 0;
  const typingInterval = setInterval(() => {
    errorCodeElement.textContent += errorCode[index];
    index++;

    if (index >= errorCode.length) {
      clearInterval(typingInterval);
    }
  }, 50);
};

// Technical details
const populateTechnicalDetails = () => {
  document.getElementById('currentUrl').textContent = window.location.href;
  document.getElementById('browserInfo').textContent = navigator.userAgent.split(' ').slice(-2).join(' ');

  const now = new Date();
  document.getElementById('timestamp').textContent = now.toLocaleString();
};

// Event listeners
const setupEventListeners = () => {
  const homeButton = document.getElementById('homeButton');
  const detailsButton = document.getElementById('detailsButton');
  const errorDetails = document.getElementById('errorDetails');

  homeButton.addEventListener('click', () => {
    window.location.href = '/';
  });

  detailsButton.addEventListener('click', () => {
    errorDetails.classList.toggle('expanded');
    detailsButton.textContent = errorDetails.classList.contains('expanded') ? 'Hide Details' : 'View Details';
  });
};

// Initialize everything
document.addEventListener('DOMContentLoaded', () => {
  initParticles();
  generateErrorCode();
  populateTechnicalDetails();
  setupEventListeners();
});