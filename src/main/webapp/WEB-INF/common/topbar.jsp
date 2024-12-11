<%@ page import="entities.Person" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%	Person p = (Person) session.getAttribute("user"); %>

<nav>
	<ul style="margin-left: 0px;">
		<li id="home-btn"><a href="index"><img src="resources/CE.jpg" alt="Logo Centro de Estética"></img></a></li>
	</ul>
	<ul style="margin-right: 10px;">
		<li class="tob-bar-item"><a href="servicios">Nuestros servicios</a></li>
		<li class="tob-bar-item"><a href="ubicacion">Ubicación</a></li>
<%	if (p != null) { %>
		<li>
			<details class="dropdown">
				<summary role="button">Bienvenido <%= p.getFirstname() %></summary>
				<ul>
					<li><a href="logout">Salir</a></li>
				</ul>
			</details>
		</li>
<% } %>
	</ul>
</nav>