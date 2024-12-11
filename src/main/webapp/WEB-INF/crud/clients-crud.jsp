<%@ page import="entities.Client" %>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>	
<%
	@SuppressWarnings("unchecked")
	LinkedList<Client> clients = (LinkedList<Client>) request.getAttribute("clientsList");
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
						</tr>
					</thead>
					<tbody>
				
<%	for (Client c : clients){ %>
						<tr id="client-id:<%= c.getId() %>" onclick="changeSelectedRow(this.id)">
							<td><%= c.getLastname() %></td>
							<td><%= c.getFirstname() %></td>
							<td><%= c.getEmail() %></td>
							<td><%= c.getUser() %></td>
						</tr>
<%	}	%>
					</tbody>
				</table>
			</div>
			
			<div class="buttons-div">
				<button id="new-client">Nuevo Cliente</button>
				<button id="update-client">Modificar</button>
				<button id="delete-client">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="client-modal" class="modal">
		<article>
			<header>
				<h2 id="client-modal-title">Cliente</h2>
			</header>
			<form method="post" action="clientes">
				<input type="hidden" name="action" id="action-modal" value="">
				<input type="hidden" name="id" id="client-modal-id" value="">
				
				<label for="firstname">Nombre</label>
				<input type="text" name="firstname" id="firstname" required>
				
				<label for="lastname">Apellido</label>
				<input type="text" name="lastname" id="lastname" required>
				
				<label for="email">Correo electrónico</label>
				<input type="email" name="email" id="email" required>
				
				<label for="user">Usuario</label>
				<input type="text" name="user" id="user" required>
				
				<label for="password">Contraseña</label>
				<input type="password" name="password" id="password" required minlength="4">
				
				<footer>
					<button type="button" class="secondary" onclick="closeModal('client-modal')">Cancelar</button>
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
			<form method="post" action="clientes">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="delete-modal-id" value="">
				<p>¿Está seguro que desea eliminar al cliente?</p>
				<footer>
					<button type="button" class="secondary" onclick="closeModal('delete-modal')">Cancelar</button>
					<button type="submit" class="delete-btn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/clients-crud.js"></script>
</body>
</html>