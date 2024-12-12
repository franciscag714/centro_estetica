const html = document.getElementsByTagName("html")[0];
let selectedId;

const clientModal = document.getElementById("client-modal");
const deleteModal = document.getElementById("delete-modal");

const newClientBtn = document.getElementById("new-client");
const updateBtn = document.getElementById("update-client");
const deleteBtn = document.getElementById("delete-client");

function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("client-id:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");
  selectedId = id.replace("client-id:", "");
}

function removeClass(className) {
  html.classList.remove(className);
}

function closeModal(modalId) {
  const modal = document.getElementById(modalId);
  html.classList.add("modal-is-closing");

  setTimeout(() => {
    modal.close();
    html.classList.remove("modal-is-closing");
    html.classList.remove("modal-is-open");
  }, 400);
}

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
