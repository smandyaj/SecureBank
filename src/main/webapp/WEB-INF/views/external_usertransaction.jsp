<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
<script
	src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.js"></script>
<!-- <script src="${pageContext.servletContext.contextPath}/assets/js/transaction.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>External User - Transaction</title>
<%@ include file="customerMenu.jsp"%>
<script>
	$(function() {

		if ($('#virtualKeyboard').length) {
			jsKeyboard.init("virtualKeyboard");

			//first input focus
			var $firstInput = $(':input').first().focus();
			jsKeyboard.currentElement = $firstInput;
			jsKeyboard.currentElementCursorPosition = 0;

		}

		// Open Add Withdraw Funds modal when .add-withdraw class is clicked
		$('.add-withdraw').click(function() {
			$('#add-withdraw').modal('show');
			return false;
		});

		// Transfer type (internal or external)
		$('.transfer-option-btn').click(function() {

			$('.transfer-option-btn').removeClass('active');

			if ($(this).hasClass('internal-transfer')) {
				$('#internal-transfer').removeClass('hidden');
				$('#external-transfer').addClass('hidden');
			} else {
				$('#external-transfer').removeClass('hidden');
				$('#internal-transfer').addClass('hidden');
			}

			$(this).addClass('active');

		});

		// get balance based on the acount selected
		$('#select-account').change(
				function() {
					var id = "#" + $(this).find('option:selected').prop('id')
							+ "bal";
					var creditId = "#"
							+ $(this).find('option:selected').prop('id')
							+ "credit";
					$('#current-balance').find('p').addClass('hide');
					if (id === '#bal') {
						$('#please-select-account').removeClass('hide');
					} else {
						$(id).removeClass('hide');
					}
					if ($('#select-receiver-account').length) {
						$('#select-receiver-account').find('option')
								.removeClass('hide').parent().prop(
										'selectedIndex', 0);
						$(creditId).addClass("hide");
					}
					return false;
				});

		$("#textPhone").hide();
		$("#textEmail").hide();
		$("#textAccNum").hide();

		$("#radioPhone").click(function() {
			$("#textAccNum").hide();
			$("#textAccNum").val('');

			$("#textEmail").hide();
			$("#textEmail").val('');

			$("#textPhone").show();
		});
		$("#radioEmail").click(function() {
			$("#textPhone").hide();
			$("#textPhone").val('');

			$("#textAccNum").hide();
			$("#textAccNum").val('');

			$("#textEmail").show();
		});
		$("#radioAcc").click(function() {
			$("#textEmail").hide();
			$("#textEmail").val('');
			$("#textPhone").hide();
			$("#textPhone").val('');

			$("#textAccNum").show();
		});

	});
</script>
</head>
<body role="document">

	<div class="container container-main" role="main">
		<div class="row">
			<div class="col-sm-9 col-md-10 main">

				<div class="page-header">
					<h1>Welcome ${fn:escapeXml(fullname)}</h1>
					<h4>Customer Id: ${fn:escapeXml(customerId) }</h4>
				</div>

				<h2>Make Payment</h2>

				<form:form id="fund-transfer" action="addTransactionSuccess"
					method="POST" modelAttribute="transaction" htmlEscape="true">
					<div class="modal-body">
						<c:if test="${!empty successMsg}">
							<div class="alert alert-success">
								${fn:escapeXml(successMsg)}</div>
						</c:if>
						<c:if test="${!empty failureMsg}">
							<div class="alert alert-danger">
								${fn:escapeXml(failureMsg)}</div>
						</c:if>
						<p>

						<label>Debit Account:</label> <select class="form-control"
							name="senderAccNumber" id="select-account">
							<option value="">Select an Account</option>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${accounts}" var="account">
								<option id="acc${count}"
									value="${fn:escapeXml(account.accountId)}">Savings:
									${fn:escapeXml(account.accountId)}</option>

								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach>
						</select>
						<form:errors path="senderAccNumber" cssClass="error"
							element="label" />
						</p>
						<hr>
						<div>
							<label>Current Balance:</label>
							<div id="current-balance">
								<p id="please-select-account">
									<i> Please select an account above </i>
								</p>
								<c:set var="count" value="0" scope="page" />
								<c:forEach items="${accounts}" var="account">
									<p class="hide" id="acc${count}bal">$${fn:escapeXml(account.accountBalance)}</p>
									<c:set var="count" value="${count + 1}" scope="page" />
								</c:forEach>
							</div>
						</div>
						<hr>
						<div>
							<label>Transfer Type:</label><br> <span
								style="margin-right: 30px;"> <input type="radio"
								 name="type" value="Internal"
								class="internal-transfer transfer-option-btn"> Internal
							</span> <span><input type="radio" name="type" value="External"
								class="external-transfer transfer-option-btn"> External</span>
							<div class="type-error">
								<form:errors path="type" cssClass="error" element="label" />
							</div>
						</div>
						<div class="transfer-account-details">
							<div id="internal-transfer" class="hidden">
								<select class="form-control" name="receiverAccNumber"
									id="select-receiver-account">
									<option value="">Select a credit account</option>
									<c:set var="count" value="0" scope="page" />
									<c:forEach items="${accounts}" var="account">
										<option id="acc${count}credit"
											value="${fn:escapeXml(account.accountId)}">Savings:
											${fn:escapeXml(account.accountId)}</option>

										<c:set var="count" value="${count + 1}" scope="page" />
									</c:forEach>
								</select>
								<form:errors path="receiverAccNumber" cssClass="error"
									element="label" />
							</div>

							<div id="external-transfer" class="hidden">
								<span><input type="radio" name="modes"
									value="receiverPhoneNumber" id="radioPhone"
									class="external-transfer transfer-option-btn"> <label>Beneficiary
										Phone Number:</label> 
										<input type="text" class="form-control"
									id="textPhone" name="receiverPhoneNumber"
									placeholder="eg: 480***27**"></span> <br> <span> <input
									type="radio" name="modes" id="radioEmail"
									value="receiverEmailId"
									class="external-transfer transfer-option-btn"> <label>Beneficiary
										Email Id:</label> 
										<input type="text" class="form-control"
									id="textEmail" name="receiverEmailId"
									placeholder="eg: abc@xyz.com"></span> <br> <span><input
									type="radio" name="modes" value="receiverAccNumberExt"
									id="radioAcc" class="external-transfer transfer-option-btn"
									><label>Beneficiary Account
										Number:</label> 
										<input type="text" class="form-control"
									id="textAccNum" name="receiverAccNumberExt"
									placeholder="eg: 1234567898764567"></span>
							</div>
							<hr>
							<p>
								<label>Amount to be Transferred:</label> <input name="amount"
									type="text" class="form-control" placeholder="e.g. 10.50">
								<form:errors path="amount" cssClass="error" element="label" />
							</p>
						</div>
						<hr>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-success">Transfer Money</button>
					</div>
					<%--Cross site scripting protection --%>
			<spring:htmlEscape defaultHtmlEscape="true" /> 
					
				</form:form>
</html>
</html>