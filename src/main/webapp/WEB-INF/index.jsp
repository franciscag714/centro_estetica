<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/login.css'>") %>

<body>
	<jsp:include page="common/topbar.jsp"/>
	
    <div class="container-fluid" id="div-login">
    	<h3>Iniciar sesión</h3>
		<form action="#" method="post" id="login-form">	
			<label>
				Usuario
				<input type="text" name="user" placeholder="Usuario" required autofocus>
			</label>
			<label>
				Contraseña
				<input type="password" name="password" placeholder="Contraseña" required>
			</label>
			
			<input type="submit" value="Ingresar" id="btn-login">
		</form>
	</div>
	
	<jsp:include page="common/show-alert.jsp"/>
</body>
</html>