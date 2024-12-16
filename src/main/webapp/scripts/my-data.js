const updateBtn = document.getElementById("update-data-btn");

function enableUpdateButton() {
  updateBtn.disabled = false;
}

document.addEventListener("DOMContentLoaded", function () {
  const togglePassword = document.getElementById("eye-icon");
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
  });
});

document
  .getElementById("firstname")
  .addEventListener("change", () => trimInputValue("firstname"));
document
  .getElementById("lastname")
  .addEventListener("change", () => trimInputValue("lastname"));
document
  .getElementById("email")
  .addEventListener("input", () => trimInputValue("email"));
document
  .getElementById("user")
  .addEventListener("change", () => trimInputValue("user"));
document
  .getElementById("password")
  .addEventListener("change", () => trimInputValue("password"));

document.getElementById("password").addEventListener("change", () => {
  const password = document.getElementById("password");
  if (password.value !== "" && password.value.length < 4) {
    password.setCustomValidity(
      "La contraseña debe tener al menos 4 caracteres."
    );
  } else password.setCustomValidity("");
});
