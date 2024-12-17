// Add event listeners for form validation
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("form[action='LoginServlet']");
    const registerForm = document.querySelector("form[action='RegisterServlet']");

    if (loginForm) {
        loginForm.addEventListener("submit", validateLoginForm);
    }

    if (registerForm) {
        registerForm.addEventListener("submit", validateRegisterForm);
    }
});

// Helper function to show an error message
function showError(input, message) {
    const errorElement = input.nextElementSibling;
    input.classList.add("error");
    errorElement.textContent = message;
    errorElement.style.display = "block";
}

// Helper function to clear an error message
function clearError(input) {
    const errorElement = input.nextElementSibling;
    input.classList.remove("error");
    errorElement.textContent = "";
    errorElement.style.display = "none";
}

// Validate Login Form
function validateLoginForm(event) {
    const username = document.getElementById("username");
    const password = document.getElementById("password");

    let isValid = true;

    if (username.value.trim() === "") {
        showError(username, "Username is required.");
        isValid = false;
    } else {
        clearError(username);
    }

    if (password.value.trim() === "") {
        showError(password, "Password is required.");
        isValid = false;
    } else {
        clearError(password);
    }

    if (!isValid) {
        event.preventDefault(); // Prevent form submission
    }
}

// Validate Register Form
function validateRegisterForm(event) {
    const username = document.getElementById("username");
    const password = document.getElementById("password");

    let isValid = true;

    if (username.value.trim() === "") {
        showError(username, "Username is required.");
        isValid = false;
    } else {
        clearError(username);
    }

    if (password.value.trim() === "") {
        showError(password, "Password is required.");
        isValid = false;
    } else if (password.value.trim().length < 6) {
        showError(password, "Password must be at least 6 characters long.");
        isValid = false;
    } else {
        clearError(password);
    }

    if (!isValid) {
        event.preventDefault(); // Prevent form submission
    }
}
