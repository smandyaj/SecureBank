<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Search Customer</title>
  <%@ include file="employeemenu.jsp" %>
  </head>

  <body role="document">

    <div class="container container-main" role="main">

      <div class="row">

 <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>Employee - Customer Search</h1>
          </div>

          <h2>Search By:</h2>
		  <br>

          <div id="internalUser">
          <form:form method="POST" modelAttribute="externalUser" action="customer-transaction" >
          <c:if test="${!empty successMsg}">
					<div class="alert alert-success">						
						${fn:escapeXml(successMsg)}
					</div>
				</c:if>
          	<c:if test="${!empty failureMsg}">
					<div class="alert alert-danger">						
						${fn:escapeXml(failureMsg)}
					</div>
				</c:if>
            <p>
              <label>Customer ID:</label>
              <form:input  path = "customerId" type="text" class="form-control" placeholder="ex: 123456789" maxlength="11" minlength="1" required= "required"></form:input>
            </p>
			<div class="modal-footer" >                
              <button type="submit" class="btn btn-success">Search</button>
            </div>
            <%--Cross site scripting protection --%>
			<spring:htmlEscape defaultHtmlEscape="true" /> 
            
		   </form:form>
          </div>
          
        </div>

      </div>

    </div>