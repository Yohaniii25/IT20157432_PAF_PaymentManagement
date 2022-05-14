package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import utils.DBConnect;

public class Payment {

	private DBConnect connection = new DBConnect();

//insert payment method

	public String insertPayment(String accountID, String amount, String payMethod, String payDate) {

		String output = "";

		try {

			Connection con = connection.getConnection();

			if (con == null) {
				return "Error while connecting to database";
			}

			// create a prepared statement
			String query = "INSERT INTO payments (`paymentID`, `accountID`,`amount`, `payMethod`, `payDate` )"
					+ "VALUES (?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, Integer.parseInt(accountID));
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, payMethod);
			preparedStmt.setString(5, payDate);

			// execute statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
			System.err.println(e.getMessage());
		}

		return output;

	}

	// read payment method

	public String readPayments() {

		String output = "";

		try {
			Connection con = connection.getConnection();

			if (con == null) {
				return "Error while connecting to database";
			}

			// prepare the html table to be displayed
			output = "<table border='1' class='container mx-2 text-center table'>" 
					+ "<thead class='thead-dark'>"
					+ "<tr>" 
					+ "<th scope='col'>Payment ID</th>"
					+ "<th scope='col'>Account ID</th>" 
					+ "<th scope='col'>Amount</th>"
					+ "<th scope='col'>Method Of Payment</th>"
					+ "<th scope='col'>Date of Payment</th>" 
					+ "<th scope='col'>Update Payment</th>" 
					+ "<th scope='col'>Delete Payment</th>" 
					+ "</tr>"
					+ "</thead>";

			String query = "SELECT * FROM payments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String paymentID = Integer.toString(rs.getInt("paymentID"));
				String accountID = Integer.toString(rs.getInt("accountID"));
				String amount = Double.toString(rs.getDouble("amount"));
				String payMethod = rs.getString("payMethod");
				String payDate = rs.getString("payDate");

				// add a row into html table
				output += "<tbody>";
				output += "<tr>";
				output += "<th><input id='hidPayIDUpdate' name='hidPayIDUpdate' type='hidden' value='" + paymentID
						+ "'>" + paymentID + "</th>";
				output += "<td>" + accountID + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + payMethod + "</td>";
				output += "<td>" + payDate + "</td>";
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-warning mx-2' data-paymentid='"
						+ paymentID + "'>" + "</td>";
				output += "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger mx-2' data-paymentid='"
						+ paymentID + "'>" + "</td>";
				output += "</tr>";
				output += "</tbody>";

			}

			con.close();

			// completing html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readPayment(String ID) {
		String output = "";

		try {
			Connection con = connection.getConnection();

			if (con == null) {
				return "Error while connecting to database";
			}

			// prepare the html table to be displayed
			output = "<table border=\"1\">" + "		<tr>" + "			<th>Payment ID</th>"
					+ "			<th>Account ID</th>" + "			<th>Amount</th>"
					+ "			<th>Method Of Payment</th>" + "			<th>Date of Payment</th>" + "		</tr>";

			String query = "SELECT * FROM payments WHERE paymentID=" + ID;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String paymentID = Integer.toString(rs.getInt("paymentID"));
				String accountID = Integer.toString(rs.getInt("accountID"));
				String amount = Double.toString(rs.getDouble("amount"));
				String payMethod = rs.getString("payMethod");
				String payDate = rs.getString("payDate");

				// add a row into html table
				output += "<tr>";
				output += "<td>" + paymentID + "</td>";
				output += "<td>" + accountID + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + payMethod + "</td>";
				output += "<td>" + payDate + "</td>";
				output += "</tr>";

			}

			con.close();

			// completing html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePayment(String paymentID) {
		String output = "";

		try {

			Connection con = connection.getConnection();
			if (con == null) {
				return "Error while connecting to database for deleting";
			}

			// create a prepared statement
			String query = "DELETE FROM payments WHERE paymentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding value
			preparedStmt.setInt(1, Integer.parseInt(paymentID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updatePayment(String ID, String accountID, String payMethod, String payDate) {

		String output = "";

		try {

			Connection con = connection.getConnection();
			if (con == null) {

				return "Error connecting to database";
			}

			String query = "UPDATE payments SET accountID=?, payMethod=?, payDate=? where paymentID=? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(accountID));
			preparedStmt.setString(2, payMethod);
			preparedStmt.setString(3, payDate);
			preparedStmt.setInt(4, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();

			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
			System.err.println(e.getMessage());

		}

		return output;
	}
}
