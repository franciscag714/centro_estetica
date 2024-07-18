<%@page import="entities.Person"%>
<%@ page import="entities.Employee" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	Person user = (Person) session.getAttribute("user");
	if (user != null && user.getClass() == Employee.class) {
%>
	<aside style="width:30%">
	  	<nav>
	    	<ul>
	      		<li><a href="#">Atenciones</a></li>
	      		<li><a href="#">Turnos</a></li>
	      		<li><a href="#">Clientes</a></li>
	      		<li><a href="#">Servicios</a></li>
	      		<li><a href="tipos-servicios">Tipos de servicios</a></li>
	      		<li><a href="#">Empleados</a></li>
	    	</ul>
		</nav>
	</aside>
<% } %>
