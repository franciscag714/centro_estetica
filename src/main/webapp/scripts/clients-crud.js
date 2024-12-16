const html = document.getElementsByTagName("html")[0];

const clientModal = document.getElementById("client-modal");
const deleteModal = document.getElementById("delete-modal");

const newClientBtn = document.getElementById("new-client");
const updateBtn = document.getElementById("update-client");
const deleteBtn = document.getElementById("delete-client");

newClientBtn.addEventListener("click", () => {
  document.getElementById("action-modal").value = "create";
  document.getElementById("client-modal-id").value = "";
  document.getElementById("client-modal-title").textContent = "Nuevo Cliente";

  clientModal.querySelector("[name='lastname']").value = "";
  clientModal.querySelector("[name='firstname']").value = "";
  clientModal.querySelector("[name='email']").value = "";
  clientModal.querySelector("[name='user']").value = "";

  document.getElementById("password").required = true;
  document.getElementById("password").value = "";
  document.getElementById("password").setCustomValidity("");
  document.getElementById("password").minlength = "4";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  clientModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

updateBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("action-modal").value = "update";
    document.getElementById("client-modal-id").value = selectedId;
    document.getElementById("client-modal-title").textContent =
      "Modificar Cliente";

    const cells = document
      .getElementById("client-id:" + selectedId)
      .getElementsByTagName("td");
    clientModal.querySelector("[name='lastname']").value = cells[0].textContent;
    clientModal.querySelector("[name='firstname']").value =
      cells[1].textContent;
    clientModal.querySelector("[name='email']").value = cells[2].textContent;
    clientModal.querySelector("[name='user']").value = cells[3].textContent;

    document.getElementById("password").required = false;
    document.getElementById("password").placeholder = "Sin modificación";
    document.getElementById("password").setCustomValidity("");
    document.getElementById("password").value = "";

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    clientModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Primero seleccione un cliente.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});

deleteBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("delete-modal-id").value = selectedId;
    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    deleteModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Primero seleccione un cliente.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
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
