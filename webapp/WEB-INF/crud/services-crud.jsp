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
	
	<div class="container-fluid" style="display:flex;">
		<jsp:include page="../common/sidebar.jsp"/>
		<div style="width:50%">
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
					<tr id="serviceId:<%= s.getId() %>" onclick="changeSelectedRow(this.id)">
						<td><%= s.getDescription() %></td>
						<td data-typeid="<%= s.getType().getId() %>"><%= s.getType().getDescription() %></td>
						<td><%= s.getUpdatedPrice() %></td>
					</tr>
<%	}	%>
				</tbody>
			</table>
			<div>
				<button id="newService">Nuevo servicio</button>
				<button id="updateService">Modificar</button>
				<button id="deleteService">Eliminar</button>
			</div>
		</div>
	</div>
	
	<!-- Modals -->
	<dialog id="serviceModal">
		<article>
			<header>
				<h2 id="serviceModalTitle" class="modal-title">Servicio</h2>
			</header>
			<form method="post" action="abmc-servicios">
				<input type="hidden" name="action" id="actionModal" value="">
				<input type="hidden" name="id" id="serviceIdModal" value="">
				
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
					<button type="button" id="closeServiceModal" class="secondary">Cancelar</button>
					<button type="submit" style="width:auto">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="deleteModal">
		<article>
			<header>
				<h2 class="modal-title">Atención</h2>
			</header>
			<form method="post" action="abmc-servicios">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="deleteModalId" value="">
				<p>¿Está seguro que desea eliminar el servicio?</p>
				<footer>
					<button type="button" id="closeDeleteModal" class="secondary">Cancelar</button>
					<button type="submit" class="deleteBtn" style="width:auto">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<script src="scripts/services-crud.js"></script>
</body>
</html>