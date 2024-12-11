<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/head.jsp" %>
<% 
    String styles = "<style>" +
        "div {" +
            "display: flex;" +
            "justify-content: center;" +
        "}" +
        
        ".button {" +
            "padding: 10px 20px;" +
            "background-color: #007bff;" +
            "color: white;" +
            "text-decoration: none;" +
            "border-radius: 5px;" +
        "}" +

        ".button:hover {" +
            "background-color: #0056b3;" +
        "}" +

        "img {" +
            "width: 100%;" +
            "height: auto;" +
            "max-height: 80vh;" +
            "object-fit: contain;" +
        "}" +
    "</style>";
%>
<%= generateHead(false, null, styles) %>

<body>
	<div>
		<img src="resources/error-500.jpg">
	</div>
	<div>
		<a href="index" class="button">Volver a cargar el Sitio Web</a>
	</div>
</body>
</html>