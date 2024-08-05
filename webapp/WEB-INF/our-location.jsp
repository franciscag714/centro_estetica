<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Centro de Estética</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
	<link rel="stylesheet" type="text/css" href="styles/main.css">
	<link rel="stylesheet" type="text/css" href="styles/our-location.css">
</head>
	<body>
		<jsp:include page="topbar.jsp"/>
		<div class="flex-container-row">
			<iframe id="map" title="Ubicación Google Maps" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3347.8704282892736!2d-60.64630682359547!3d-32.95443017228297!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x95b7ab11d0eb49c3%3A0x11f1d3d54f950dd0!2sUniversidad%20Tecnol%C3%B3gica%20Nacional%20%7C%20Facultad%20Regional%20Rosario!5e0!3m2!1ses!2sar!4v1711402786413!5m2!1ses!2sar" class="rounded-5 map" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
			
			<form id="questions-form" action="preguntas" method="post">
				<label for="email">Correo electrónico</label>
				<input id="email" name="email" type="email" required>
				
				<label for="issue">Asunto</label>
				<select id="issue" name="issue" required>
					<option>Motivo 1</option>
					<option>Motivo 2</option>
					<option>Motivo 3</option>
					<option>Otro</option>
				</select>
				
				<label for="description">Describa su problema</label>
				<textarea id="description" name="description" rows=5 required></textarea>
				
				<input type="submit" value="Enviar">
			</form>
		</div>
	</body>
</html>
