$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid-------------------------
	var type = ($("#hidPayIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
		{
			url: "PaysAPI",
			type: type,
			data: decodeURIComponent($("#formPayment").serialize()),
			dataType: "text",
			complete: function(response, status) {
				onPaymentSaveComplete(response.responseText, status);
			}
		});
});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		}
		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}
	else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	}
	else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidPayIDSave").val("");
	$("#formPayment")[0].reset();
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidPayIDSave").val($(this).data("paymentid"));
	$("#accountID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(1)').text());
	$("#payMethod").val($(this).closest("tr").find('td:eq(2)').text());
	$("#payDate").val(decodeURIComponent($(this).closest("tr").find('td:eq(3)').text()));
});



$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "PaysAPI",
			type: "DELETE",
			data: "paymentID=" + $(this).data("paymentid"),
			dataType: "text",
			complete: function(response, status) {
				onPaymentDeleteComplete(response.responseText, status);
			}
		});
});


function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		}
		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}
	else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	}
	else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


// CLIENT-MODEL================================================================
function validatePaymentForm() {
	// accountID
	if ($("#accountID").val().trim() == "") {
		return "Insert account id!";
	}

	// amount
	if ($("#amount").val().trim() == "") {
		return "Insert amount!";
	}
	
	

	// payMethod
	if ($("#payMethod").val().trim() == "") {
		return "Insert payment method!";
	}
	
	// payMethod
	if (! ($("#payMethod").val().trim() == "bank" || $("#payMethod").val().trim() == "card" ) ){
		return "Invalid payment method (Only bank and card is valid.)!";
	}
	
	// validating payDate
	if ($("#payDate").val().trim() == "") {
		return "Insert date of payement!";
	}
	
	// is numerical value
	var tmpPrice = $("#amount").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Payment amount!";
	}

	// convert to decimal price
	$("#amount").val(parseFloat(tmpPrice).toFixed(2));

	return true;
}




