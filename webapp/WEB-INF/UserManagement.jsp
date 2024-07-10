<%@page import="entities.Employee"%>
<%@page import="data.EmployeeData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Centro de Estética</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
	
	<%
		Employee e = (Employee) session.getAttribute("usuario");
	%>
	</head>
	<body>
		<div class="container">
			<nav>
			  	<ul>
			    	<li><strong>Logo del centro</strong></li>
				</ul>
				<ul>
				    <li><a href="servicios">Nuestros servicios</a></li>
				    <li><a href="//maps.app.goo.gl/Fs1y6u17hcTVAViZ9">Ubicación</a></li>
				    <li><details class="dropdown">
 				 			<summary role="button">Bienvenido <%=e.getFirstname() %></summary>
  							<ul>
  								<li><a href="index.html">Salir</a></li>
 							</ul>
						</details>
  					</li>
				</ul>
			</nav>
			<h4>pagina</h4>
		</div>
	</body>
</html>