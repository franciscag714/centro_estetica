<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Appointment" %>
<%@ page import="entities.Attention" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/attentions-crud.css'>") %>
<%
	int selectedId = (int) request.getAttribute("selectedId");
	
	@SuppressWarnings("unchecked")
	LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("appointmentsList");
	
	@SuppressWarnings("unchecked")
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
	
	@SuppressWarnings("unchecked")
	LinkedList<Attention> attentions = (LinkedList<Attention>) request.getAttribute("attentionsList");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
		<div class="content-div">
			<div id="appointments-div" class="table-div">
				<table>
					<thead>
						<tr>
							<th scope="col">Fecha y hora</th>
							<th scope="col">Empleado</th>
							<th scope="col">Cliente</th>
							<th scope="col">Ingresos</th>
						</tr>
					</thead>
					<tbody>
<% 	for (Appointment a : appointments) {	%>
						<tr id="appointment-id:<%= a.getId() %>" onclick="changeSelAppointment(<%= a.getId() %>)">
							<td><%= a.getFormattedDateTime() %></td>
							<td><%= a.getEmployee().getFullname() %></td>
							<td><%= a.getClient().getFullname() %></td>
							<td><%= a.getTotalIncome() %></td>
						</tr>
<%	}	%>
					</tbody>	
				</table>
			</div>
			
			<div id="attentions-div">
				<div class="table-div">
					<table id="attentions-table" data-selected-appointment=<%= selectedId %>>
						<thead>
							<tr>
								<th scope="col">Servicio</th>
								<th scope="col">Precio</th>
							</tr>
						</thead>
						<tbody>
<%
if (attentions != null) {
 	for (Attention a : attentions) {	%>
							<tr id="service-id:<%= a.getService().getId() %>">
								<td><%= a.getService().getDescription() %></td>
								<td><%= a.getFormatedPrice() %></td>
							</tr>
<%
	}
}
%>
						</tbody>
					</table>
<%
if (attentions == null) {	%>
					<p id="attentions-message">Seleccione un turno para ver los servicios brindados.</p>
<%
} else if (attentions.isEmpty()) {	%>
					<p id="attentions-message">No se brindó ninguna atención.</p>
<%
} else {	%>
					<p id="attentions-message" style="display:none;"></p>
<%
}
%>
				</div>
				
				<div id="attentions-actions" <%= attentions == null ? "style='display:none;'" : "" %>>
					<button id="create-attention">Nueva atención</button>
					<button id="delete-attention" <%= attentions != null && attentions.isEmpty() ? "style='display:none;'" : "" %>>Eliminar</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="create-attention-modal" class="modal">
		<article>
			<header>
				<h2>Nueva Atención</h2>
			</header>
			<form method="post" action="atenciones">
				<input type="hidden" name="action" value="create">
				<input type="hidden" id="appointment" name="appointment" value="">
				
				<label id="appointment-label">Turno: dd/MM/yyyy</label>
				<label id="client-label">Ciente: nombre-apellido</label>
				<label id="price">Servicio</label>
				<label for="service">Servicio</label>
				<select id="service" name="service" required>
<%	for (Service s : services){	%>
					<option
						value="<%= s.getId() %>"
						data-price="<%= s.getFormatedPrice() %>"
					>
						<%= s.getDescription() %>
					</option>
<%	}	%>
				</select>
				
				<footer>
					<button type="button" class="secondary" onclick="closeModal('create-attention-modal')">Cancelar</button>
					<button type="submit" style="width:auto">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="delete-modal" class="modal">
		<article>
			<header>
				<h2>Atención</h2>
			</header>
			<form method="post" action="atenciones">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="appointment" id="delete-modal-appointment-id" value="">
				<input type="hidden" name="service" id="delete-modal-service-id" value="">
				<p>¿Está seguro que desea eliminar la atención?</p>
				<footer>
					<button type="button" class="secondary" onclick="closeModal('delete-modal')">Cancelar</button>
					<button type="submit" class="delete-btn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/attentions-crud.js"></script>
</body>
</html>
