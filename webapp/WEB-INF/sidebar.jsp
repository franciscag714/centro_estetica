<%@ page import="entities.Employee" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% if (request.getSession().getAttribute("user").getClass() == Employee.class) { %>
	<aside>
	  	<nav>
	    	<ul>
	      		<li><a href="#">Atenciones</a></li>
	      		<li><a href="#">Turnos</a></li>
	      		<li><a href="#">Clientes</a></li>
	      		<li><a href="#">Servicios</a></li>
	      		<li><a href="#">Tipos de servicios</a></li>
	      		<li><a href="#">Empleados</a></li>
	    	</ul>
		</nav>
	</aside>
<% } %>