<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="entities.Appointment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/book-appointment.css'>") %>
<% 
	LocalDateTime currentDateTime = LocalDateTime.now();
	@SuppressWarnings("unchecked")
	Map<String, LinkedList<Appointment>> appointments = (Map<String, LinkedList<Appointment>>) request.getAttribute("availableAppointments");
	@SuppressWarnings("unchecked")
	LinkedList<Appointment> clientAppointments = (LinkedList<Appointment>) request.getAttribute("clientAppointments");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
		<div class="content-div">
			<div id="my-appointments-div">
				<h3 style="text-align:center;">Mis Turnos</h3>
<% 
if (clientAppointments.isEmpty()) {
	out.println("<p>No tiene ningún turno reservado.</p>");
}
else {
%>
				<div class="table-div">
		    		<ul>
<%
	for (Appointment a : clientAppointments) {
						out.println("<li><label>"+ a.getFullFormattedDateTime()+ "</label>");
		
		if (a.getDateTime().isAfter(currentDateTime)) {
						out.println("<button type='button' class='secondary-btn' "
		            		+ "data-appointment-id='" + a.getId() + "' "
		            		+ "data-datetime='" + a.getFullFormattedDateTime() + "' "
		            		+ "onclick='openUnbookModal(this)' style='width:100%'>Cancelar</button>");

		}
						out.println("</li>");
	}
%>
					</ul>
				</div>
<%	} %>
			</div>

			<div id="available-div">
    			<h3>
    				<span>Turnos Disponibles</span>
        			<button type="button" id="open-filters-btn"><img src="resources/icons/filter.png" alt="Mostrar filtros"></button>
    			</h3>
<% 
if (appointments.isEmpty()) {
	out.println("<p>No hay turnos disponibles :(</p>");
}
else {
%>
				<form action="reservar-turno" method="post" id="book-form">
        			<input type="hidden" name="action" value="book">
					<div class="table-div">
				    	<ul>
<%
	for (Map.Entry<String, LinkedList<Appointment>> entry : appointments.entrySet()) { %>
							<li><b><%= entry.getKey() %></b></li>
<%
		for (Appointment a : entry.getValue()) {
			out.println(String.format(""
				+ "<li><label>"
		    	+ "<input type='radio' name='appointment-id' value='%s' required> %s - Profesional: %s"
				+ "</label></li>",
	        	a.getId(), a.getFormattedTime(), a.getEmployee().getFullname()));
		}
	}
%>
						</ul>
					</div>
					<button type="submit">Reservar</button>
				</form>
<%
}
%>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="filters-modal" class="modal">
		<article>
			<header>
				<h2>Filtrar Turnos</h2>
			</header>
			
			<form action="reservar-turno" method="get" id="filters-form">
				<label for="date">Fecha deseada</label>
				<input type="date" name="date" id="date" <%= request.getParameter("date") != null ? "value=" + request.getParameter("date") : "" %>>
				
				<label for="start-time">Hora desde</label>
				<input type="time" name="start-time" id="start-time" <%= request.getParameter("start-time") != null ? "value=" + request.getParameter("start-time") : "" %>>
				
				<label for="end-time">Hora hasta</label>
				<input type="time" name="end-time" id="end-time" <%= request.getParameter("end-time") != null ? "value=" + request.getParameter("end-time") : "" %>>
								
				<footer>
					<button type="button" class="secondary-btn" id="clear-filters-btn">Borrar filtros</button>
					<button type="submit">Buscar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="unbook-modal" class="modal">
		<article>
			<header>
				<h2>Cancelar Turno</h2>
			</header>
			
			<form action="reservar-turno" method="post">
				<input type="hidden" name="action" value="unbook">
				<input type="hidden" name="appointment-id" id="unbook-modal-id" value="">
				<p id="unbook-modal-message"></p>
				
				<footer>
					<button type="button" class="secondary-btn" onclick="closeModal('unbook-modal')">No</button>
					<button type="submit" class="delete-btn">Sí, cancelar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/book-appointment.js"></script>
</body>
</html>