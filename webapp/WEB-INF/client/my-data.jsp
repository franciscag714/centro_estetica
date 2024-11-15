<%@ page import="entities.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>

<%
	Client user = (Client) request.getAttribute("client"); 
%>
<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid" style="display:flex">
		<jsp:include page="../common/sidebar.jsp"/>
		
			<div style="width:50%">
			<h1>Mis Datos</h1>
			<form id="clientDataForm" method="POST" action="mis-datos">			
				<input type="hidden" name="action" value="update">
				
				<input type="hidden" name="id" id="id" value=<%= user.getId() %>>
			
				<label for="firstname">Nombre:</label>
				<input type="text" name="firstname" id="firstname" value="<%= user.getFirstname() %>" oninput="enableUpdateButton()">
				
				<label for="lastname">Apellido:</label>
				<input type="text" name="lastname" id="lastname" value="<%= user.getLastname() %>" oninput="enableUpdateButton()">
				
				<label for="email">Correo electrónico:</label>
				<input type="email" name="email" id="email" value="<%= user.getEmail() %>" oninput="enableUpdateButton()">
				
				<label for="user">Usuario:</label>
				<input type="text" name="user" id="user" value="<%= user.getUser() %>" oninput="enableUpdateButton()">
				
				<label for="password">Constraseña:
					<br><small>Dejar en blanco si desea mantener la contraseña actual</small>
				</label>
				<div role="group" style="position:relative;">
				    <input type="password" name="password" id="password" placeholder="Ingrese la nueva contraseña" oninput="enableUpdateButton()">
					<i id="eyeIcon" class="bx bx-show" style="position: absolute; right: 10px; top: 10px; cursor: pointer;"></i> <!-- Ícono de ojo visible, falta agregar estilos -->	
				</div>
				
				<div>
					<button type="submit" id="updateClientData" disabled>Guardar cambios</button>
				</div>
			</form>
		</div>
			
	</div>
	
	<script src="scripts/my-data.js"></script>
</body>
</html>
