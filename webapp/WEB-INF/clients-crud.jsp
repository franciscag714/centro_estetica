<%@ page import="entities.Client" %>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" type="text/css" href="styles/main.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
<%
	LinkedList<Client> clients = (LinkedList<Client>) request.getAttribute("clientsList");
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
					    	<th scope="col">Apellido</th>
					      	<th scope="col">Nombre</th>
					      	<th scope="col">Correo</th>
					      	<th scope="col">Usuario</th>
			    		</tr>
					</thead>
		  			<tbody>
				    
<%	for (Client c : clients){ %>
						<tr id="clientId:<%= c.getId() %>">
					      	<th><%= c.getLastname() %></th>
					      	<th><%= c.getFirstname() %></th>
					      	<th><%= c.getEmail() %></th>
					      	<th><%= c.getUser() %></th>
					    </tr>
<%	}	%>
					</tbody>
				</table>
				<div>
		  			<button id="newService">Nuevo cliente</button>
		  			<button id="updateService">Cambiar contraseña</button>
		  			<button id="updateService">Modificar</button>
		  			<button id="deleteService">Eliminar</button>
				</div>
			</div>
		</div>
		
	</body>
</html>