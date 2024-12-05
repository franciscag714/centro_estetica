const html = document.getElementsByTagName("html")[0];
const baseUrl =
  window.location.hostname === "localhost"
    ? "http://localhost:8080/centro_estetica"
    : "https://centroestetica.com";

const selectedAttention = { appointmentId: null, serviceId: null };

const attentionsTable = document.getElementById("attentions-table");
const attentionsActions = document.getElementById("attentions-actions");
const attentionsMessage = document.getElementById("attentions-message");

const createAttModal = document.getElementById("create-attention-modal");
const deleteModal = document.getElementById("delete-modal");

const createAttBtn = document.getElementById("create-attention");
const deleteBtn = document.getElementById("delete-attention");

function changeSelAppointment(appointmentId) {
  if (selectedAttention.appointmentId)
    document
      .getElementById("appointment-id:" + selectedAttention.appointmentId)
      .classList.remove("selected-row");

  document
    .getElementById("appointment-id:" + appointmentId)
    .classList.add("selected-row");

  selectedAttention.appointmentId = appointmentId;
  selectedAttention.serviceId = null;
  loadAttentionsTable();
}

async function loadAttentionsTable() {
  try {
    const response = await fetch(
      `${baseUrl}/lista-atenciones/${selectedAttention.appointmentId}`
    );
    if (!response.ok) throw new Error("Network response was not OK");

    const attentions = await response.json();
    tableBody = attentionsTable.querySelector("tbody");
    tableBody.innerHTML = "";

    if (attentions.length != 0) {
      attentions.forEach((attention) => {
        const row = document.createElement("tr");
        row.addEventListener("click", () =>
          changeSelAttention(attention.service.id)
        );

        row.id = `service-id:${attention.service.id}`;
        row.innerHTML = `
        <td>${attention.service.description}</td>
        <td>${attention.formatedPrice}</td>`;

        tableBody.appendChild(row);

        deleteBtn.style.display = "";
        attentionsMessage.style.display = "none";
        attentionsTable.style.display = "";
      });
    } else {
      deleteBtn.style.display = "none";
      attentionsMessage.textContent = "No se brindó ninguna atención.";
      attentionsMessage.style.display = "";
      attentionsTable.style.display = "none";
    }
    attentionsActions.style.display = "";
  } catch (error) {
    Swal.fire({
      title: "Error al cargar las atenciones.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
}

function changeSelAttention(serviceId) {
  if (selectedAttention.serviceId)
    document
      .getElementById("service-id:" + selectedAttention.serviceId)
      .classList.remove("selected-row");

  document
    .getElementById("service-id:" + serviceId)
    .classList.add("selected-row");

  selectedAttention.serviceId = serviceId;
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

createAttBtn.addEventListener("click", () => {
  document.getElementById("appointment").value =
    selectedAttention.appointmentId;
  const row = document
    .getElementById("appointment-id:" + selectedAttention.appointmentId)
    .querySelectorAll("td");

  document.getElementById(
    "appointment-label"
  ).innerHTML = `Fecha y hora turno: <b>${row[0].textContent}</b>`;

  document.getElementById(
    "client-label"
  ).innerHTML = `Cliente: <b>${row[2].textContent}</b>`;

  document.getElementById("service").value = "";
  document.getElementById("price").textContent = "Precio: ---";

  html.classList.add("modal-is-open");
  html.classList.add("modal-is-opening");
  createAttModal.showModal();
  setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
});

deleteBtn.addEventListener("click", () => {
  if (selectedAttention.serviceId) {
    document.getElementById("delete-modal-appointment-id").value =
      selectedAttention.appointmentId;
    document.getElementById("delete-modal-service-id").value =
      selectedAttention.serviceId;

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    deleteModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Primero seleccione una atención.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});

document.getElementById("service").addEventListener("change", (event) => {
  const selectedService = event.target.options[event.target.selectedIndex];
  document.getElementById(
    "price"
  ).innerHTML = `Precio: <b>${selectedService.getAttribute("data-price")}</b>`;
});
