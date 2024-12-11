<%@ page import="entities.Client" %>
<%@ page import="entities.Person" %>
<%@ page import="entities.Employee" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	Person user = (Person) session.getAttribute("user");

	String path = request.getRequestURL().toString() .substring(request.getContextPath().length());
	String filename = path.substring(path.lastIndexOf("/") + 1);
%>

<button id="show-sidebar-btn">Menú</button>

<aside id="sidebar">
  	<nav>
    	<ul>
    	
<%	if (user != null && user.getClass() == Employee.class) {	%>
      		<li <%= filename.equals("attentions-crud.jsp") ? "class='selected'" : "" %>><a href="atenciones">Atenciones</a></li>
      		<li <%= filename.equals("appointments-crud.jsp") ? "class='selected'" : "" %>><a href="turnos">Turnos</a></li>
      		<li <%= filename.equals("clients-crud.jsp") ? "class='selected'" : "" %>><a href="clientes">Clientes</a></li>

<%		if (((Employee) user).isAdmin()) {	%>
      		<li <%= filename.equals("services-crud.jsp") ? "class='selected'" : "" %>><a href="abmc-servicios">Servicios</a></li>
      		<li <%= filename.equals("service-types-crud.jsp") ? "class='selected'" : "" %>><a href="tipos-servicios">Tipos de servicios</a></li>
      		<li <%= filename.equals("employees-crud.jsp") ? "class='selected'" : "" %>><a href="empleados">Empleados</a></li>
<%
		}
	}
	else if (user != null && user.getClass() == Client.class) {
%>
      		<li <%= filename.equals("book-appointment.jsp") ? "class='selected'" : "" %>><a href="reservar-turno">Reservar turno</a></li>
      		<li <%= filename.equals("my-data.jsp") ? "class='selected'" : "" %>><a href="mis-datos">Mis datos</a></li>
<%	}	%>
    	</ul>
	</nav>
</aside>
