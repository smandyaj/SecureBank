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
             <li><a href=${pageContext.request.contextPath}/customer/home>Home</a></li>
             <li><a href=${pageContext.servletContext.contextPath}/customer/customer-transaction>Add Transaction</a></li>
             <li><a href=${pageContext.request.contextPath}/customer/request-money>Request Money</a></li>
            <li><a href=${pageContext.request.contextPath}/customer/credit-debit>Credit-Debit</a></li>
            <li><a href=${pageContext.request.contextPath}/customer/credit-home>Credit Card</a></li>
			<li><a href=${pageContext.request.contextPath}/customer/requests-pending>Requests Pending</a></li>
			<li><a href=${pageContext.servletContext.contextPath}/customer/profile>Profile</a></li>
            <li><a href=${pageContext.servletContext.contextPath}/logout>Logout</a></li>
		</ul>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/assets/js/common.js"></script>
</body>
</html>