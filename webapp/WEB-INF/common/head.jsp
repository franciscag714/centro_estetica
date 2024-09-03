<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%!
String generateHead(Boolean includeCss, String title, String additionalLines)
{
	if (title == null) title = "Centro de Estética";
	
	StringBuilder head = new StringBuilder();
	head.append("<!DOCTYPE html>");
	head.append("<html>");
	head.append("<head>");
	head.append("<meta charset='UTF-8'>");
	head.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
	head.append("<title>" + title + "</title>");
	
	if (includeCss){
		head.append("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css'>");
		head.append("<link rel='stylesheet' type='text/css' href='styles/main.css'>");
	}
	
	if (additionalLines != null) head.append(additionalLines);
	
	head.append("</head>");
	return head.toString();
}
%>
