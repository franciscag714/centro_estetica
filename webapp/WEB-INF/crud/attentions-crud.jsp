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

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
					
		<div style="width: 65%; margin: 10px;">
			<table style="width: 100%; border-collapse: collapse;">
				<thead>
					<tr>
						<th scope="col">Fecha y hora</th>
						<th scope="col">Empleado</th>
						<th scope="col">Cliente</th>
						<th scope="col">Ingresos</th>
					</tr>
				</thead>
				<tbody>
<% 	for (Appointment a : appointments){	%>
		
					<tr id="appointmentId:<%= a.getId() %>" onclick="changeSelAppointment(<%= a.getId() %>)">
						<td><%= a.getFormattedDateTime() %></td>
						<td><%= a.getEmployee().getFullname() %></td>
						<td><%= a.getClient().getFullname() %></td>
						<td><%= a.getTotalIncome() %></td>
					</tr>
<%	}	%>
				</tbody>	
			</table>
		</div>
		
		<div style="width: 35%; margin: 10px; border-left: 1px solid #ccc;">
			<p id="attentionsMessage" style="text-align: center; margin-top: 10px;">Seleccione un turno para ver los servicios brindados.</p><!-- poner mensaje de que no hay servicios también y ocultar tabla -->
			
			<table id="attentionsTable" style="display: none; width: 100%; border-collapse: collapse;">
				<thead>
					<tr>
						<th scope="col">Servicio</th>
						<th scope="col">Precio</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<div id="attentionsActions" style="display: none;">
				<button id="createAttention">Nueva atención</button>
				<button id="deleteAttention">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="createAttentionModal" class="modal">
		<article>
			<header>
				<h2 class="modal-title">Nueva Atención</h2>
			</header>
			<form method="post" action="atenciones">
				<input type="hidden" name="action" value="create">
				<input type="hidden" id="appointment" name="appointment" value="">
				
				<label id="appointmentLabel">Turno: dd/MM/yyyy</label>
				<label id="clientLabel">Ciente: nombre-apellido</label>
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
					<button type="button" class="secondary" onclick="closeModal('createAttentionModal')">Cancelar</button>
					<button type="submit" style="width:auto">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="deleteModal" class="modal">
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
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/attentions-crud.js"></script>
</body>
</html>
