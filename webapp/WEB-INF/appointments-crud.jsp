<%@page import="entities.Employee"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.LinkedList"%>
<%@page import="entities.Appointment" %>
<%@page import="entities.Client" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, inital-scale=1.0">
		<title>Centro de Estética</title>
		<link rel="stylesheet" type="text/css" href="styles/main.css">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
	<%
		LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("appointmentsList");
		LinkedList<Client> clients = (LinkedList<Client>) request.getAttribute("clientsList");
		LinkedList<Employee> employees = (LinkedList<Employee>) request.getAttribute("employeesList");
	%>
		
	</head>
	<body>
		<jsp:include page="topbar.jsp"/>
		
		<div class="container-fluid" style="display:flex;">
			<jsp:include page="sidebar.jsp"/>
			<div style="width:50%">
				<table>
					<thead>
						<tr>
							<th scope="col">Horario</th>
							<th scope="col">Cliente</th>
							<th scope="col">Empleado</th>
						</tr>
					</thead>
					<tbody>
					<% for (Appointment a: appointments){ %>
						<tr id="appointmentId:<%= a.getId() %>" onclick="changeSelectedRow(this.id)">
							<td><%= a.getDateTime() %></td>
							<td><%= a.getClient().getFullname() %></td>
							<td><%= a.getEmployee().getFullname() %></td>
							
						</tr>			
					<% } %>		
					</tbody>
				</table>
				<div>
					<button id="newAppointment">Nuevo Turno</button>
					<button id="updateAppointment">Modificar</button>
					<button id="deleteAppointment">Eliminar</button>
				</div>
			</div>
		</div>
		
		<!-- Modals -->
		<dialog id="appointmentModal">
			<article>
				<header>
					<h2 id="appointmentModalTitle" class="modal-title">Turno</h2>
				</header>
				<form method="post" action="turnos">
					<input type="hidden" name="action" id="actionModal" value="">
					<input type="hidden" name="id" id="appointmentModalId" value="">
					
					<label for="date_time">Horario</label>
					<input type="datetime-local" name="date_time" id="date_time" required>
					
					<label for="client">Seleccionar cliente</label>
					<select id="client" name="client" required>	
					<% for (Client c: clients){%>
							<option value="<%= c.getId() %>"><%= c.getLastname()%>, <%= c.getFirstname() %></option>
					<%}%>
					</select>
					
					<label for="employee">Empleado</label>
					<select id="employee" name="employee" required>	
					<% for (Employee e: employees){%>
							<option value="<%= e.getId() %>"><%= e.getLastname()%>, <%= e.getFirstname() %></option>
					<%}%>
					</select>				
					<footer>
						<button type="button" id="closeAppointmentModal" class="secondary">Cancelar</button>
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
				<form method="post" action="turnos">
					<input type="hidden" name="action" value="delete">
					<input type="hidden" name="id" id="deleteModalId" value="">
					<p>¿Está seguro que desea eliminar el turno?</p>
					<footer>
						<button type="button" id="closeDeleteModal" class="secondary">Cancelar</button>
						<button type="submit" class="deleteBtn" style="width:auto">Eliminar</button>
					</footer>
				</form>
			</article>
		</dialog>
		
		<script src="scripts/appointments-crud.js"></script>
	</body>
</html>