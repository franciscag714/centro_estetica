<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" type="text/css" href="styles/main.css" /> 
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
<%
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
%>
</head>
	<body>
		<jsp:include page="topbar.jsp"/>
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