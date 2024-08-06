const html = document.getElementsByTagName('html')[0];
let selectedId;

const clientModal = document.getElementById('clientModal');
const deleteModal = document.getElementById('deleteModal');

const newClientBtn = document.getElementById('newClient');
const updateBtn = document.getElementById('updateClient');
const deleteBtn = document.getElementById('deleteClient');

const closeClientModalBtn = document.getElementById('closeClientModal');
const closeDeleteBtn = document.getElementById('closeDeleteModal');

function changeSelectedRow(id){
    if(selectedId)
        document.getElementById('clientId:' + selectedId).classList.remove('selected-row');

    document.getElementById(id).classList.add('selected-row');
    selectedId = id.replace('clientId:', '');
}

function removeClass(className){
	html.classList.remove(className);
}

function closeModal(modal){
	modal.close();
	removeClass('modal-is-closing');
	removeClass('modal-is-open');
}

newClientBtn.addEventListener('click', () => {
	document.getElementById('actionModal').value = 'create';
	document.getElementById('clientModalId').value = '';
	document.getElementById('clientModalTitle').textContent = 'Nuevo Cliente';
	
	clientModal.querySelector("[name='lastname']").value = '';
	clientModal.querySelector("[name='firstname']").value = '';
	clientModal.querySelector("[name='email']").value = '';
	clientModal.querySelector("[name='user']").value = '';
		
	document.getElementById('password').required = true;
	document.getElementById('passwordDiv').style.display = 'block';
	
    html.classList.add('modal-is-open');
	html.classList.add('modal-is-opening');
	clientModal.showModal();
	setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
});

updateBtn.addEventListener('click', () => {
	if(selectedId){
		document.getElementById('actionModal').value = 'update';
		document.getElementById('clientModalId').value = selectedId;
		document.getElementById('clientModalTitle').textContent = 'Modificar Cliente';
		
		const cells = document.getElementById('clientId:' + selectedId).getElementsByTagName('td');
		clientModal.querySelector("[name='lastname']").value = cells[0].textContent;
		clientModal.querySelector("[name='firstname']").value = cells[1].textContent;
		clientModal.querySelector("[name='email']").value = cells[2].textContent;
		clientModal.querySelector("[name='user']").value = cells[3].textContent;
		
		document.getElementById('password').required = false;
		document.getElementById('passwordDiv').style.display = 'none';
		
	    html.classList.add('modal-is-open');
		html.classList.add('modal-is-opening');
		clientModal.showModal();
		setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
  	}
  	else{
		alert('Primero seleccione un cliente');
	}
});

deleteBtn.addEventListener('click', () => {
	if(selectedId){
		document.getElementById('deleteModalId').value = selectedId;
	    html.classList.add('modal-is-open');
		html.classList.add('modal-is-opening');
		deleteModal.showModal();
		setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
  	}
  	else{
		alert('Primero seleccione un cliente');
	}
});

closeClientModalBtn.addEventListener('click',  () => {
	html.classList.add('modal-is-closing');
  	setTimeout(closeModal.bind(null, clientModal), 400);
});

closeDeleteBtn.addEventListener('click',  () => {
	html.classList.add('modal-is-closing');
  	setTimeout(closeModal.bind(null, deleteModal), 400);
});
