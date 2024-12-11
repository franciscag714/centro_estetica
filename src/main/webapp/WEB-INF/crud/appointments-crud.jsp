<%@ page import="entities.Employee" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Appointment" %>
<%@ page import="entities.Client" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	@SuppressWarnings("unchecked")
	LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("appointmentsList");
	@SuppressWarnings("unchecked")	
	LinkedList<Client> clients = (LinkedList<Client>) request.getAttribute("clientsList");
	@SuppressWarnings("unchecked")
	LinkedList<Employee> employees = (LinkedList<Employee>) request.getAttribute("employeesList");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
		
		<div class="content-div" style="width:50%;">
			<div class="table-div">
				<table>
					<thead>
						<tr>
							<th scope="col">Fecha y hora</th>
							<th scope="col">Empleado</th>
							<th scope="col">Cliente</th>
						</tr>
					</thead>
					<tbody>
					<% for (Appointment a: appointments){ %>
						<tr id="appointment-id:<%= a.getId() %>" data-ismodifiable="<%= a.isModifiable() %>" onclick="changeSelectedRow(this.id)">
							<td><%= a.getFormattedDateTime() %></td>
							<td data-employeeid=<%= a.getEmployee().getId() %> ><%= a.getEmployee().getFullname() %></td>
							<td data-clientid=<%= a.getClient().getId() %> ><%= a.getClient().getFullname() != null ? a.getClient().getFullname() : "--disponible--"%></td>
						</tr>
					<% } %>
					</tbody>
				</table>
			</div>
			<div class="buttons-div">
				<button id="new-appointment">Nuevo Turno</button>
				<button id="update-appointment">Modificar</button>
				<button id="delete-appointment">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="appointment-modal" class="modal">
		<article>
			<header>
				<h2 id="appointment-modal-title">Turno</h2>
			</header>
			<form method="post" action="turnos">
				<input type="hidden" name="action" id="action-modal" value="">
				<input type="hidden" name="id" id="appointment-modal-id" value="">
				
				<label for="date-time">Horario</label>
				<input type="datetime-local" name="date-time" id="date-time" required>
				
				<label for="employee">Empleado</label>
				<select id="employee" name="employee" required>	
				<% for (Employee e: employees){%>
						<option value="<%= e.getId() %>"><%= e.getFullname() %></option>
				<%}%>
				</select>
				
				<label for="client" style="color:gray">Cliente (opcional)</label>
				<select id="client" name="client">
					<option value="0">--disponible--</option>
					
<% for (Client c: clients){%>
					<option value="<%= c.getId() %>"><%= c.getFullname() %></option>
<%}%>

				</select>
				
				<footer>
					<button type="button" class="secondary" onclick="closeModal('appointment-modal')">Cancelar</button>
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
			<form method="post" action="turnos">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="delete-modal-id" value="">
				<p>¿Está seguro que desea eliminar el turno?</p>
				<footer>
					<button type="button" class="secondary" onclick="closeModal('delete-modal')">Cancelar</button>
					<button type="submit" class="delete-btn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/appointments-crud.js"></script>
</body>
</html>