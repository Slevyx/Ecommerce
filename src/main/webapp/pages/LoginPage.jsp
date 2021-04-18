<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
	<form class="login-form" autocomplete="off" action="/Ecommerce/Login" method="POST">
		<label for="username">Username</label>
		<input type="text" name="username"/>
		<label for="password">Password</label>
		<input type="password" name="password"/>
		<button type="submit" class="btn btn-primary">Login</button>
	</form>
	<p>${error}</p>
</body>
</html>