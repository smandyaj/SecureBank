<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>

<!--  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>-->
<!DOCTYPE html>
<html lang="en">

<%@ include file="customerMenu.jsp"%>

<div class="container">
			<h1>Make a Payment</h1>
			<spring:url value="/customer/creditcard/${accountId}/make-payment" var="actionUrl" />
	<br />

	<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

	<form:form class="form-horizontal" method="post"
		modelAttribute="paymentForm" action="make-payment" htmlEscape="true">
		
		<form:hidden path="transactionId" />

		<spring:bind path="senderAccNumber">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Pay Account Number</label>
				<div class="col-sm-10">
					<form:input path="senderAccNumber" class="form-control" id="senderAccNumber"
						placeholder="SenderAccNumber" />
					<form:errors path="senderAccNumber" class="control-label" />
				</div>
			</div>
		</spring:bind>


		<spring:bind path="transactionAmount">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Amount</label>
				<div class="col-sm-10">
					<form:input path="transactionAmount" class="form-control"
						id="transactionAmount" placeholder="TransactionAmount" />
					<form:errors path="transactionAmount" class="control-label" />
				</div>
			</div>
		</spring:bind>


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn-lg btn-primary pull-right">Pay</button>
			</div>
		</div>
	</form:form>

</div>
</body>
</html>