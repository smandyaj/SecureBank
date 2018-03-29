<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
<!DOCTYPE html>
<html lang="en">

<%@ include file="employeemenu.jsp"%>

<div class="container">
			<h1>Update Employee ${user}</h1>
			<spring:url value="/employee/modify-profile" var="actionUrl" />
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
		modelAttribute="employeeForm" action="${actionUrl}" htmlEscape="true">

		<form:hidden path="employeeId" />

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

		<spring:bind path="address">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Address</label>
				<div class="col-sm-10">
					<form:textarea path="address" rows="5" class="form-control"
						id="address" placeholder="address" />
					<form:errors path="address" class="control-label" />
				</div>
			</div>
		</spring:bind>


		<spring:bind path="phoneNumber">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Mobile</label>
				<div class="col-sm-10">
					<form:input path="phoneNumber" class="form-control"
						id="phoneNumber" placeholder="Mobile" />
					<form:errors path="phoneNumber" class="control-label" />
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

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
			</div>
		</div>
		<%--Cross site scripting protection --%>
			<spring:htmlEscape defaultHtmlEscape="true" /> 
		
	</form:form>

</div>
</body>
</html>