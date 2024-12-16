<%@ page import="entities.Employee" %>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	@SuppressWarnings("unchecked")
	LinkedList<Employee> employees = (LinkedList<Employee>) request.getAttribute("employeesList");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
		
		<div class="content-div" style="width:85%;">
			<div class="table-div">
				<table>
					<thead>
						<tr>
							<th scope="col">Apellido</th>
							<th scope="col">Nombre</th>
							<th scope="col">Correo</th>
							<th scope="col">Usuario</th>
							<th scope="col">Tipo empleado</th>
						</tr>
					</thead>
					<tbody>
<% for (Employee e: employees) { %>
						<tr id="employee-id:<%= e.getId() %>" onclick="changeSelectedRow(this.id)">
							<td><%= e.getLastname() %></td>
							<td><%= e.getFirstname() %></td>
							<td><%= e.getEmail() %></td>
							<td><%= e.getUser() %></td>
<%
	String tipo = ""; 
	if (e.isAdmin()) 
		tipo = "administrador";
	else 
		tipo = "empleado";
%>
							<td><%= tipo %></td>
						</tr>
<% } %>
					</tbody>
				</table>
			</div>
			
			<div class="buttons-div">
				<button id="new-employee">Nuevo empleado</button>
				<button id="update-employee" class="secondary-btn">Modificar</button>
				<button id="delete-employee" class="secondary-btn">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="employee-modal" class="modal">
		<article>
			<header>
				<h2 id="employee-modal-title">Empleado</h2>
			</header>
			<form method="post" action="empleados" >
				<input type="hidden" name="action" id="action-modal" value="">
				<input type="hidden" name="id" id="employee-modal-id" value="">
				
				<label for="firstname">Nombre</label>
				<input type="text" name="firstname" id="firstname" required>
				
				<label for="lastname">Apellido</label>
				<input type="text" name="lastname" id="lastname" required>
				
				<label for="email">Correo</label>
				<input type="email" name="email" id="email" required>
				
				<label for="user">Usuario</label>
				<input type="text" name="user" id="user" required>
				
				<label for="password">Contraseña</label>
				<input type="password" name="password" id="password" required>
				
				<label for="is-admin">Tipo empleado</label>
				<select id="is-admin" name="is-admin" required>
					<option value="empleado">Empleado</option>
					<option value="administrador">Administrador</option>
				</select>
				
				<footer>
					<button type="button" class="secondary-btn" onclick="closeModal('employee-modal')">Cancelar</button>
					<button type="submit">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="delete-modal" class="modal">
		<article>
			<header>
				<h2>Atención</h2>
			</header>
			<form method="post" action="empleados">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="delete-modal-id" value="">				
				<p>¿Esta seguro que desea eliminar al empleado?</p>
				<footer>
					<button type="button" class="secondary-btn" onclick="closeModal('delete-modal')">Cancelar</button>
					<button type="submit" class="delete-btn">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/utils.js"></script>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/employees-crud.js"></script>
</body>
</html>