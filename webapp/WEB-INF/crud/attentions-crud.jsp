<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Appointment" %>
<%@ page import="entities.Attention" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	@SuppressWarnings("unchecked")
	LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("appointmentsList");
	@SuppressWarnings("unchecked")
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
%>

<!-- Aclaro que quise hacer: dos tablas primero appoint y que al seleccionar una fila aparezca la de att
junto con los botones de agregar, mod y eliminar, los cuales abren modal -->
<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid" style="display:flex;">
		<jsp:include page="../common/sidebar.jsp"/>
					
		<div style="width: 45%; margin: 10px;">
			<table style="width: 100%; border-collapse: collapse;">
				<thead>
					<tr>
						<th scope="col">Fecha y hora</th>
						<th scope="col">Empleado</th>
						<th scope="col">Cliente</th>
						<th scope="col">Total</th>
					</tr>
				</thead>				
				<tbody>
<% 	for (Appointment a : appointments){	%>
		
					<tr id="appointmentId:<%= a.getId() %>" onclick="changeSelAppointment(<%= a.getId() %>)">
						<td><%= a.getFormattedDateTime() %></td>
						<td data-employeeid=<%= a.getEmployee().getId() %> ><%= a.getEmployee().getFullname() %></td>
						<td data-clientid=<%= a.getClient().getId() %>> <%= a.getClient().getFullname() %></td>
						<td>$----</td>
					</tr>
<%	}	%>

				</tbody>	
			</table>
		</div>
		
		<div id="attentionsDiv" style="display:none; width: 45%; margin: 10px; border-left: 1px solid #ccc;">						
			<table style="width: 100%; border-collapse: collapse;">
				<thead>
					<tr>
						<th scope="col">Servicio</th>
						<th scope="col">Precio</th>
					</tr>
				</thead>
				<tbody id="attentionsList"></tbody>
			</table>
			<div>
				<button id="createAttention">Nueva atención</button>
				<button id="deleteAttention">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="createAttentionModal">
		<article>
			<header>
				<h2 class="modal-title">Nueva Atención</h2>
			</header>
			<form method="post" action="atenciones">
				<input type="hidden" name="action" value="create">
				<input type="hidden" id="appointment" name="appointment" value="">
				
				<label id="appointmentLabel">Turno: dd/MM/yyyy</label>
				<label id="clientLabel">Ciente: nombre-apellido</label>
				
				<label for="service">Servicio</label>
				<select id="service" name="service" required>
<%	for (Service s : services){	%>
					<option value=<%= s.getId() %>><%= s.getDescription() %></option>
<%	}	%>
				</select>

				<label id="price">Servicio</label>
				
				<footer>
					<button type="button" class="secondary" onclick="closeModal('createAttentionModal')">Cancelar</button>
					<button type="submit" style="width:auto">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="deleteModal">
		<article>
			<header>
				<h2 class="modal-title">Atención</h2>
			</header>
			<form method="post" action="atenciones">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="appointment" id="deleteModalAppointmentId" value="">
				<input type="hidden" name="service" id="deleteModalServiceId" value="">
				<p>¿Está seguro que desea eliminar la atención?</p>
				<footer>
					<button type="button" class="secondary" onclick="closeModal('deleteModal')">Cancelar</button>
					<button type="submit" class="deleteBtn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<script src="scripts/attentions-crud.js"></script>
</body>
</html>
