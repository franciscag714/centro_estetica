<%@ page import="entities.Alert" %>
<%
if (request.getAttribute("alert") != null) {
	Alert alert = (Alert) request.getAttribute("alert");	
%>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script>
		Swal.fire({
    		icon: "<%= alert.getIcon() %>",
    		title: "<%= alert.getMessage() %>",
    		confirmButtonColor: "#f0ad4e",
		});
	</script>
<% } %>
