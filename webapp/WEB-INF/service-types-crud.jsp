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
			      	<th scope="col">Modificar</th>
			      	<th scope="col">Eliminar</th>
			    </tr>
			</thead>
		  	<tbody>
				    
<%	for (ServiceType t : types){ %>
				<tr>
			      	<th><%= t.getDescription() %></th>
			      	<td></td>
			      	<td></td>
			    </tr>
<%	}	%>
			</tbody>
		</table>
		
		<div>
  			<button id="newType">Nuevo tipo</button>
		</div>
		
		<!-- Modals -->
		<dialog id="typeModal">
	  		<article>
	  			<header>
	    			<h2 id="typeModalTitle">tipo de servicio</h2>
	    		</header>
	    		<form method="post" action="tipos-servicios">
	    			<input type="hidden" name="action" id="actionModal" value="">
	    			<input type="hidden" name="id" id="typeModalId" value="">
	    			<label for="desc">Descripción</label>
	    			<input type="text" name="description" id="desc" required>
	    		
	    			<footer>
	      				<button type="button" id="closeTypeModal" className="secondary">Cancelar</button>
	      				<button type="submit">Guardar</button>
	    			</footer>
	    		</form>
	  		</article>
		</dialog>
		
		<script src="scripts/services-types-crud.js"></script>
	</body>
</html>