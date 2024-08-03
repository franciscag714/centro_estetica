<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
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
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
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
					    	<th scope="col">Descripción</th>
					      	<th scope="col">Tipo</th>
					      	<th scope="col">Precio</th>
			    		</tr>
					</thead>
		  			<tbody>
				    
<%	for (Service s : services){ %>
						<tr id="hola">
					      	<td><%= s.getDescription() %></td>
					      	<td><%= s.getType().getDescription() %></td>
					      	<td><%= s.getUpdatedPrice() %></td>
					    </tr>
<%	}	%>
					</tbody>
				</table>
				<div>
		  			<button id="newService">Nuevo servicio</button>
		  			<button id="updateService">Modificar</button>
		  			<button id="deleteService">Eliminar</button>
				</div>
			</div>
		</div>
		
	</body>
</html>