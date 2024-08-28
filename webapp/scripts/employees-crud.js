const html = document.getElementsByTagName('html')[0];
let selectedId;

const employeeModal = document.getElementById('employeeModal');
const deleteModal = document.getElementById('deleteModal');

const newEmployeeBtn = document.getElementById('newEmployee');
const updateBtn = document.getElementById('updateEmployee');
const deleteBtn = document.getElementById('deleteEmployee');

const closeEmployeeModalBtn = document.getElementById('closeEmployeeModal');
const closeDeleteBtn = document.getElementById('closeDeleteModal');

function changeSelectedRow(id){
	if (selectedId)
		document.getElementById('employeeId:' + selectedId).classList.remove('selected-row');
	
	document.getElementById(id).classList.add('selected-row');
	selectedId = id.replace('employeeId:', '');
}

function removeClass(className){
	html.classList.remove(className);
}

function closeModal(modal){
	modal.close();
	removeClass('modal-is-closing');
	removeClass('modal-is-open');
}

newEmployeeBtn.addEventListener('click', () => {
	document.getElementById('actionModal').value = 'create';
	document.getElementById('employeeModalId').value = '';
	document.getElementById('employeeModalTitle').textContent = 'Nuevo Empleado';
	
	employeeModal.querySelector("[name='firstname']").value = '';
	employeeModal.querySelector("[name='lastname']").value = '';
	employeeModal.querySelector("[name='email']").value = '';
	employeeModal.querySelector("[name='user']").value = '';
	employeeModal.querySelector("[name='is_admin']").value = '';
	
	document.getElementById('password').required = true;
	document.getElementById('passwordDiv').style.display = 'block';
		
    html.classList.add('modal-is-open');
	html.classList.add('modal-is-opening');
	employeeModal.showModal();
	setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);	
});

updateBtn.addEventListener('click', () => {
	if (selectedId){
		document.getElementById('actionModal').value = 'update';
		document.getElementById('employeeModalId').value = selectedId;
		document.getElementById('employeeModalTitle').textContent = 'Modificar Empleado';
		
		const cells = document.getElementById('employeeId:' + selectedId).getElementsByTagName('td');
		employeeModal.querySelector("[name='lastname']").value = cells[0].textContent;
		employeeModal.querySelector("[name='firstname']").value = cells[1].textContent;
		employeeModal.querySelector("[name='email']").value = cells[2].textContent;
		employeeModal.querySelector("[name='user']").value = cells[3].textContent;
		employeeModal.querySelector("[name='is_admin']").value = cells[4].textContent;
		
		document.getElementById('password').required = false;
		document.getElementById('passwordDiv').style.display = 'none';
			
	    html.classList.add('modal-is-open');
		html.classList.add('modal-is-opening');
		employeeModal.showModal();
		setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);	
	}
	else {
		alert('Primero debe seleccionar un empleado');
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
		alert('Primero debe seleccionar un empleado');
	}
});

closeEmployeeModalBtn.addEventListener('click',  () => {
	html.classList.add('modal-is-closing');
  	setTimeout(closeModal.bind(null, employeeModal), 400);
});

closeDeleteBtn.addEventListener('click',  () => {
	html.classList.add('modal-is-closing');
  	setTimeout(closeModal.bind(null, deleteModal), 400);
});