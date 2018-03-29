<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pending Requests for Internal Users</title>
</head>
<body>
<%@ include file="adminmenu.jsp" %>
<div class="container">
<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>Pending Requests</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Employee ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="user" items="${users}">
				<tr>
					<td>
						${user.userId}
					</td>
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					<td>
						<spring:url value="/admin/approve/${user.modId}" var="adminApprovalUrl" />
						<spring:url value="/admin/decline/${user.modId}" var="adminDeclineUrl" />
						<button class="btn btn-info" onclick="location.href='${adminApprovalUrl}'">Approve</button>
						<button class="btn btn-info" onclick="location.href='${adminDeclineUrl}'">Decline</button>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>