const html = document.getElementsByTagName("html")[0];

const appointmentModal = document.getElementById("appointment-modal");
const deleteModal = document.getElementById("delete-modal");

const newAppointmentBtn = document.getElementById("new-appointment");
const updateBtn = document.getElementById("update-appointment");
const deleteBtn = document.getElementById("delete-appointment");
const dateTimeInput = document.getElementById("date-time");

// @Override
function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("appointment-id:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");

  if (document.getElementById(id).dataset.isModifiable === "true")
    showButtons();
  else hideButtons();

  selectedId = id.replace("appointment-id:", "");
}

newAppointmentBtn.addEventListener("click", () => {
  document.getElementById("action-modal").value = "create";
  document.getElementById("appointment-modal-id").value = "";
  document.getElementById("appointment-modal-title").textContent =
    "Nuevo Turno";

  appointmentModal.querySelector("[name='date-time']").value = "";

  const employeeInput = appointmentModal.querySelector("[name='employee']");
  if (employeeInput) employeeInput.value = "";

  appointmentModal.querySelector("[name='client']").value = "0";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  appointmentModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

updateBtn.addEventListener("click", () => {
  if (selectedId) {
    document.getElementById("action-modal").value = "update";
    document.getElementById("appointment-modal-id").value = selectedId;
    document.getElementById("appointment-modal-title").textContent =
      "Modificar Turno";

    const cells = document
      .getElementById("appointment-id:" + selectedId)
      .getElementsByTagName("td");

    appointmentModal.querySelector("[name='date-time']").value =
      formatForDateTimeLocal(cells[0].textContent);

    const employeeInput = appointmentModal.querySelector("[name='employee']");
    if (employeeInput) employeeInput.value = cells[1].dataset.employeeId;

    appointmentModal.querySelector("[name='client']").value =
      cells[2].dataset.clientId;

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    appointmentModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Primero seleccione un turno.",
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
      title: "Primero seleccione un turno.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});

dateTimeInput.addEventListener("change", function () {
  const selectedDateTime = new Date(dateTimeInput.value);
  const currentDateTime = new Date();

  if (selectedDateTime.getTime() < currentDateTime.getTime())
    dateTimeInput.setCustomValidity(
      "Ingrese una fecha y una hora posteriores a ahora."
    );
  else dateTimeInput.setCustomValidity("");
});

function formatForDateTimeLocal(dateStr) {
  const [datePart, timePart] = dateStr.split(" ");
  let [day, month, year] = datePart.split("/");
  let [hours, minutes] = timePart.split(":");

  month = month.padStart(2, "0");
  day = day.padStart(2, "0");
  hours = hours.padStart(2, "0");
  minutes = minutes.padStart(2, "0");

  return `${year}-${month}-${day}T${hours}:${minutes}`;
}

function hideButtons() {
  updateBtn.style.display = "none";
  deleteBtn.style.display = "none";
}

function showButtons() {
  updateBtn.style.display = "";
  deleteBtn.style.display = "";
}
