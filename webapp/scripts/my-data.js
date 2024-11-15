const updateBtn = document.getElementById("updateClientData");

function enableUpdateButton() {
  updateBtn.disabled = false;
}

document.addEventListener("DOMContentLoaded", function () {
    const togglePassword = document.getElementById("eyeIcon");
    const passwordField = document.getElementById("password");
    
    togglePassword.addEventListener("click", () => {
        if (passwordField.type === "password") {
            passwordField.type = "text";
            togglePassword.classList.remove("bx-show");
            togglePassword.classList.add("bx-hide"); 
        } else {
            passwordField.type = "password";
            togglePassword.classList.remove("bx-hide");
            togglePassword.classList.add("bx-show");
        }
    });
	
	passwordField.addEventListener("input", () => {
		enableUpdateButton();
	})
});



