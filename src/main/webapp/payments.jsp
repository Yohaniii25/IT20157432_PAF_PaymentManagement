<%@page import="model.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<link rel="stylesheet" href="Views/style.css">



<script type="text/javascript" src="Components/payments.js"></script>
</head>
<body>

	<div class="container mt-3">
		<h2>Payments Management</h2>
		<div class="row">
			<div class="col-sm">
				<form id="formPayment" name="formPayment">

					Account ID: <input id="accountID" name="accountID" type="number"
						class="form-control form-control-sm" min="0"
						placeholder="Enter account id"> <br /> Payment amount: <input
						id="amount" name="amount" type="number"
						class="form-control form-control-sm" min="1"
						placeholder="Enter payment amount"> <br /> Payment
					Method: <input id="payMethod" name="payMethod" type="text"
						class="form-control form-control-sm"
						placeholder="Enter payment method (Only bank or card)"> <br />
					Payment Date: <input id="payDate" name="payDate" type="text"
						class="form-control form-control-sm"
						placeholder="Enter the payment date"> <br /> <input
						id="btnReset" name="btnReset" type="reset" value="Reset"
						class="btn btn-secondary"> <input id="btnSave"
						name="btnSave" type="button" value="Save" class="btn btn-success">
					<input type="hidden" id="hidPayIDSave" name="hidPayIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success my-3"></div>
				<div id="alertError" class="alert alert-danger my-3"></div>
			</div>
			<div class="col-sm container">
				<div id="divPaymentsGrid">
					<%
					Payment payObj = new Payment();
					out.print(payObj.readPayments());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>