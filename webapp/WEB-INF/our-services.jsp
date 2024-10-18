<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.ServiceType" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/our-services.css'>") %>
	
<%
	@SuppressWarnings("unchecked")
	LinkedList<ServiceType> serviceTypes = (LinkedList<ServiceType>) request.getAttribute("serviceTypesList");
%>

<body>
	<jsp:include page="common/topbar.jsp"/>
	
<%
for (ServiceType type : serviceTypes) {
	if (!type.getServices().isEmpty()) {
%>
		<div class="div-h3"><h3><span><%= type.getDescription().toUpperCase() %></span></h3></div>
		<div id="articles-div">
<%
		for (Service service : type.getServices()) {
%>
		
			<article>
				<header><%= service.getDescription() %></header>
				<body><%= service.getFormatedPrice() %></body>
			</article>
<%
		}
%>
		</div>
<%
	}
}
%>
	
</body>
</html>