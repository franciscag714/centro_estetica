let selectedId;

const html = document.getElementsByTagName("html")[0];
const serviceModal = document.getElementById("serviceModal");
const deleteModal = document.getElementById("deleteModal");

const newBtn = document.getElementById("newService");
const updateBtn = document.getElementById("updateService");
const deleteBtn = document.getElementById("deleteService");

const closeServiceModalBtn = document.getElementById("closeServiceModal");
const closeDeleteBtn = document.getElementById("closeDeleteModal");

function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("serviceId:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");
  selectedId = id.replace("serviceId:", "");
}

function removeClass(className) {
  html.classList.remove(className);
}

function closeModal(modal) {
  modal.close();
  removeClass("modal-is-closing");
  removeClass("modal-is-open");
}

newBtn.addEventListener("click", () => {
  document.getElementById("actionModal").value = "create";
  document.getElementById("serviceIdModal").value = "";
  document.getElementById("serviceModalTitle").textContent = "Nuevo Servicio";

  serviceModal.querySelector("[name='description']").value = "";
  serviceModal.querySelector("[name='price']").value = "";
  serviceModal.querySelector("[name='type']").value = 0;

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  serviceModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

updateBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("actionModal").value = "update";
    document.getElementById("serviceIdModal").value = selectedId;
    document.getElementById("serviceModalTitle").textContent =
      "Modificar Servicio";

    const celdas = document
      .getElementById("serviceId:" + selectedId)
      .getElementsByTagName("td");

    serviceModal.querySelector("[name='description']").value =
      celdas[0].textContent;
    serviceModal.querySelector("[name='type']").value =
      celdas[1].dataset.typeid;
    serviceModal.querySelector("[name='price']").value = celdas[2].textContent;

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    serviceModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    alert("Primero seleccione un servicio");
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
    alert("Primero seleccione un servicio");
  }
});

closeServiceModalBtn.addEventListener("click", () => {
  html.classList.add("modal-is-closing");
  setTimeout(closeModal.bind(null, serviceModal), 400);
});

closeDeleteBtn.addEventListener("click", () => {
  html.classList.add("modal-is-closing");
  setTimeout(closeModal.bind(null, deleteModal), 400);
});
