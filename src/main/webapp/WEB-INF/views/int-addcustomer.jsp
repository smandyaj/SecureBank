<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous" />
<!DOCTYPE html>
<html lang="en">

<%@ include file="employeemenu.jsp"%>

<div class="container">

	<c:choose>
		<c:when test="${customer eq 'new'}">
			<h1>Add Customer/Merchant</h1>
		</c:when>
		<c:otherwise>
			<h1>Update customer ${customer}</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/employee/customer-add-modify"
		var="employeeActionUrl" />

	<form:form class="form-horizontal" method="post"
		modelAttribute="customerForm" action="${employeeActionUrl}"
		htmlEscape="true">

		<form:hidden path="customerId" />

		<spring:bind path="firstName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">First Name</label>
				<div class="col-sm-10">
					<form:input path="firstName" type="text" class="form-control "
						id="firstName" placeholder="FirstName" />
					<form:errors path="firstName" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="lastName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Last Name</label>
				<div class="col-sm-10">
					<form:input path="lastName" type="text" class="form-control "
						id="lastName" placeholder="LastName" />
					<form:errors path="lastName" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<!--  IMPORTANT -->
		<spring:bind path="customerType">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">User Type</label>
			<div class="col-sm-10">
				<form:select path="customerType" items="${customerTypes}" />
				<form:errors path="customerType" class="control-label" />
			</div>
		</div>
		</spring:bind>


		<spring:bind path="emailId">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<form:input path="emailId" class="form-control" id="emailId"
						placeholder="Email" />
					<form:errors path="emailId" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<!--  need to add confirm password -->
		<spring:bind path="customerAddress">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Address</label>
				<div class="col-sm-10">
					<form:textarea path="customerAddress" rows="5" class="form-control"
						id="address" placeholder="address" />
					<form:errors path="customerAddress" class="control-label" />
				</div>
			</div>
		</spring:bind>


		<spring:bind path="phone">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Mobile</label>
				<div class="col-sm-10">
					<form:input path="phone" class="form-control" id="phone"
						placeholder="Mobile" />
					<form:errors path="phone" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="userName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">User Name</label>
				<div class="col-sm-10">
					<form:input path="userName" class="form-control" id="userName"
						placeholder="UserName" />
					<form:errors path="userName" class="control-label" />
				</div>
			</div>
		</spring:bind>

	<!--  IMPORTANT -->
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Select Account Type</label>
			<div class="col-sm-10">
				<form:select path="accountType" items="${accountTypes}" />
				<form:errors path="accountType" class="control-label" />
			</div>
		</div>


		<spring:bind path="passwordHash">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<form:password path="passwordHash" class="form-control"
						id="passwordHash" placeholder="password" />
					<form:errors path="passwordHash" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${customer  eq 'new'}">
						<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%--Cross site scripting protection --%>
		<spring:htmlEscape defaultHtmlEscape="true" />

	</form:form>

</div>
</body>
</html>