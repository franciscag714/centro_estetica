<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Appointment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, null) %>
<% 	LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("availableAppointments"); %>

<body>
	<jsp:include page="common/topbar.jsp"/>
    
	<div class="container-fluid" style="display:flex;">
		<jsp:include page="common/sidebar.jsp"/>
		
		<form action="reservar-turno" method="get">
			<button type="submit" onclick="function()">Borrar filtros - mejorar</button>
		
			<label for="date">Fecha deseada</label>
			<input type="date" name="date" id="date" <%= request.getParameter("date") != null ? "value=" + request.getParameter("date") : "" %>>
			
			<label for="start-time">Hora desde</label>
			<input type="time" name="start-time" id="start-time" <%= request.getParameter("start-time") != null ? "value=" + request.getParameter("start-time") : "" %>>
			
			<label for="end-time">Hora hasta</label>
			<input type="time" name="end-time" id="end-time" <%= request.getParameter("end-time") != null ? "value=" + request.getParameter("end-time") : "" %>>
			
			<button type="submit">Buscar</button>
		</form>
		
<% 
if (appointments.isEmpty()) {
	
}
else {
%>
		<form action="reservar-turno" method="post">
		    <ul>
<%
	for (Appointment a : appointments) {
		out.println("<li>" + a.getFormattedDateTime() + "</li>");
	}
%>
			</ul>
		</form>
<% } %>
		
		
		<!--esto lo debería usar para mostrar mis turnos depues-->
	</div>
</body>
</html>