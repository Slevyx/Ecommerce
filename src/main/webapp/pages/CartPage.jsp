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
	<h1>Cart List:</h1>
	<c:choose>
		<c:when test="${empty cartList}">
			<p>No articles found.</p>
		</c:when>
		<c:otherwise>
			<div class="articlesList">
				<div class="column-description">
					<div class="width-20">Code:</div>
					<div class="width-20">Name:</div>
					<div class="width-20">Available:</div>
					<div class="width-20">Quantity:</div>
					<div class="width-20">Total Price:</div>
				</div>
				<c:forEach items="${cartList}" var="article">
					<div class="article">
						<div class="width-20">${article.code}</div>
						<div class="width-20">${article.name}</div>
						<div class="width-20">${article.availability}</div>
						<div class="width-20">${article.price}</div>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="width-20"><a href=""><button type="button" class="btn btn-primary">BUY</button></a></div>
</body>
</html>