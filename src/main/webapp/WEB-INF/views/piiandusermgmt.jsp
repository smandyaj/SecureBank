<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PII And Employee Management</title>
</head>
<body>
<div class="container">
<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>All employeess</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#employeesID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Mobile</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="employee" items="${employees}">
				<tr>
					<td>
						${employee.id}
					</td>
					<td>${employee.firstName}</td>
					<td>${employee.lastName}</td>
					<td>${employee.email}</td>
					<td>${employee.mobile}</td>
					<td>
						<spring:url value="/employees/${employee.id}" var="employeeUrl" />
						<spring:url value="/employees/${employee.id}/delete" var="deleteUrl" /> 
						<spring:url value="/employees/${employee.id}/update" var="updateUrl" />

						<button class="btn btn-info" onclick="location.href='${employeeUrl}'">Query</button>
						<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
						<button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button></td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>