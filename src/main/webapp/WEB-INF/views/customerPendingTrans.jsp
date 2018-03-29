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
<title>Pending Requests for Customer Transactions</title>
</head>
<body>
<%@ include file="customerMenu.jsp" %>
<div class="container">
<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>
		<h1>Hello ${user.firstName} ${user.lastName}</h1>
		<h2>Pending Requests</h2>

		<table class="table table-striped">
			<thead>
				<tr>
				<th>Transaction ID</th>
					<th>Payer ID</th>
					<th>Receiver ID</th>
					<th>Amount</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="transaction" items="${transactions}">
				<tr>
					<td>${transaction.transactionId}</td>
					<td>${transaction.payerId}</td>
					<td>${transaction.receiverId}</td>
					<td>${transaction.transactionAmount}</td>
					<td>

						<spring:url value="/customer/approve-request/${transaction.transactionId}" var="senderApprovalUrl" />
						<spring:url value="/customer/decline-request/${transaction.transactionId}" var="senderDeclineUrl" />
						<button class="btn btn-info" onclick="location.href='${senderApprovalUrl}'">Approve</button>
						<button class="btn btn-info" onclick="location.href='${senderDeclineUrl}'">Decline</button>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>