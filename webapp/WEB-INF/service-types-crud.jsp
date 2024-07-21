<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.ServiceType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" type="text/css" href="styles/main.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
<%
	LinkedList<ServiceType> types = (LinkedList<ServiceType>) request.getAttribute("typesList");
%>
</head>
	<body>
		<jsp:include page="topbar.jsp"/>

		<table>
			<thead>
				<tr>
			    	<th scope="col">Descripción</th>
			    </tr>
			</thead>
		  	<tbody>
				    
<%	for (ServiceType t : types){ %>
				<tr id="typeId:<%= t.getId() %>" onclick="changeSelectedRow(this.id)">
			      	<td><%= t.getDescription() %></td>
			    </tr>
<%	}	%>
			</tbody>
		</table>
		
		<div>
  			<button id="newType">Nuevo tipo</button>
  			<button id="updateType">Modificar</button>
  			<button id="deleteType">Eliminar</button>
		</div>
		
		
		<!-- Modals -->
		<dialog id="typeModal">
	  		<article>
	  			<header>
	    			<h2 id="typeModalTitle" class="modal-title">tipo de servicio</h2>
	    		</header>
	    		<form method="post" action="tipos-servicios">
	    			<input type="hidden" name="action" id="actionModal" value="">
	    			<input type="hidden" name="id" id="typeModalId" value="">
	    			<label for="desc">Descripción</label>
	    			<input type="text" name="description" id="desc" required>
	    		
	    			<footer>
	      				<button type="button" id="closeTypeModal" class="secondary">Cancelar</button>
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
	    		<form method="post" action="tipos-servicios">
	    			<input type="hidden" name="action" value="delete">
	    			<input type="hidden" name="id" id="deleteModalId" value="">
	    			<p>¿Está seguro que desea eliminar el tipo de servicio?</p>
	    			<footer>
	      				<button type="button" id="closeDeleteModal" class="secondary">Cancelar</button>
	      				<button type="submit" class="deleteBtn" style="width:auto">Eliminar</button>
	    			</footer>
	    		</form>
	  		</article>
		</dialog>
		
		<script src="scripts/services-types-crud.js"></script>
	</body>
</html>