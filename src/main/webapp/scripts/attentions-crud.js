const html = document.getElementsByTagName("html")[0];
const baseUrl =
  window.location.hostname === "localhost"
    ? "http://localhost:8080/centro_estetica"
    : "https://centroestetica.com";

const selectedAppointment = {
  appointmentId: null,
  selectedServiceId: null,
  servicesIds: [],
};

const attentionsTable = document.getElementById("attentions-table");
const attentionsActions = document.getElementById("attentions-actions");
const attentionsMessage = document.getElementById("attentions-message");

const createAttModal = document.getElementById("create-attention-modal");
const deleteModal = document.getElementById("delete-modal");

const createAttBtn = document.getElementById("create-attention");
const qrBtn = document.getElementById("qr-btn");
const deleteBtn = document.getElementById("delete-attention");

function changeSelAppointment(appointmentId) {
  if (selectedAppointment.appointmentId)
    document
      .getElementById("appointment-id:" + selectedAppointment.appointmentId)
      .classList.remove("selected-row");

  document
    .getElementById("appointment-id:" + appointmentId)
    .classList.add("selected-row");

  selectedAppointment.appointmentId = appointmentId;
  selectedAppointment.selectedServiceId = null;
  selectedAppointment.servicesIds = [];

  loadAttentionsTable();
}

document.addEventListener("DOMContentLoaded", function () {
  id = attentionsTable.getAttribute("data-selected-appointment");
  if (id !== "0") {
    selectedAppointment.appointmentId = parseInt(id);
    document
      .getElementById("appointment-id:" + id)
      .classList.add("selected-row");

    const tableBody = attentionsTable.querySelector("tbody");
    const rows = tableBody.querySelectorAll("tr");

    rows.forEach((row) => {
      const serviceId = row.id.substring(11);
      selectedAppointment.servicesIds.push(parseInt(serviceId));

      row.addEventListener("click", () => {
        changeSelAttention(serviceId);
      });
    });
  }
});

async function loadAttentionsTable() {
  try {
    const response = await fetch(
      `${baseUrl}/lista-atenciones/${selectedAppointment.appointmentId}`
    );
    if (!response.ok) throw new Error("Network response was not OK");

    const attentions = await response.json();
    const tableBody = attentionsTable.querySelector("tbody");
    tableBody.innerHTML = "";

    if (attentions.length !== 0) {
      attentions.forEach((attention) => {
        selectedAppointment.servicesIds.push(attention.service.id);
        const row = document.createElement("tr");

        row.addEventListener("click", () =>
          changeSelAttention(attention.service.id)
        );

        row.id = `service-id:${attention.service.id}`;
        row.innerHTML = `
        <td>${attention.service.description}</td>
        <td>${attention.formatedPrice}</td>`;

        tableBody.appendChild(row);

        qrBtn.style.display = "";
        deleteBtn.style.display = "";
        attentionsMessage.style.display = "none";
      });
    } else {
      qrBtn.style.display = "none";
      deleteBtn.style.display = "none";
      attentionsMessage.textContent = "No se brindó ninguna atención.";
      attentionsMessage.style.display = "";
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
  if (selectedAppointment.selectedServiceId)
    document
      .getElementById("service-id:" + selectedAppointment.selectedServiceId)
      .classList.remove("selected-row");

  document
    .getElementById("service-id:" + serviceId)
    .classList.add("selected-row");

  selectedAppointment.selectedServiceId = serviceId;
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
  let openModal = false;
  const serviceSelect = document.getElementById("service");
  const options = serviceSelect.options;

  Array.from(options).forEach((opt) => {
    if (selectedAppointment.servicesIds.includes(parseInt(opt.value)))
      opt.style.display = "none";
    else {
      opt.style.display = "";
      openModal = true;
    }
  });

  if (openModal) {
    document.getElementById("appointment").value =
      selectedAppointment.appointmentId;
    const row = document
      .getElementById("appointment-id:" + selectedAppointment.appointmentId)
      .querySelectorAll("td");

    document.getElementById(
      "appointment-label"
    ).innerHTML = `Fecha y hora turno: <b>${row[0].textContent}</b>`;

    document.getElementById(
      "client-label"
    ).innerHTML = `Cliente: <b>${row[2].textContent}</b>`;

    serviceSelect.value = "";
    document.getElementById("price").textContent = "Precio: ---";

    html.classList.add("modal-is-open");
    html.classList.add("modal-is-opening");
    createAttModal.showModal();
    setTimeout(removeClass.bind(null, "modal-is-opening"), 400);
  } else {
    Swal.fire({
      title: "Todos los servicios disponibles han sido brindados.",
      icon: "error",
      confirmButtonColor: "#f0ad4e",
    });
  }
});

deleteBtn.addEventListener("click", () => {
  if (selectedAppointment.selectedServiceId) {
    document.getElementById("delete-modal-appointment-id").value =
      selectedAppointment.appointmentId;
    document.getElementById("delete-modal-service-id").value =
      selectedAppointment.selectedServiceId;

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

function generateQr() {
  url = `${baseUrl}/atenciones`;
  fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ id: selectedAppointment.appointmentId }),
  }).then((response) => {
    if (response.ok) {
      Swal.fire({
        title: "QR listo!",
        icon: "success",
        confirmButtonColor: "#f0ad4e",
      });
    } else {
      Swal.fire({
        title: "No se pudo generar el QR.",
        icon: "error",
        confirmButtonColor: "#f0ad4e",
      });
    }
  });
}
