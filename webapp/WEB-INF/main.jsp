<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>

<%= generateHead(true, null, null) %>

<body>
	<jsp:include page="common/topbar.jsp"/>
	<div class="container-fluid" style="display:flex;">
		<jsp:include page="common/sidebar.jsp"/>
		<div style="width:50%">
			<img src="resources/obra-en-construccion.jpg">
		</div>
	</div>
</body>
</html>