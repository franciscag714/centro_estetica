const html = document.getElementsByTagName("html")[0];
let selectedId;

const typeModal = document.getElementById("type-modal");
const deleteModal = document.getElementById("delete-modal");

const newTypeBtn = document.getElementById("new-type");
const updateBtn = document.getElementById("update-type");
const deleteBtn = document.getElementById("delete-type");

function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("type-id:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");
  selectedId = id.replace("type-id:", "");
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

newTypeBtn.addEventListener("click", () => {
  document.getElementById("action-modal").value = "create";
  document.getElementById("type-modal-id").value = "";
  document.getElementById("type-modal-title").textContent =
    "Nuevo Tipo de Servicio";
  typeModal.querySelector("[name='description']").value = "";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  typeModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

updateBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("action-modal").value = "update";
    document.getElementById("type-modal-id").value = selectedId;
    document.getElementById("type-modal-title").textContent =
      "Modificar Tipo de Servicio";

    const description = document
      .getElementById("type-id:" + selectedId)
      .getElementsByTagName("td")[0].textContent;
    typeModal.querySelector("[name='description']").value = description;

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    typeModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Primero seleccione un tipo de servicio.",
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
      title: "Primero seleccione un tipo de servicio.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});
