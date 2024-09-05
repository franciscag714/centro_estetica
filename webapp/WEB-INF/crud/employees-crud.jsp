<%@ page import="entities.Employee" %>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	LinkedList<Employee> employees = (LinkedList<Employee>) request.getAttribute("employeesList");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid" style="display:flex;">
		<jsp:include page="../common/sidebar.jsp"/>
		<div style="width:50%">
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
				<%for (Employee e: employees) {%>
					<tr id="employeeId:<%= e.getId() %>" onclick="changeSelectedRow(this.id)">
						<td><%= e.getLastname() %></td>
						<td><%= e.getFirstname() %></td>
						<td><%= e.getEmail() %></td>
						<td><%= e.getUser() %></td>
						<%String tipo=""; 
						if (e.getIsAdmin()) 
							tipo="administrador";
						else 
							tipo="empleado";
						%>
						<td><%= tipo %></td>
					</tr>
				<% } %>
				</tbody>
			</table>
			<div>
				<button id="newEmployee">Nuevo empleado</button>
				<button id="updatePassword">Cambiar contraseña</button>
				<button id="updateEmployee">Modificar</button>
				<button id="deleteEmployee">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="employeeModal">
		<article>
			<header>
				<h2 id="employeeModalTitle" class="modal-title">Empleado</h2>
			</header>
			<form method="post" action="empleados" >
				<input type="hidden" name="action" id="actionModal" value="">
				<input type="hidden" name="id" id="employeeModalId" value="">
				
				<label for="firstname">Nombre</label>
				<input type="text" name="firstname" id="firstname" required>
				
				<label for="lastname">Apellido</label>
				<input type="text" name="lastname" id="lastname" required>
				
				<label for="email">Correo</label>
				<input type="email" name="email" id="email" required>
				
				<label for="user">Usuario</label>
				<input type="text" name="user" id="user" required>
				
				<label for="is_admin">Tipo empleado</label>
				<select id="is_admin" name="is_admin" required>
					<option value="empleado">Empleado</option>
					<option value="administrador">Administrador</option>
				</select>
				
				<div id="passwordDiv">
					<label for="password">Contraseña</label>
					<input type="password" name="password" id="password" required>
				</div>
				
				<footer>
					<button type="button" id="closeEmployeeModal" class="secondary">Cancelar</button>
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
			<form method="post" action="empleados">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="deleteModalId" value="">				
				<p>¿Esta seguro que desea eliminar al empleado?</p>
				<footer>
					<button type="button" id="closeDeleteModal" class="secondary">Cancelar</button>
					<button type="submit" class="deleteBtn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<script src="scripts/employees-crud.js"></script>
</body>
</html>