<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>

<%@ include file="customerMenu.jsp"%>
	<div class="page-header">
		<h1>${title} </h1>
	</div>

	<div id="add-withdraw">
		<form:form id="credit-debit-form" method="POST" modelAttribute="transaction" action="requestMoneySuccess" htmlEscape="true">
			<div>
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
				<div class="modal-header">
					<h4 class="modal-title">Request Money from your Friends:</h4>
				</div>
				<div class="modal-body">

					<p>
						<label>Sender Account Number:</label>
						<input name="senderAccNumber" type="text" class="form-control" placeholder="e.g. 5">
						<form:errors path="senderAccNumber" cssClass="error" element="label"/>
					</p>
					<p>
						<label>Request Amount:</label>
						<input name="amount" type="text" class="form-control" placeholder="e.g. 10.50">
						<form:errors path="amount" cssClass="error" element="label"/>
					</p>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
					<button type="submit" class="btn btn-success">Request</button>
				</div>
			</div>
			<%--Cross site scripting protection --%>
			<spring:htmlEscape defaultHtmlEscape="true" /> 
			

		</form:form>
		<!-- /form -->
	</div>
	<!-- /#add-withdraw -->