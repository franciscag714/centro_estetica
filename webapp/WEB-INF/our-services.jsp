<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, "<link rel='stylesheet' type='text/css' href='styles/our-services.css'>") %>
	
<%
	LinkedList<Service> services = (LinkedList<Service>) request.getAttribute("servicesList");
%>

<body>
	<jsp:include page="common/topbar.jsp"/>
	<div id="articles-div">
<%
for (Service s : services){
%>
		<article>
			<header><%= s.getDescription() %></header>
			<body><%= s.getFormatedPrice() %></body>
		</article>
<%
}
%>
	</div>
</body>
</html>