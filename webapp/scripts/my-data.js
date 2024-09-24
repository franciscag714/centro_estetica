
const html = document.getElementsByTagName('html')[0];

const clientDataModal = document.getElementById('clientDataModal');

const updateBtn = document.getElementById('updateClientData');

const closeClientDataModalBtn = document.getElementById('closeClientDataModal');

let idC;

function setId(id){
	idC = document.getElementById(id);
}

function removeClass(className){
	html.classList.remove(className);
}

function closeModal(modal){
	modal.close();
	removeClass('modal-is-closing');
	removeClass('modal-is-open');
}

updateBtn.addEventListener('click', () => {
	document.getElementById('clientDataModalId').value = idC;
	document.getElementById('clientDataModalTitle').textContent = 'Modificar datos';
	
	//no funciona
	document.getElementById('firstnameI').disabled = false;
	document.getElementById('lastnameI').disabled = false;
	document.getElementById('emailI').disabled = false;
	document.getElementById('userI').disabled = false;
	
	clientDataModal.querySelector("[name='firstname']").value = document.getElementById('firstnameI').value;
	clientDataModal.querySelector("[name='lastname']").value = document.getElementById('lastnameI').value;
	clientDataModal.querySelector("[name='email']").value = document.getElementById('emailI').value;
	clientDataModal.querySelector("[name='user']").value = document.getElementById('userI').value;
	
	/*
    document.getElementById('firstname').value = document.getElementById('firstnameI').value;
    document.getElementById('lastname').value = document.getElementById('lastnameI').value;
    document.getElementById('email').value = document.getElementById('emailI').value;
    document.getElementById('user').value = document.getElementById('userI').value;
	*/
	
	//por el momento
	document.getElementById('password').required = false;
	document.getElementById('passwordDiv').style.display = 'none';

    html.classList.add('modal-is-open');
	html.classList.add('modal-is-opening');
	clientDataModal.showModal();
	setTimeout(removeClass.bind(null, 'modal-is-opening'), 400);
});

closeClientDataModalBtn.addEventListener('click', () => {
	html.classList.add('modal-is-closing');
	setTimeout(closeModal.bind(null, clientDataModal), 400);
})