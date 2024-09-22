<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/login.css'>") %>
<% Boolean userNotFound = (Boolean) request.getAttribute("userNotFound"); %>

<body>
	<jsp:include page="common/topbar.jsp"/>
    <div class="container-fluid flex-container-column">
		
<% if (userNotFound != null) { %>
		<small class="login-error">El usuario ingresado no existe</small>
<% } %>
		<form action="#" method="post" id="login-form">
			<fieldset>
				<label>
					Usuario
					<input type="text" name="user" placeholder="Usuario" required autofocus>
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