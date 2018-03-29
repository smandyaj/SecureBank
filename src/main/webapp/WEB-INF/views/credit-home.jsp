<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
</script>
<html>

<body>
<%@ include file="customerMenu.jsp"%>


	
 
<h2> Account Summary  </h2>
	<table border="1">

<tr>
<th>accountId</th>
<th>Current balance</th>
<th>Detailes</th>
</tr>
  		
 	<c:forEach var="account" items="${accounts}">
   <tr>
   <td>${account.accountId}</td>
    <td>${account.accountBalance}</td> 
      <td><a href=${pageContext.request.contextPath}/customer/creditcard/${account.accountId} >Detailes</a>

            </td> 
    </tr>
</c:forEach>
 	
 	
 	
 	
 	
</table>








</body>

</html>


