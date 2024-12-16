let selectedId;

function removeClass(className) {
  html.classList.remove(className);
}

function trimInputValue(id) {
  const input = document.getElementById(id);
  input.value = input.value.trim();
}

function changeSelectedRow(elementId) {
  const id = elementId.split(":");

  if (selectedId)
    document
      .getElementById(`${id[0]}:${selectedId}`)
      .classList.remove("selected-row");

  document.getElementById(elementId).classList.add("selected-row");
  selectedId = id[1];
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
