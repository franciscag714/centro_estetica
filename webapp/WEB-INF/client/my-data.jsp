<%@ page import="entities.Client" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, 
		"<link rel='stylesheet' href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css'><link rel='stylesheet' href='styles/my-data.css'") %>
<%	Client client = (Client) session.getAttribute("user"); %>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
		
		<div id="mainDiv">
			<h1 style="text-align: center;">Mis Datos</h1>
			<form id="clientDataForm" method="POST" action="mis-datos">
				
				<div class="form-group">
					<label for="firstname">Nombre</label>
					<input type="text" name="firstname" id="firstname" value="<%= client.getFirstname() %>" oninput="enableUpdateButton()">
				
					<label for="lastname" style="margin-left:15px; margin-right:5px;">Apellido</label>
					<input type="text" name="lastname" id="lastname" value="<%= client.getLastname() %>" oninput="enableUpdateButton()">
				</div>
				
				<div class="form-group">
					<label for="email">Correo&nbsp;electrónico</label>
					<input type="email" name="email" id="email" value="<%= client.getEmail() %>" oninput="enableUpdateButton()">
				</div>
				
				<div class="form-group">
					<label for="user">Usuario</label>
					<input type="text" name="user" id="user" value="<%= client.getUser() %>" oninput="enableUpdateButton()">
					
					<label for="password">Constraseña</label>
					<div id="passwordDiv" role="group">
					    <input type="password" name="password" id="password" placeholder="No modificar" oninput="enableUpdateButton()">
						<i id="eyeIcon" class="bx bx-show"></i>
					</div>
				</div>
				
				<div id="button-div">
					<button type="submit" id="updateClientData" disabled>Guardar cambios</button>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/my-data.js"></script>
</body>
</html>
