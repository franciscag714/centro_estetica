const html = document.getElementsByTagName("html")[0];
let selectedId;

const employeeModal = document.getElementById("employee-modal");
const deleteModal = document.getElementById("delete-modal");

const newEmployeeBtn = document.getElementById("new-employee");
const updateBtn = document.getElementById("update-employee");
const deleteBtn = document.getElementById("delete-employee");

function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("employee-id:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");
  selectedId = id.replace("employee-id:", "");
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

newEmployeeBtn.addEventListener("click", () => {
  document.getElementById("action-modal").value = "create";
  document.getElementById("employee-modal-id").value = "";
  document.getElementById("employee-modal-title").textContent =
    "Nuevo Empleado";

  employeeModal.querySelector("[name='firstname']").value = "";
  employeeModal.querySelector("[name='lastname']").value = "";
  employeeModal.querySelector("[name='email']").value = "";
  employeeModal.querySelector("[name='user']").value = "";
  employeeModal.querySelector("[name='is-admin']").value = "";

  document.getElementById("password").required = true;
  document.getElementById("password").value = "";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  employeeModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

updateBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("action-modal").value = "update";
    document.getElementById("employee-modal-id").value = selectedId;
    document.getElementById("employee-modal-title").textContent =
      "Modificar Empleado";

    const cells = document
      .getElementById("employee-id:" + selectedId)
      .getElementsByTagName("td");
    employeeModal.querySelector("[name='lastname']").value =
      cells[0].textContent;
    employeeModal.querySelector("[name='firstname']").value =
      cells[1].textContent;
    employeeModal.querySelector("[name='email']").value = cells[2].textContent;
    employeeModal.querySelector("[name='user']").value = cells[3].textContent;
    employeeModal.querySelector("[name='is-admin']").value =
      cells[4].textContent;

    document.getElementById("password").required = false;
    document.getElementById("password").placeholder = "Sin modificación";
    document.getElementById("password").value = "";

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    employeeModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Primero seleccione un empleado.",
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
      title: "Primero seleccione un empleado.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});
