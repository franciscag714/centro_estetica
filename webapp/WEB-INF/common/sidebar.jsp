<%@ page import="entities.Client" %>
<%@ page import="entities.Person" %>
<%@ page import="entities.Employee" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%	Person user = (Person) session.getAttribute("user");	%>
<aside style="display:auto;">
  	<nav style="width: 100%;">
    	<ul style="padding:15px;" >
<%	if (user != null && user.getClass() == Employee.class) {	%>
      		<li><a href="atenciones">Atenciones</a></li>
      		<li><a href="turnos">Turnos</a></li>
      		<li><a href="clientes">Clientes</a></li>
      		<li><a href="abmc-servicios">Servicios</a></li>
      		<li><a href="tipos-servicios">Tipos de servicios</a></li>
      		<li><a href="empleados">Empleados</a></li>
<%
	}
	else if (user != null && user.getClass() == Client.class) {
%>
      		<li><a href="reservar-turno">Reservar turno</a></li>
      		<li><a href="#">Mis datos</a></li>
<%	}	%>
    	</ul>
	</nav>
</aside>
