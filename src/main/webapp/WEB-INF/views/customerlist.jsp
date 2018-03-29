<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
function post(path, params, method) {
	method = method || "post"; 
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);
	for ( var key in params) {
		if (params.hasOwnProperty(key)) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);
			form.appendChild(hiddenField);
		}
	}
	document.body.appendChild(form);
	form.submit();
}
</script>
</head>
<%@ include file="employeemenu.jsp"%>

<body>

	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>All Employees</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>FirstName</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="customer" items="${customers}">
				<tr>
					<td>${customer.customerId}</td>
					<td>${customer.firstName}</td>
					<td>${customer.emailId}</td>
					<%-- <spring:url value="/employee/${employee.employeeId}" var="userUrl" /> --%>
					<spring:url value="/employee/customer-delete/${customer.customerId}" var="deleteUrl" />
					<spring:url value="/employee/customer-update/${customer.customerId}" var="updateUrl" />
					<td>
					<%-- <button class="btn btn-info" onclick="location.href='${userUrl}'">Query</button> --%>
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Update</button>
					<button class="btn btn-danger"
						onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>