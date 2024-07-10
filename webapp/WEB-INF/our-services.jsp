<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
<%@ page import="data.ServiceData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
<%
	ServiceData data = new ServiceData();
	LinkedList<Service> services = data.list();
%>
</head>
	<body>
		<nav>
		  	<ul>
		    	<li><strong>Logo del centro</strong></li>
			</ul>
			<ul>
			    <li><a href="#">Nuestros servicios</a></li>
			    <li><a href="//google.com.ar">Ubicación</a></li>
			</ul>
		</nav>
		<div>
<%
	for (Service s : services){
%>
			<article><%= s.getDescription() %></article>
<%
	}
%>
		</div>
	</body>
</html>