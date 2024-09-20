<%@page import="entities.Service"%>
<%@page import="entities.Appointment"%>
<%@page import="entities.Attention"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	LinkedList<Appointment> appointments = (LinkedList<Appointment>) request.getAttribute("appointmentsList");
    LinkedList<Attention> attentions = (LinkedList<Attention>) request.getAttribute("attentionsList");
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
%>

<!-- Aclaro que quise hacer: dos tablas primero appoint y que al seleccionar una fila aparezca la de att
junto con los botones de agregar, mod y eliminar, los cuales abren modal -->
<body>
	<jsp:include page="../common/topbar.jsp"/>
	
		<div class="container-fluid" style="display:flex; flex-direction:row; justify-content:space-between; align-items:flex-start;">
			<jsp:include page="../common/sidebar.jsp"/>
			<!-- supuestamente todo lo que agregue hace que se muestre la tabla desp -->
			
			<div style="width: 45%; margin: 10px;">
				<table id="appointmentsTable" style="width: 100%; border-collapse: collapse;">
					<thead>
						<tr>
							<th scope="col">Fecha y hora</th>
							<th scope="col">Empleado</th>
							<th scope="col">Cliente</th>
							<th scope="col">Total</th>
						</tr>
					</thead>
					<tbody>
					<% for (Appointment a: appointments){ %>
						<tr id="appointmentId:<%= a.getId() %>" onclick="changeSelectedRow(this.id)">
							<td><%= a.getFormattedDateTime() %></td>
							<td data-employeeid=<%= a.getEmployee().getId() %> ><%= a.getEmployee().getFullname() %></td>
							<% if(a.getClient()!=null){ %>
							<td data-clientid=<%= a.getClient().getId() %>> <%= a.getClient().getFullname() %></td>
							<% } %>			
						<% } %>	
							<td></td>
						</tr>
					</tbody>	
				</table>
			</div>
			
			<!-- no funciona, tira error en la linea 49 -->
			<div style="display:none; width: 45%; margin: 10px; border-left: 1px solid #ccc;">
				<% for (Attention att: attentions){ 
						//if (att.getAppointment().getId() == app.getId()){ %>
						
							<table id="attentionsTable" style="width: 100%; border-collapse: collapse;">
								<thead>
									<tr>
										<th scope="col">Servicio</th>
										<th scope="col">Precio</th>
									</tr>
								</thead>
								<tbody>
									<tr id="attentionId:<%= att.getAppointment().getId() %>-<%=att.getService().getId() %>">
										<td><%= att.getService().getDescription() %></td>
										<td><%= att.getService().getFormatedPrice() %></td>
									</tr>
								
								</tbody>
							</table>
							<% } %>
						<%// } %>
							<div>
								<button id="newAttention">Nueva atención</button>
								<button id="updateAttention">Modificar</button>
								<button id="deleteAttention">Eliminar</button>
							</div>
				</div>
			
		</div>
		
				
		<!-- Modals, todavia no estan -->
		<dialog id="attentionModal">
			<article>
				<header>
					<h2 id="attentionModalTitle" class="modal-title">Atentión</h2>
				</header>
				<form method="post" action="atenciones">
					<label for="appointment">Turno</label>
					<select id="appointment" name="appointment" required>
					</select>
					
					<label for="service">Servicio</label>
					<select id="service" name="service" required>
					</select>
					
					<footer>
						<button type="button" id="closeAttentionModal" class="secondary">Cancelar</button>
						<button type="submit" style="width:auto">Guardar</button>
					</footer>
				</form>
			</article>
		</dialog>
		
		<dialog id="deleteModal">
			<article>
				<header>
					<h2 class="modal-title">Ateción</h2>
				</header>
				<form method="post" action="atenciones">
					<input type="hidden" name="action" value="delete">
					<input type="hidden" name="id" id="deleteModalId" value="">
					<p>¿Está seguro que desea eliminar la atención?</p>
					<footer>
						<button type="button" id="closeDeleteModal" class="secondary">Cancelar</button>
						<button type="submit" class="deleteBtn" style="width:auto">Eliminar</button>
					</footer>
				</form>
			</article>
		</dialog>
		
		<script src="scripts/attentions-crud.js"></script>
</body>
</html>

