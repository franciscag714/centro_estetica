const html = document.getElementsByTagName("html")[0];
const baseUrl =
  window.location.hostname === "localhost"
    ? "http://localhost:8080/centroestetica"
    : "https://centroestetica.com";

let selectedAttention = { appointmentId: null, serviceId: null };

const attentionsDiv = document.getElementById("attentionsDiv");
const attentionsList = document.getElementById("attentionsList");

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

  selectedAttention = { appointmentId: appointmentId, serviceId: null };
  loadAttentionsTable();
}

async function loadAttentionsTable() {
  try {
    attentionsDiv.style.display = "block";
    const response = await fetch(
      `${baseUrl}/lista-atenciones/${selectedAttention.appointmentId}`
    );
    if (!response.ok) throw new Error("Network response was not OK");

    const attentions = await response.json();
    attentionsList.innerHTML = "";

    attentions.forEach((attention) => {
      const row = document.createElement("tr");
      row.addEventListener("click", () =>
        changeSelAttention(attention.service.id)
      );

      row.id = `serviceId:${attention.service.id}`;
      row.innerHTML = `
        <td>${attention.service.description}</td>
        <td>${attention.price}</td>`;

      attentionsList.appendChild(row);
    });
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
  document.getElementById("appointmentLabel").textContent = "Cliente: ";
  document.getElementById("clientLabel").textContent = "Cliente: ";
  document.getElementById("service").value = "";
  document.getElementById("price").textContent = "";

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
    alert("Primero seleccione una atención."); //utilizar SweetAlert
  }
});
