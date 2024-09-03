<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/head.jsp" %>

<%= generateHead(true, null, null) %>
<%
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
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
					<tr id="hola">
						<td><%= s.getDescription() %></td>
						<td><%= s.getType().getDescription() %></td>
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
	
</body>
</html>