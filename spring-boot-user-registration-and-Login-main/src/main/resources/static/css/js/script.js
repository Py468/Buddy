document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('roommateForm');
    const loader = document.getElementById('loader');
    const successMessage = document.getElementById('successMessage');
    const cleanlinessInput = document.getElementById('cleanliness');
    const cleanlinessValue = document.getElementById('cleanlinessValue');
    const ratingInput = document.getElementById('ratingPastRoommate');
    const ratingValue = document.getElementById('ratingValue');
    const photoInput = document.getElementById('photoRoommate');
    const fileName = document.getElementById('fileName');
    const formGroups = document.querySelectorAll('.form-group');

    // Animate form groups on scroll
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, { threshold: 0.1 });

    formGroups.forEach(group => {
        observer.observe(group);
    });

    // Update range input values with smooth animation
    function updateRangeValue(input, display) {
        display.textContent = input.value;
        display.style.transform = 'scale(1.2)';
        setTimeout(() => {
            display.style.transform = 'scale(1)';
        }, 200);
    }

    cleanlinessInput.addEventListener('input', function() {
        updateRangeValue(this, cleanlinessValue);
    });

    ratingInput.addEventListener('input', function() {
        updateRangeValue(this, ratingValue);
    });

    // Enhanced file input handling
    photoInput.addEventListener('change', function() {
        const file = this.files[0];
        if (file) {
            fileName.textContent = file.name;
            fileName.style.color = '#4f46e5';
        } else {
            fileName.textContent = 'No file chosen';
            fileName.style.color = '#6b7280';
        }
    });

    // Smooth scroll to form sections
    document.querySelectorAll('label').forEach(label => {
        label.addEventListener('click', function(e) {
            const input = document.getElementById(this.getAttribute('for'));
            if (input) {
                input.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        });
    });

    // Interactive form validation
    const inputs = form.querySelectorAll('input[required], select[required]');
    inputs.forEach(input => {
        input.addEventListener('invalid', function(e) {
            e.preventDefault();
            this.classList.add('invalid');
        });

        input.addEventListener('input', function() {
            if (this.validity.valid) {
                this.classList.remove('invalid');
            }
        });
    });

    // Form submission with enhanced animation
   form.addEventListener('submit', function(e) {
       e.preventDefault();

       const isValid = form.checkValidity();
       if (!isValid) {
           form.reportValidity();
           return;
       }

       // Show loader
       loader.style.display = 'flex';
       loader.style.opacity = '0';
       requestAnimationFrame(() => {
           loader.style.opacity = '1';
       });

       // Show success message with animation
       successMessage.style.display = 'flex';
       successMessage.style.opacity = '0';
       successMessage.style.transform = 'translate(-50%, -50%) scale(0.9)';
       requestAnimationFrame(() => {
           successMessage.style.opacity = '1';
           successMessage.style.transform = 'translate(-50%, -50%) scale(1)';
       });

       // Submit to backend after short delay
       setTimeout(() => {
           form.submit(); // ✅ Actual Spring Boot submission
       }, 1500);
   });


// Enhanced reset form functionality
function resetForm() {
    const form = document.getElementById('roommateForm');
    const successMessage = document.getElementById('successMessage');
    const fileName = document.getElementById('fileName');
    const cleanlinessValue = document.getElementById('cleanlinessValue');
    const ratingValue = document.getElementById('ratingValue');

    // Animate form reset
    successMessage.style.transform = 'translate(-50%, -50%) scale(0.9)';
    successMessage.style.opacity = '0';

    setTimeout(() => {
        form.reset();
        successMessage.style.display = 'none';
        fileName.textContent = 'No file chosen';
        fileName.style.color = '#6b7280';
        cleanlinessValue.textContent = '5';
        ratingValue.textContent = '5';

        // Animate form groups back in
        document.querySelectorAll('.form-group').forEach((group, index) => {
            group.style.opacity = '0';
            setTimeout(() => {
                group.style.opacity = '1';
            }, index * 100);
        });
    }, 300);
}

// Add smooth scroll behavior
document.addEventListener('scroll', function() {
    const header = document.querySelector('header');
    if (window.scrollY > 50) {
        header.style.transform = 'translateY(-100%)';
    } else {
        header.style.transform = 'translateY(90px)';
    }
});
});