const html = document.getElementsByTagName("html")[0];

const serviceModal = document.getElementById("service-modal");
const deleteModal = document.getElementById("delete-modal");

const newBtn = document.getElementById("new-service");
const updateBtn = document.getElementById("update-service");
const deleteBtn = document.getElementById("delete-service");

newBtn.addEventListener("click", () => {
  document.getElementById("action-modal").value = "create";
  document.getElementById("service-id-modal").value = "";
  document.getElementById("service-modal-title").textContent = "Nuevo Servicio";

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
    document.getElementById("action-modal").value = "update";
    document.getElementById("service-id-modal").value = selectedId;
    document.getElementById("service-modal-title").textContent =
      "Modificar Servicio";

    const celdas = document
      .getElementById("service-id:" + selectedId)
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
    Swal.fire({
      title: "Primero seleccione un servicio.",
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
      title: "Primero seleccione un servicio.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});

document
  .getElementById("description")
  .addEventListener("change", () => trimInputValue("description"));
