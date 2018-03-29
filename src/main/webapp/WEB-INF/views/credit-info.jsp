<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
</script>
<html>
<body>
<%@ include file="customerMenu.jsp"%>
<p></p>
<table border="1">

<tr>
<th>Account Id: </th>
<th>${account.accountId}</th>
</tr>

<tr>
<th>Current Balance: </th>
<th>${account.accountBalance} </th>
</tr>

<tr>
<th>Interest: </th>
<th>${interest}</th>
</tr>

<tr>
<th>Late Fee: </th>
<th>${latefee}</th>
</tr>
</table>
<p></p>
 
<div><a href=${pageContext.request.contextPath}/customer/creditcard/${account.accountId}/payment>Make a payment</a></div>

		
<div>Transaction History:</div>


		<!--  <table class="table table-striped">-->
		<table border="1">
			<thead>
				<tr>
					<th>Sender</th>
					<th>Receiver</th>
					<th>Amount</th>
					<th>Status</th>
					<th>Type</th>
					<th>Time</th>
				</tr>
			</thead>

			<c:forEach var="tran" items="${transactions}">
				<tr>
					<td>${tran.senderAccNumber}</td>
					<td>${tran.receiverAccNumber}</td>
					<td>${tran.transactionAmount}</td>
					<td>${tran.status}</td>
					<td>${tran.transactionType}</td>
					<td>${tran.transactionCreateTime}</td>
				</tr>
			</c:forEach>
		</table>


  <p></p>
 	
 	
 	
 	


		
 
</body>

</html>