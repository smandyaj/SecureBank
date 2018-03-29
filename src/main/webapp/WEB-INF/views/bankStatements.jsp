<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
<title>Customer Home Page</title>
</head>
<body colspan="10">
<%@ include file="customerMenu.jsp"%>
<br>


<div class="page-header">
		<h1>Welcome ${fn:escapeXml(fullname)}</h1>
		<h4>Customer Id: ${fn:escapeXml(customerId) }</h4>
	</div>

	<%-- <h2>Accounts:</h2>

	<table class="table">
		<thead>
			<tr>
				<th>Name</th>
				<th>Balance</th>
				<th>Statement</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty accounts}">
				<tr><td colspan="3" class="center">No Accounts to display. Please
					contact the bank.</td></tr>
			</c:if>
			<c:forEach items="${accounts}" var="account">
				<tr>
					<td>$${fn:escapeXml(account.accountId)}</td>
					<td>$${fn:escapeXml(account.accountBalance)}</td>
					<td><a href="statements">View Statements</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table> --%>
	
  

    <div class="row" colspan="10" >
     <form:form id="statements-form" method="POST" modelAttribute="transaction" action="bankStatements" htmlEscape="true">
        
						<p style="left: 10%;">

						<label>Account:</label> 
						<select class="form-control" name="accountId" id="select-account">
							<option value="">Select an Account</option>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${accounts}" var="account">
								<option id="acc${count}"
									value="${fn:escapeXml(account.accountId)}" ${accNumber == account.accountId? 'selected': ''}>Savings:
									${fn:escapeXml(account.accountId)}</option>

								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach>
						</select>
						  <button type="submit" class="btn btn-success" style="display:inline-block">Submit</button>
						<form:errors path="bankAccounts" cssClass="error"
							element="label" />
						</p>  
						<%--Cross site scripting protection --%>
						<spring:htmlEscape defaultHtmlEscape="true" /> 
    </form:form> 
    </div>

    <c:if test="${!empty statementFailureMsg}">
        <div class="alert alert-danger">                       
            ${fn:escapeXml(statementFailureMsg)}
        </div>
    </c:if>
<p>${fn:escapeXml(accNumber)} </p>
    <table class="table table-striped">
    <thead>
        <tr>
            <th >Transaction Id</th>
            <th >Transaction Type</th>

        </tr>
    </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty accNumber}">
                    <tr>
                        <td colspan="3">Please select an account.</td>
                    </tr> 
                </c:when>
                <c:when test="${empty statements}">
                    <tr>
                        <td colspan="3">Could not find statements for this account.</td>
                    </tr>                
                </c:when>
            </c:choose>
            <c:forEach items="${statements}" var="statement">                
                <tr>
                    <td width="20%">${fn:escapeXml(statement.transactionId)}</td>
                    <td width="20%"> ${statement.transactionType=='1'?'Credit':'Debit'}</td>
<%--                     <td width="20%">${fn:escapeXml(statement.transactionCreateTime)}</td>
                    <td width="20%"> ${fn:escapeXml(statement.transactionAmount)}</td> --%>

                    <td class="center">
                        <form:form action="bankStatements/download" method="post" target="_blank">
                            <input type="hidden" name="accountId" value="${accNumber}"></input>
                            <input type="hidden" name="transactionId" value="${fn:escapeXml(statement.transactionId)}"></input>
                            <input type="hidden" name="transactionType" value="${fn:escapeXml(statement.transactionType)}"></input>
                            <button type="submit" class="btn btn-default" >Download</button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	
</body>
</html>