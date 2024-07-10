const newTypeButton = document.getElementById("newType");
const closeButton = document.getElementById("closeTypeModal");
const dialog = document.getElementById("typeModal");
const html = document.getElementsByTagName("html")[0];

newTypeButton.addEventListener("click", () => {
	document.getElementById('actionModal').value = 'insert';
	document.getElementById('typeModalId').value = '';
	document.getElementById('typeModalTitle').textContent = "Nuevo Tipo de Servicio"
	
    html.classList.add('modal-is-open');
	html.classList.add('modal-is-opening');
	dialog.showModal();
  	html.classList.remove('modal-is-opening');
});

closeButton.addEventListener("click", () => {
	html.classList.add('modal-is-closing');
  	dialog.close();
  	html.classList.remove('modal-is-closing');
  	html.classList.remove('modal-is-open');
});
