<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
		<meta charset="ISO-8859-1">
		<title>Centro de Est�tica</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.colors.min.css"/>
<body>
		<div class="container">
					<nav>
					  	<ul>
					    	<li><strong>Logo del centro</strong></li>
						</ul>
						<ul>
						    <li><a href="servicios">Nuestros servicios</a></li>
						    <li><a href="//maps.app.goo.gl/Fs1y6u17hcTVAViZ9">Ubicaci�n</a></li>
						</ul>
					</nav>
				<h2>Ingrese su usuario</h2>
				<small class="pico-color-red-500">El usuario ingresado no existe</small>
					<form action="signin" method="post" style="width:50%">
						<fieldset>
						    <label>
								Usuario
						      	<input type="text" name="user" placeholder="Usuario" required="" autofocus="">
						    </label>
						    <label>
						      	Contrase�a
						      	<input type="password" name="password" placeholder="Contrase�a" required="">
						    </label>
						</fieldset>
						
						<input type="submit">
					</form>
		</div>
</body>
</html>