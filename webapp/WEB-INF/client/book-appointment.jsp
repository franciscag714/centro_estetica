<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Appointment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/book-appointment.css'>") %>
<% 
	@SuppressWarnings("unchecked")
	LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("availableAppointments");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	<div class="container-fluid" style="display:flex;">
		<jsp:include page="../common/sidebar.jsp"/>
		
		<form action="reservar-turno" method="get" id="filter-form">
			<h4>
				Filtrar turnos
				<button type="button" id="clear-filters-btn"><img src="resources/icons/delete.png" alt="Borrar filtros"></button>
			</h4>
			
			<label for="date">Fecha deseada</label>
			<input type="date" name="date" id="date" <%= request.getParameter("date") != null ? "value=" + request.getParameter("date") : "" %>>
			
			<label for="start-time">Hora desde</label>
			<input type="time" name="start-time" id="start-time" <%= request.getParameter("start-time") != null ? "value=" + request.getParameter("start-time") : "" %>>
			
			<label for="end-time">Hora hasta</label>
			<input type="time" name="end-time" id="end-time" <%= request.getParameter("end-time") != null ? "value=" + request.getParameter("end-time") : "" %>>
			
			<button type="submit">Buscar</button>
		</form>
		
		<div style="margin-left: 50px;">
<% 
if (appointments.isEmpty()) {
	out.println("<p>No hay turnos disponibles :(</p>");
}
else {
%>
			<form action="reservar-turno" method="post">
			    <ul>
<%
	for (Appointment a : appointments) {
		out.println(String.format(""
			+ "<li class='appointment-li'><label>"
		    + "<input type='radio' name='appointment-id' value='%s' required> %s"
			+ "</label></li>",
	        a.getId(), a.getFormattedDateTime()));
	}
%>
				</ul>
				<button type="submit">Reservar</button>
			</form>
<% } %>

		</div>
	</div>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/book-appointment.js"></script>
</body>
</html>