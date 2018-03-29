<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
             <li><a href="${pageContext.servletContext.contextPath}/employee/home">Home</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/employee/customer-transaction">Add Transaction</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/employee/pending-transactions">Pending Transactions</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/employee/pending-profile">Pending Profile</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/employee/customer-list">View-Edit Customers</a></li>
            <sec:authorize access="hasRole('MANAGER')">
            <li><a href="${pageContext.servletContext.contextPath}/employee/employee-add">Add Internal Users</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/employee/employee-list">View-Edit Internal Users</a></li>
            </sec:authorize>
            <li><a href="${pageContext.servletContext.contextPath}/employee/customer-add">Add Users</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/employee/profile">Profile</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
		</ul>
	</div>
</body>
</html>