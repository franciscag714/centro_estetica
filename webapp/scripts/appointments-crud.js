const html = document.getElementsByTagName('html')[0];
let selectedId;

const appointmentModal = document.getElementById('appointmentModal');
const deleteModal = document.getElementById('deleteModal');

const newAppointmentBtn = document.getElementById('newAppointment');
const updateBtn = document.getElementById('updateAppointment');
const deleteBtn = document.getElementById('deleteAppointment');

const closeAppointmentModalBtn = document.getElementById('closeAppointmentModal');
const closeDeleteBtn = document.getElementById('closeDeleteModal');

function changeSelectedRow(id){
	if (selectedId)
		document.getElementById('appointmentId:' + selectedId).classList.remove('selected-row');
	
	document.getElementById(id).classList.add('selected-row');
	selectedId = id.replace('appointmentId:', '');
}

function removeClass(className){
	html.classList.remove(className);
}

function closeModal(modal){
	modal.close();
	removeClass('modal-is-closing');
	removeClass('modal-is-open');
}

newAppointmentBtn.addEventListener('click', () => {
	document.getElementById('actionModal').value = 'create';
	document.getElementById('appointmentModalId').value = '';
	document.getElementById('appointmentModalTitle').textContent = 'Nuevo Turno';
	
	appointmentModal.querySelector("[name='date_time']").value = '';
	appointmentModal.querySelector("[name='client']").value = '';
	appointmentModal.querySelector("[name='employee']").value = '';
	
	html.classList.add('modal-is-open');
	html.classList.add('modal-is-opening');
	appointmentModal.showModal();
	setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
});

updateBtn.addEventListener('click', () => {
	if(selectedId){
		document.getElementById('actionModal').value = 'update';
		document.getElementById('appointmentModalId').value = selectedId;
		document.getElementById('appointmentModalTitle').textContent = 'Modificar Turno';
		
		const cells = document.getElementById('appointmentId:' + selectedId).getElementsByTagName('td');
		appointmentModal.querySelector("[name='date_time']").value = cells[0].textContent;
		appointmentModal.querySelector("[name='client']").value = cells[1].textContent;
		appointmentModal.querySelector("[name='employee']").value = cells[2].textContent;
		
	    html.classList.add('modal-is-open');
		html.classList.add('modal-is-opening');
		appointmentModal.showModal();
		setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
  	}
  	else{
		alert('Primero seleccione un turno');
	}
});

deleteBtn.addEventListener('click', () => {
	if (selectedId){
		document.getElementById('deleteModalId').value = selectedId;
		html.classList.add('modal-is-open');
		html.classList.add('modal-is-opening');
		deleteModal.showModal();
		setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
	}
	else{
		alert('Primero seleccione un turno');
	}
});

closeAppointmentModalBtn.addEventListener('click', () => {
	html.classList.add('modal-is-closing');
	setTimeout(closeModal.bind(null, appointmentModal), 400);
});

closeDeleteBtn.addEventListener('click', () => {
	html.classList.add('modal-is-closing');
	setTimeout(closeModal.bind(null, deleteModal), 400);
});