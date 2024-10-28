const html = document.getElementsByTagName("html")[0];
const baseUrl =
  window.location.hostname === "localhost"
    ? "http://localhost:8080/centroestetica"
    : "https://centroestetica.com";

const selectedAttention = { appointmentId: null, serviceId: null };

const attentionsTable = document.getElementById("attentionsTable");
const attentionsActions = document.getElementById("attentionsActions");
const attentionsMessage = document.getElementById("attentionsMessage");

const createAttModal = document.getElementById("createAttentionModal");
const deleteModal = document.getElementById("deleteModal");

const createAttBtn = document.getElementById("createAttention");
const deleteBtn = document.getElementById("deleteAttention");

function changeSelAppointment(appointmentId) {
  if (selectedAttention.appointmentId)
    document
      .getElementById("appointmentId:" + selectedAttention.appointmentId)
      .classList.remove("selected-row");

  document
    .getElementById("appointmentId:" + appointmentId)
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

        row.id = `serviceId:${attention.service.id}`;
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
    console.error("Error al cargar atenciones: ", error); //poner sweetalert
  }
}

function changeSelAttention(serviceId) {
  if (selectedAttention.serviceId)
    document
      .getElementById("serviceId:" + selectedAttention.serviceId)
      .classList.remove("selected-row");

  document
    .getElementById("serviceId:" + serviceId)
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
    .getElementById("appointmentId:" + selectedAttention.appointmentId)
    .querySelectorAll("td");

  document.getElementById(
    "appointmentLabel"
  ).innerHTML = `Fecha y hora turno: <b>${row[0].textContent}</b>`;

  document.getElementById(
    "clientLabel"
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
    document.getElementById("deleteModalAppointmentId").value =
      selectedAttention.appointmentId;
    document.getElementById("deleteModalServiceId").value =
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
