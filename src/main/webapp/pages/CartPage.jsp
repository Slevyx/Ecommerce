<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
	<div class="cart-header">
		<div class="cart-logout"><a href="/Ecommerce/Logout"><button type="button" class="btn btn-warning">LOGOUT</button></a></div>
		<div class="back-button">
			<a href="/Ecommerce/pages/ShopPage.jsp">
				<button type="button" class="btn btn-warning">BACK TO SHOP PAGE</button>
			</a>
		</div>
	</div>
	<h1>Cart List:</h1>
	<p>${error}</p>
	<c:choose>
		<c:when test="${empty cartList}">
			<h2>${purchaseMessage}</h2>
			<p>Your Cart is empty. Go back to the Shop Page to buy some!</p>
		</c:when>
		<c:otherwise>
			<div class="articlesList">
				<div class="column-description">
					<div class="width-20">Name:</div>
					<div class="width-20">Availability:</div>
					<div class="width-20">Quantity:</div>
					<div class="width-20">Add or Remove:</div>
					<div class="width-20">Total Price:</div>
				</div>
				<c:forEach items="${cartList}" var="article">
					<div class="article">
						<div class="width-20">${article.name}</div>
						<c:choose>
							<c:when test="${article.availability >= article.quantity}">
								<div class="width-20"><div class="dot green"></div>Available</div>
							</c:when>
							<c:otherwise>
								<div class="width-20"><div class="dot red"></div>Not Available</div>
							</c:otherwise>
						</c:choose>
						<div class="width-20">${article.quantity}</div>
						<div class="width-20 space-around">
							<a href="/Ecommerce/AddRemoveArticles?action=add&id=${article.id}">
								<button type="button" class="btn btn-success">+</button>
							</a>
							<a href="/Ecommerce/AddRemoveArticles?action=remove&id=${article.id}">
								<button type="button" class="btn btn-danger">-</button>
							</a>
							<a href="/Ecommerce/RemoveAllArticles?id=${article.id}">
								<button type="button" class="btn btn-dark">Remove</button>
							</a>
						</div>
						<div class="width-20">${article.price * article.quantity}&euro;</div>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
	<c:if test="${not empty cartList}">
		<div class="width-20"><a href="/Ecommerce/Buy"><button type="button" class="btn btn-success">BUY</button></a></div>
		<div class="total">Total:${total}&euro;</div>
	</c:if>
</body>
</html>