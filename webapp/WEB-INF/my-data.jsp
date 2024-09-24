<%@page import="entities.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, null) %>

<% Client c = (Client) request.getSession().getAttribute("user");%>

<body>
	<jsp:include page="common/topbar.jsp"/>
	
	<div class="container-fluid" style="display:flex">
		<jsp:include page="common/sidebar.jsp"/>
			<div style="width:50%">
				
				<h1>Mis Datos</h1>
				
				<input type="hidden" name="idI" id="<%=c.getId()%>">
				
				<label for="firstnameI">Nombre:</label>
				<input type="text" name="firstnameI" id="firstnameI" value="<%= c.getFirstname()%>" disabled>
				
				<label for="lastnameI">Apellido:</label>
				<input type="text" name="lastnameI" id="lastnameI" value="<%= c.getLastname()%>" disabled>
				
				<label for="emailI">Correo electrónico:</label>
				<input type="email" name="emailI" id="emailI" value="<%= c.getEmail() %>" disabled>
				
				<label for="userI">Usuario:</label>
				<input type="text" name="userI" id="userI" value="<%= c.getUser() %>" disabled>
				
				<div>
					<button id="updateClientData" onclick="setId(idI.id)">Modificar</button>
				</div>
			</div>
			
	</div>
	
	<!-- Modal -->
	
	<dialog id="clientDataModal">
		<article>
			<header>
				<h2 id="clientDataModalTitle" class="modal-title">Datos</h2>
			</header>
			<form method="post" action="mis-datos">
				<input type="hidden" name="action" id="actionModal" value="update">
				<input type="hidden" name="id" id="clientDataModalId" value="">
				
				<label for="firstname">Nombre</label>
				<input type="text" name="firstname" id="firstname" required>
				
				<label for="lastname">Apellido</label>
				<input type="text" name="lastname" id="lastname" required>
				
				<label for="email">Correo electrónico</label>
				<input type="email" name="email" id="email" required>
				
				<label for="user">Usuario</label>
				<input type="text" name="user" id="user" required>
				
				<div id="passwordDiv">
					<label for="password">Contraseña</label>
					<input type="password" name="password" id="password" required>
				</div>
				
				<footer>
					<button type="button" id="closeClientDataModal" class="secondary">Cancelar</button>
					<button type="submit" style="width:auto">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<script src="scripts/my-data.js"></script>
</body>
</html>
