let searchbtn = document.querySelector('#search-btn');
let searchbar = document.querySelector('.search-bar-container');
let formbtn = document.querySelector('#login-btn');
let loginForm = document.querySelector('.log');
let formClose = document.querySelector('#fclose');

window.onscroll = () => {
    searchbtn.classList.remove('fa-times');
    searchbar.classList.remove('active')
}

searchbtn.addEventListener('click', () => {
    searchbtn.classList.toggle('fa-times');
    searchbar.classList.toggle('active')
});
formbtn.addEventListener('click', () => {
    loginForm.classList.add('active');
    
});
formClose.addEventListener('click', () => {
    loginForm.classList.remove('active');
    
});

document.getElementById('accommodationForm').addEventListener('submit', function (e) {
    e.preventDefault(); 
    
    const errorMessages = [];
    const name = document.getElementById('name').value.trim();
    const age = document.getElementById('age').value;
    const email = document.getElementById('email').value.trim();
    const type = document.getElementById('type').value;
    const budget = document.getElementById('budget').value;
    const gender = document.getElementById('gender').value;

    
    if (name === '') {
        errorMessages.push('Name is required.');
    }

    
    if (age === '' || isNaN(age) || age < 18 || age > 100) {
        errorMessages.push('Age must be between 18 and 100.');
    }

    
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        errorMessages.push('Please enter a valid email address.');
    }

    
    if (type === '') {
        errorMessages.push('Please select an accommodation type.');
    }

    
    if (budget === '' || isNaN(budget) || budget <= 0) {
        errorMessages.push('Budget must be a positive number.');
    }

    
    if (gender === '') {
        errorMessages.push('Please select a preferred gender.');
    }

    const errorDiv = document.getElementById('errorMessages');
    errorDiv.innerHTML = ''; 

    if (errorMessages.length > 0) {
        errorMessages.forEach(message => {
            const errorPara = document.createElement('p');
            errorPara.textContent = message;
            errorDiv.appendChild(errorPara);
        });
    } else {
        alert('Form submitted successfully!');
        
    }
});
var typed = new Typed(".tnt",{
    strings: ["Whether you're looking to share a space or searching for someone to fill an empty room, we've got you covered."],
    typeSpeed: 25,
    backSpeed: 5,
    loop: true
});

