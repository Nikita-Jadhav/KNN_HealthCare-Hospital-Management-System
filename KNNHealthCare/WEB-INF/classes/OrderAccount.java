import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/OrderAccount")

public class OrderAccount extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayOrderAccount(request, response);
	}
	
	protected void displayOrderAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
         {  
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to view orders...!");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='post'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Patient Account</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry'><br>");
			User user=utility.getUser();			
			HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			try
		    {
				orderPayments=MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet())
			{
				for(OrderPayment od:entry.getValue())	
				if(od.getUserName().equals(user.getName()))
				size= size+1;
			}	
			if(size>0)
			{
				pw.print("<br><br><table  class='orderDisplayTable'>");
				pw.print("<tr><td><h4 style='color:brown;'>Select</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Order Id</h4></td>");
				pw.print("<td><h4 style='color:brown;'>User Name</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Product Ordered</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Product Price ($)</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Order Date</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Delivery Date</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Order Status</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Cancel Order</h4></td></tr>");
				for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet())
				{
					for(OrderPayment oi:entry.getValue())	
					if(oi.getUserName().equals(user.getName())) 
					{
						pw.print("<form method='get' action='ViewOrder'>");
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='orderName' value='"+oi.getOrderName()+"'></td>");							
						String c=oi.getOrderDate();
						String d=oi.getDeliveryDate();
						pw.print("<td>"+oi.getOrderId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getOrderName()+"</td><td>"+oi.getOrderPayablePrice()+"</td><td>"+c+"</td><td>"+d+"</td><td>"+oi.getOrderStatus()+"</td>");						
						String temp=oi.getOrderStatus();
						if(temp.equals("Delivered"))
						{
							pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy_1' disabled></td>");
						}
						else
						{
							pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy_1'></td>");
						}
						pw.print("<td><input type='hidden' name='orderId' value='"+oi.getOrderId()+"'></td>");
						pw.print("</tr>");
						pw.print("</form>");
					}
				}
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>You have no orders placed...!</h3>");
			}	
			pw.print("</table>");		
			pw.print("<br><br></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{}		
	}
}
