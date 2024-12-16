const html = document.getElementsByTagName("html")[0];
const filtersModal = document.getElementById("filters-modal");
const dateInput = document.getElementById("date");
const unbookModal = document.getElementById("unbook-modal");

const filters = {
  date: "",
  startTime: "",
  endTime: "",
};

document.getElementById("open-filters-btn").addEventListener("click", () => {
  filters.date = dateInput.value;
  filters.startTime = document.getElementById("start-time").value;
  filters.endTime = document.getElementById("end-time").value;

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  filtersModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

dateInput.addEventListener("change", function () {
  const selectedDateTime = new Date(dateInput.value);
  const currentDateTime = new Date();

  currentDateTime.setHours(0, 0, 0, 0);
  selectedDateTime.setHours(selectedDateTime.getHours() + 3);

  if (selectedDateTime < currentDateTime)
    dateInput.setCustomValidity("Ingrese la fecha de hoy o una posterior.");
  else dateInput.setCustomValidity("");
});

document.getElementById("clear-filters-btn").addEventListener("click", () => {
  document.getElementById("date").value = "";
  document.getElementById("start-time").value = "";
  document.getElementById("end-time").value = "";

  if (filters.date === "" && filters.startTime === "" && filters.endTime === "")
    return closeModal("filters-modal");

  document.getElementById("filter-form").submit();
});

function openUnbookModal(button) {
  document.getElementById("unbook-modal-id").value =
    button.dataset.appointmentId;
  document.getElementById("unbook-modal-message").textContent =
    "¿Está seguro que desea cancelar su turno para el " +
    button.dataset.datetime +
    "?";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  unbookModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
}
