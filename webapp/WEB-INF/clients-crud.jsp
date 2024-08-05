<%@ page import="entities.Client" %>
<%@ page import="java.util.LinkedList" %>
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
	LinkedList<Client> clients = (LinkedList<Client>) request.getAttribute("clientsList");
%>
</head>
	<body>
		<jsp:include page="topbar.jsp"/>
		
		<div class="container-fluid" style="display:flex;">
			<jsp:include page="sidebar.jsp"/>
			<div style="width:50%">
				<table>
					<thead>
						<tr>
					    	<th scope="col">Apellido</th>
					      	<th scope="col">Nombre</th>
					      	<th scope="col">Correo</th>
					      	<th scope="col">Usuario</th>
			    		</tr>
					</thead>
		  			<tbody>
				    
<%	for (Client c : clients){ %>
						<tr id="clientId:<%= c.getId() %>" onclick="changeSelectedRow(this.id)">
					      	<td><%= c.getLastname() %></td>
					      	<td><%= c.getFirstname() %></td>
					      	<td><%= c.getEmail() %></td>
					      	<td><%= c.getUser() %></td>
					    </tr>
<%	}	%>
					</tbody>
				</table>
				<div>
		  			<button id="newClient">Nuevo cliente</button>
		  			<button id="updateClient">Cambiar contraseña</button>
		  			<button id="updateClient">Modificar</button>
		  			<button id="deleteClient">Eliminar</button>
				</div>
			</div>
		</div>
		
		<!-- Modals -->
		<dialog id="clientModal">
	  		<article>
	  			<header>
	    			<h2 id="clientModalTitle" class="modal-title">Cliente</h2>
	    		</header>
	    		<form method="post" action="clientes">
	    			<input type="hidden" name="action" id="actionModal" value="">
	    			<input type="hidden" name="id" id="clientModalId" value="">
	    				    			
	    			<label for="firstname">Nombre</label>
	    			<input type="text" name="firstname" id="firstname" required>
					
					<label for="lastname">Apellido</label>
	    			<input type="text" name="lastname" id="lastname" required>
	    			
	    			<label for="email">Correo electrónico</label>
	    			<input type="email" name="email" id="email" required>
	    			
	    			<label for="user">Usuario</label>
	    			<input type="text" name="user" id="user" required>
	    			
	    			<label for="password">Contraseña</label>
	    			<input type="password" name="password" id="password" required>		    		
	    			
	    			<footer>
	      				<button type="button" id="closeClientModal" class="secondary">Cancelar</button>
	      				<button type="submit" style="width:auto">Guardar</button>
	    			</footer>
	    		</form>
	  		</article>
		</dialog>
		
		<!--<dialog id="deleteModal">
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
		</dialog>-->
		
		<script src="scripts/clients-crud.js"></script>
	</body>
</html>