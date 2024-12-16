<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.ServiceType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
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
						</tr>
					</thead>
					<tbody>
					
<%	for (ServiceType t : types){ %>
						<tr id="type-id:<%= t.getId() %>" onclick="changeSelectedRow(this.id)">
							<td><%= t.getDescription() %></td>
						</tr>
<%	}	%>
					</tbody>
				</table>
			</div>
			<div class="buttons-div">
				<button id="new-type">Nuevo tipo</button>
				<button id="update-type" class="secondary-btn">Modificar</button>
				<button id="delete-type" class="secondary-btn">Eliminar</button>
			</div>
		</div>
	</div>
	
	
	<!-- Modals -->
	<dialog id="type-modal" class="modal">
		<article>
			<header>
				<h2 id="type-modal-title">tipo de servicio</h2>
			</header>
			<form method="post" action="tipos-servicios">
				<input type="hidden" name="action" id="action-modal" value="">
				<input type="hidden" name="id" id="type-modal-id" value="">
				<label for="description">Descripción</label>
				<input type="text" name="description" id="description" required>
			
				<footer>
					<button type="button" class="secondary-btn" onclick="closeModal('type-modal')">Cancelar</button>
					<button type="submit">Guardar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<dialog id="delete-modal" class="modal">
		<article>
			<header>
				<h2>Atención</h2>
			</header>
			<form method="post" action="tipos-servicios">
				<input type="hidden" name="action" value="delete">
				<input type="hidden" name="id" id="delete-modal-id" value="">
				<p>¿Está seguro que desea eliminar el tipo de servicio?</p>
				<footer>
					<button type="button" class="secondary-btn" onclick="closeModal('delete-modal')">Cancelar</button>
					<button type="submit" class="delete-btn">Eliminar</button>
				</footer>
			</form>
		</article>
	</dialog>
	
	<jsp:include page="../common/show-alert.jsp"/>
	<script src="scripts/utils.js"></script>
	<script src="scripts/sidebar.js"></script>
	<script src="scripts/service-types-crud.js"></script>
</body>
</html>