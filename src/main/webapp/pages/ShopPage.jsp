<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shop</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
	<div class="header">
		<h2>Welcome, ${loggedUser}!</h2>
		<a href="/Ecommerce/pages/CartPage.jsp"><img src="${pageContext.request.contextPath}/img/cart.png" width="50"></a>
		<p>${user_articles}</p>
	</div>
	<c:choose>
		<c:when test="${empty articlesList}">
			<p>No articles found.</p>
		</c:when>
		<c:otherwise>
			<div class="articlesList">
				<div class="column-description">
					<div class="width-20">Code:</div>
					<div class="width-20">Name:</div>
					<div class="width-20">Availability:</div>
					<div class="width-20">Price:</div>
					<div class="width-20">Buy Articles:</div>
				</div>
				<c:forEach items="${articlesList}" var="article">
					<div class="article">
						<div class="width-20">${article.code}</div>
						<div class="width-20">${article.name}</div>
						<div class="width-20">${article.availability}</div>
						<div class="width-20">${article.price}</div>
						<div class="width-20">
							<a href="/Ecommerce/AddToCart?id=${article.id}"><button type="button" class="btn btn-primary">Add To Cart</button></a>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>