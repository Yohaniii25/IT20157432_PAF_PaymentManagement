package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Payment;

/**
 * Servlet implementation class PaysAPI
 */
@WebServlet("/PaysAPI")
public class PaysAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Payment payObj = new Payment();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaysAPI() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String output = payObj.insertPayment(request.getParameter("accountID"), 
				request.getParameter("amount"),
				request.getParameter("payMethod"),
				request.getParameter("payDate"));
		
		response.getWriter().write(output);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = payObj.updatePayment(paras.get("hidPayIDSave").toString(),
										   paras.get("accountID").toString(),
										   paras.get("payMethod").toString(),
										   paras.get("payDate").toString());
			
		response.getWriter().write(output);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = payObj.deletePayment(paras.get("paymentID").toString());
		response.getWriter().write(output);
		
	}
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			for (String param : params)
			{ 
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		}
		
		catch (Exception e)
		{
		}
		
		return map;
	}

}
