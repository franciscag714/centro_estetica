<%@ page import="entities.Employee" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%	Employee e = (Employee) session.getAttribute("user"); %>

<nav>
	<ul>
		<li><a href="index"><img id="btn-logo" src="resources/CE.jpg" alt="Logo Centro de Estética"></img></a></li>
	</ul>
	<ul>
		<li><a href="servicios">Nuestros servicios</a></li>
		<li><a href="ubicacion">Ubicación</a></li>
<%	if (e != null) { %>
		<li>
			<details class="dropdown">
				<summary role="button">Bienvenido <%= e.getFirstname() %></summary>
				<ul>
					<li><a href="logout">Salir</a></li>
				</ul>
			</details>
		</li>
<% } %>
	</ul>
</nav>