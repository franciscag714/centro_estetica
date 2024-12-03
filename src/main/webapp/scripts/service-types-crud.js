const html = document.getElementsByTagName("html")[0];
let selectedId;

const typeModal = document.getElementById("typeModal");
const deleteModal = document.getElementById("deleteModal");

const newTypeBtn = document.getElementById("newType");
const updateBtn = document.getElementById("updateType");
const deleteBtn = document.getElementById("deleteType");

const closeTypeModalBtn = document.getElementById("closeTypeModal");
const closeDeleteBtn = document.getElementById("closeDeleteModal");

function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("typeId:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");
  selectedId = id.replace("typeId:", "");
}

function removeClass(className) {
  html.classList.remove(className);
}

function closeModal(modal) {
  modal.close();
  removeClass("modal-is-closing");
  removeClass("modal-is-open");
}

newTypeBtn.addEventListener("click", () => {
  document.getElementById("actionModal").value = "create";
  document.getElementById("typeModalId").value = "";
  document.getElementById("typeModalTitle").textContent =
    "Nuevo Tipo de Servicio";
  typeModal.querySelector("[name='description']").value = "";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  typeModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

updateBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("actionModal").value = "update";
    document.getElementById("typeModalId").value = selectedId;
    document.getElementById("typeModalTitle").textContent =
      "Modificar Tipo de Servicio";

    const description = document
      .getElementById("typeId:" + selectedId)
      .getElementsByTagName("td")[0].textContent;
    typeModal.querySelector("[name='description']").value = description;

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    typeModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    alert("Primero seleccione un tipo de servicio");
  }
});

deleteBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("deleteModalId").value = selectedId;
    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    deleteModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    alert("Primero seleccione un tipo de servicio");
  }
});

closeTypeModalBtn.addEventListener("click", () => {
  html.classList.add("modal-is-closing");
  setTimeout(closeModal.bind(null, typeModal), 400);
});

closeDeleteBtn.addEventListener("click", () => {
  html.classList.add("modal-is-closing");
  setTimeout(closeModal.bind(null, deleteModal), 400);
});
