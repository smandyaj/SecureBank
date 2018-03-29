<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
#adminMenu > ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #111;
}

#adminMenu > ul > li {
	float: left;
}

#adminMenu > ul > li > a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

/* Change the link color to #111 (black) on hover */
#adminMenu > ul > li > a:hover {
	background-color: #111;
}
</style>
</head>
<body>
	<div id="adminMenu">
		<ul id="adminList">
			<li><a href=#>Home</a></li>
			<li><a href=${pageContext.request.contextPath}/admin/employee-list>PII And Employee Management</a></li>
			<li><a href=${pageContext.request.contextPath}/admin/requests-pending>Requests Pending</a></li>
			<li><a href=${pageContext.request.contextPath}/admin/systemlogs>System Log</a></li>
			<li><a href=${pageContext.request.contextPath}/admin/employee-add>Add Users</a></li>
			<li><a href=${pageContext.request.contextPath}/logout/>Logout</a></li>
		</ul>
	</div>
</body>
</html>