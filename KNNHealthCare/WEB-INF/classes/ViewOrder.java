import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/ViewOrder")

public class ViewOrder extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders...!");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");		
		pw.print("<form name ='ViewOrder' action='ViewOrder' method='get'>");
		pw.print("<div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>View Order</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'>");	
		if(request.getParameter("Order")==null)
		{
			pw.print("<br><table align='center'><tr><td><h2 style='font-family:Georgia, Times New Roman, Times, serif;font-size:25px;color:black;'>Enter Order Number &nbsp&nbsp<input style='font-size:25px;border-radius:5px;' name='orderId' type='text'></h2></td></tr>");
			pw.print("<br><tr><td><input type='submit' name='Order' value='ViewOrder' class='btn_order'></td></tr></table>");
		}
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		if(request.getParameter("Order")!=null && request.getParameter("Order").equals("ViewOrder"))
		{
			if (request.getParameter("orderId") != null && request.getParameter("orderId") != "" )
			{	
				int orderId=Integer.parseInt(request.getParameter("orderId"));
				pw.print("<input type='hidden' name='orderId' value='"+orderId+"'>");
				try
				{
					orderPayments=MySqlDataStoreUtilities.selectOrder();
				}
				catch(Exception e)
				{}
				int size=0;
				if(orderPayments.get(orderId)!=null)
				{
				for(OrderPayment od:orderPayments.get(orderId))	
				if(od.getUserName().equals(username))
				size= orderPayments.get(orderId).size();
				}
				if(size>0)
				{	
					pw.print("<br><br><table  class='orderDisplayTable'>");
					pw.print("<tr>");
					pw.print("<td><h4 style='color:brown;'>Select</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Order Id</h4></td>");
					pw.print("<td><h4 style='color:brown;'>User Name</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Product Ordered</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Product Price ($)</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Order Date</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Delivery Date</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Order Status</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Cancel Order</h4></td></tr>");
					for (OrderPayment oi : orderPayments.get(orderId)) 
					{
						String c=oi.getOrderDate();
						String d=oi.getDeliveryDate();
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='orderName' value='"+oi.getOrderName()+"'></td>");
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
						pw.print("</tr>");
					}
					pw.print("</table>");
				}
				else
				{
					pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>You have not placed any order with this order id...!</h3>");
				}
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Please enter valid order number...!</h3>");	
			}
		}
		if(request.getParameter("Order")!=null && request.getParameter("Order").equals("CancelOrder"))
		{
			if(request.getParameter("orderName") != null)
			{
				String orderName=request.getParameter("orderName");
				int orderId=0;
				orderId=Integer.parseInt(request.getParameter("orderId"));
				ArrayList<OrderPayment> ListOrderPayment =new ArrayList<OrderPayment>();
				try
				{		
					orderPayments=MySqlDataStoreUtilities.selectOrder();
				}	
				catch(Exception e)
				{
			
				}
				for (OrderPayment oi : orderPayments.get(orderId)) 
				{
					try
					{
						String o=oi.getDeliveryDate();
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");						
						SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");						
						String sa=sdf.format(date);
						Date date1 = myFormat.parse(sa);
						Date date2 = myFormat.parse(o);
						long diff = date2.getTime() - date1.getTime();
						long days1=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						if(days1>5)
						{
							if(oi.getOrderName().equals(orderName) && oi.getUserName().equals(username))
							{
								MySqlDataStoreUtilities.reduceProductCount(orderName);
								MySqlDataStoreUtilities.deleteOrder(orderId,orderName);								
								ListOrderPayment.add(oi);
								pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Your Order ("+oi.getOrderName()+") is Cancelled...!</h3>");	
							}						
						}
					}
					catch(ParseException p)
					{}
				}
				if(ListOrderPayment.size()==0)
				{
					pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Your Order can not be Cancelled as you can cancel 5 days before the delivery date...!</h3>");
				}
				orderPayments.get(orderId).removeAll(ListOrderPayment);
				if(orderPayments.get(orderId).size()==0)
				{
					orderPayments.remove(orderId);
				}
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Please select any product...!</h3>");
			}	
		}
		pw.print("<br><br></div></form>");		
		utility.printHtml("Footer.html");
	}
}


