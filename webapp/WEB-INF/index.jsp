<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" type="text/css" href="styles/main.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
	<link rel="stylesheet" type="text/css" href="styles/login.css">
<%
   	Boolean userNotFound = (Boolean) request.getAttribute("userNotFound");
%>
</head>
<body>
	<jsp:include page="topbar.jsp"/>
    <div class="container-fluid flex-container-column">
		
<% if (userNotFound != null) { %>
		<small class="login-error">El usuario ingresado no existe</small>
<% } %>
		<form action="#" method="post" id="login-form">
			<fieldset>
				<label>
					Usuario
					<input type="text" name="user" placeholder="Usuario" required autofocus="">
				</label>
				<label>
					Contraseña
					<input type="password" name="password" placeholder="Contraseña" required>
				</label>
			</fieldset>
			
			<input type="submit">
		</form>
	</div>
</body>
</html>