<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<t:auth>



	<form class="form-signin" method="post"
		action="${pageContext.servletContext.contextPath}/login" htmlEscape="true">
		<div>
			<c:if test="${!empty successMsg}">
				<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
			</c:if>

			<c:if test="${!empty error}">
				<div class="alert alert-danger">${fn:escapeXml(error)}</div>
			</c:if>

			<c:if test="${!empty failureMsg}">
				<div class="alert alert-danger">${fn:escapeXml(failureMsg)}</div>
			</c:if>

			<h3>Secure Bank Login</h3>
			<h2 class="form-signin-heading">Please Login</h2>

			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<div class="form-errors">
					Your login attempt was not successful due to:
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					.
				</div>
			</c:if>


			<label for="username" class="sr-only">User Name</label> <input
				name="username" type="text" id="inputEmail" class="form-control"
				placeholder="User Name" required="" autofocus=""
				autocomplete="off"> <label for="inputPassword"
				class="sr-only">Password</label> <input name="password"
				type="password" id="inputPassword" class="form-control"
				placeholder="Password" required="" autocomplete="off">
			<!-- <div class="g-recaptcha"
			data-sitekey="6LcQrwwTAAAAAP1rFCMhODCuHWbbkgC9mJ2Qm6gz"></div> -->
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>

			<p class="center">
				<a href="forgotpass">Forgot Password?</a>
			</p>
		</div>
		<%--Cross site scripting protection --%>
			<spring:htmlEscape defaultHtmlEscape="true" /> 
		
	</form>
	<%-- <div id="loginBox">
		<form name="loginForm" action="<c:url value='/customerRedirect'/>"
			method='POST'>
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
					<!--  use keyboard for security -->
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="Submit" /></td>
				</tr>
				<tr>
			</table>

			<span><a href="newUser"><button>Sign up</button></a></span> <span><a
				href="forgotPassword"><button>Forgot Password</button></a></span>

		</form>

	</div> --%>

	<div id="virtualKeyboard"></div>


</t:auth>
