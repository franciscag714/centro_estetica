<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
<%
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
%>
</head>
	<body>
		<nav>
		  	<ul>
		    	<li><strong>Logo del centro</strong></li>
			</ul>
			<ul>
			    <li><a href="servicios">Nuestros servicios</a></li>
			    <li><a href="//google.com.ar">Ubicación</a></li>
			</ul>
		</nav>
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
				<tr>
			      	<th><%= s.getDescription() %></th>
			      	<td><%= s.getType().getDescription() %></td>
			      	<td><%= s.getUpdatedPrice() %></td>
			    </tr>
<%	}	%>
			</tbody>
		</table>
	</body>
</html>