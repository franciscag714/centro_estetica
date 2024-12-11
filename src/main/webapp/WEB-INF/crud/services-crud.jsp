<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
<%@ page import="entities.ServiceType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	@SuppressWarnings("unchecked")
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
	@SuppressWarnings("unchecked")	
	LinkedList<ServiceType> types = (LinkedList<ServiceType>) request.getAttribute("typesList");
%>

<body>
	<jsp:include page="../common/topbar.jsp"/>
	
	<div class="container-fluid main-container">
		<jsp:include page="../common/sidebar.jsp"/>
		
		<div class="content-div" style="width:50%;">
			<div class="table-div">
				<table>
					<thead>
						<tr>
							<th scope="col">Descripción</th>
							<th scope="col">Tipo</th>
							<th scope="col">Precio</th>
						</tr>
					</thead>
					<tbody>
					
<%	for (Service s : services){ %>
						<tr id="service-id:<%= s.getId() %>" onclick="changeSelectedRow(this.id)">
							<td><%= s.getDescription() %></td>
							<td data-typeid="<%= s.getType().getId() %>"><%= s.getType().getDescription() %></td>
							<td><%= s.getUpdatedPrice() %></td>
						</tr>
<%	}	%>
					</tbody>
				</table>
			</div>
			<div class="buttons-div">
				<button id="new-service">Nuevo servicio</button>
				<button id="update-service">Modificar</button>
				<button id="delete-service">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="service-modal" class="modal">
		<article>
			<header>
				<h2 id="service-modal-title">Servicio</h2>
			</header>
			<form method="post" action="abmc-servicios">
				<input type="hidden" name="action" id="action-modal" value="">
				<input type="hidden" name="id" id="service-id-modal" value="">
				
				<label for="desc">Descripción</label>
				<input type="text" name="description" id="desc" required>
				
				<label for="type">Tipo de servicio</label>
				<select id="type" name="type" required>
<% for (ServiceType t : types) { %>
					<option value=<%= t.getId() %>><%= t.getDescription() %></option>
<% } %>
				</select>
				
				<label for="price">Precio</label>
				<input type="number" name="price" id="price" min="0" step="0.01" required>

				<footer>
					<button type="button" class="secondary" onclick="closeModal('service-modal')">Cancelar</button>
					<button type="submit" style="width:auto">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="delete-modal" class="modal">
		<article>
			<header>
				<h2>Atención</h2>
			</header>
			<form method="post" action="abmc-servicios">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="delete-modal-id" value="">
				<p>¿Está seguro que desea eliminar el servicio?</p>
				<footer>
					<button type="button" class="secondary" onclick="closeModal('delete-modal')">Cancelar</button>
					<button type="submit" class="delete-btn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/services-crud.js"></script>
</body>
</html>