<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<t:page>

	<div class="page-header">
		<h1>Account Statement: ${statementName}</h1>
		<ul class="no-bullets">
			<li><strong>${user.firstName} 
					${user.lastName}</strong></li>
			<li>${user.customerAddress}</li>
			<li>${user.state} ${user.zipCode}</li>
			<li><strong>Balance:</strong> $${account.accountBalance}</li>
			<li><strong>Account Number:</strong> ${account.accountId}</li>
		</ul>
	</div>

	<h2>Transactions:</h2>

	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Transaction ID</th>
				<th>Transaction Date</th>
				<th>Debit/Credit</th>
				<th>Transfer Amount</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transactions}" var="transaction">
				<tr>
					<td>${transaction.transactionId}</td>
					<td>
						<joda:format var="updatedAt"
							value="${transaction.transactionCreateTime}" pattern="dd MMM, yyyy HH:mm"
							style="F-" dateTimeZone="America/Phoenix" />
						${transactionCreateTime}
					</td>
					<td>${transaction.transactionType=='1'?'Credit':'Debit'}</td>
					<td>$${transaction.transactionAmount}</td>
					<c:set var="total" scope="page" value="${transaction.transactionAmount}"/>
				</tr>
			</c:forEach>

		</tbody>
	</table>

</t:page>