const html = document.getElementsByTagName('html')[0];
let selectedId;

const attentionsTable = document.getElementById('attentionsTable');


function changeSelectedRow(id){
	if (selectedId)
		document.getElementById('appointmentId:' + selectedId).classList.remove('selected-row');

	document.getElementById(id).classList.add('selected-row');

	selectedId = id.replace('appointmentId:', '');
	loadAttentionsTable();
}

/* mmm no se, ya no podia pensar mas y en contre esto
function loadAttentionsTable() {
    if (!selectedId) {
        attentionsTable.style.display = 'none';
        return;
    }
    
    attentionsTable.style.display = 'block';

    // Hacer una solicitud al servidor para obtener las atenciones
    fetch(`/api/attentions?appointmentId:=${selectedId}`)
        .then(response => response.json())
        .then(data => {
            const tbody = attentionsTable.querySelector('tbody');
            tbody.innerHTML = ''; // Limpiar la tabla existente

            data.attentions.forEach(attention => {
                const row = document.createElement('tr');
                row.id = `attentionId:${attention.appointmentId}-${attention.serviceId}`;
                row.innerHTML = `
                    <td>${attention.serviceDescription}</td>
                    <td>${attention.servicePrice}</td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error al cargar atenciones:', error);
        });
}
*/
