const html = document.getElementsByTagName("html")[0];
const baseUrl =
  window.location.hostname === "localhost"
    ? "http://localhost:8080/centroestetica"
    : "https://centroestetica.com";

let selectedId;
const attentionsDiv = document.getElementById("attentionsDiv");
const attentionsList = document.getElementById("attentionsList");

function changeSelectedRow(id) {
  if (selectedId)
    document
      .getElementById("appointmentId:" + selectedId)
      .classList.remove("selected-row");

  document.getElementById(id).classList.add("selected-row");

  selectedId = id.replace("appointmentId:", "");
  loadAttentionsTable();
}

async function loadAttentionsTable() {
  try {
    attentionsDiv.style.display = "block";
    const response = await fetch(`${baseUrl}/lista-atenciones/${selectedId}`);
    if (!response.ok) throw new Error("Network response was not OK");

    const attentions = await response.json();
    attentionsList.innerHTML = "";

    attentions.forEach((attention) => {
      const row = document.createElement("tr");
      row.id = `attentionId:${attention.appointment.id}-${attention.service.id}`;
      row.innerHTML = `
        <td>${attention.service.description}</td>
        <td>${attention.price}</td>`;

      attentionsList.appendChild(row);
    });
  } catch (error) {
    console.error("Error al cargar atenciones: ", error); //poner sweetalert
  }
}
