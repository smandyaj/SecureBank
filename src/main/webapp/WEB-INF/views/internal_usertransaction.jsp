<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
	<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.js"></script>
	<script src="${pageContext.servletContext.contextPath}/assets/js/transaction.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Internal User - Transaction</title>
<%@ include file="employeemenu.jsp" %>
</head>
<body role="document">

    <div class="container container-main" role="main">
      <div class="row">
        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>Internal User</h1>
          </div>

          <h2>Add Transaction</h2>

	<form:form id="fund-transfer" action="addTransactionSuccess" method="POST" modelAttribute="transaction" htmlEscape="true">
		<div class="modal-body">
			<c:if test="${!empty successMsg}">
				<div class="alert alert-success">						
					${fn:escapeXml(successMsg)}
				</div>
			</c:if>
			<c:if test="${!empty failureMsg}">
				<div class="alert alert-danger">						
					${fn:escapeXml(failureMsg)}
				</div>
			</c:if>
			<p>
				<label>Debit Account:</label> <select class="form-control"
					name="senderAccNumber" id="select-account">
					<option value="">Select an Account</option>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${accounts}" var="account">
						<option id="acc${count}" value="${fn:escapeXml(account.accountId)}">Savings:
						${fn:escapeXml(account.accountId)}</option>

						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</select>
				<form:errors path="senderAccNumber" cssClass="error"
					element="label" />
			</p>
			<hr>
			<div>
				<label>Current Balance:</label>
				<div id="current-balance">
					<p id="please-select-account">
						<i> Please select an account above </i>
					</p>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${accounts}" var="account">
						<p class="hide" id="acc${count}bal">$${fn:escapeXml(account.accountBalance)}</p>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</div>
			</div>
			<hr>
			<div>
				<label>Transfer Type:</label><br> <span
					style="margin-right: 30px;"> <input type="radio" name="type"
					value="Internal" class="internal-transfer transfer-option-btn">
					Internal
				</span> <span><input type="radio" name="type" value="External"
					class="external-transfer transfer-option-btn"> External</span>
				<div class="type-error">
					<form:errors path="type" cssClass="error" element="label" />
				</div>
			</div>
			<div class="transfer-account-details">
				<div id="internal-transfer" class="hidden">
					<select class="form-control" name="receiverAccNumber"
						id="select-receiver-account">
						<option value="">Select a credit account</option>
						<c:set var="count" value="0" scope="page" />
						<c:forEach items="${accounts}" var="account">
							<option id="acc${count}credit" value="${fn:escapeXml(account.accountId)}">Savings:
								${fn:escapeXml(account.accountId)}</option>

							<c:set var="count" value="${count + 1}" scope="page" />
						</c:forEach>
					</select>
					<form:errors path="receiverAccNumber" cssClass="error"
						element="label" />
				</div>

				<div id="external-transfer" class="hidden">
					<p>
						<label>Beneficiery Account Number:</label> <input type="text"
							class="form-control" name="receiverAccNumberExternal"
							placeholder="eg: 1234">
					</p>
				</div>
				<hr>
			<p>
				<label>Amount to be Transferred:</label>
				<input name="amount" type="text" class="form-control" placeholder="e.g. 10.50">
				<form:errors path="amount" cssClass="error" element="label"/>
			</p>
			</div>
			<hr>
		
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-success">Transfer Funds</button>
		</div>
		<%--Cross site scripting protection --%>
			<spring:htmlEscape defaultHtmlEscape="true" /> 
		
	</form:form>
</html>