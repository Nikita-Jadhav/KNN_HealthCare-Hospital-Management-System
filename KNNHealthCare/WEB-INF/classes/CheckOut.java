import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
	    try
        {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request,pw);
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to add items to cart...!");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession();
			String userType = session.getAttribute("usertype").toString();		
			String userName = session.getAttribute("username").toString();
			//String finalDiscountedTotal = request.getParameter("finalDiscountedTotal");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			double orginalTotal= Double.parseDouble(request.getParameter("subTotal"));
			double totalDiscount = Double.parseDouble(request.getParameter("orderFinalDiscount"));
			double totalRebate=Double.parseDouble(request.getParameter("orderFinalRebate"));
			double finalDiscountedTotal = orginalTotal - (totalDiscount + totalRebate);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Place your order</a></h2></div>");
		pw.print("<br>");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
		pw.print("<div id='entry'>");
		pw.print("<h4 style='margin-left:15px;font-size:19px;color:Black;'>By placing your order, you agree to KNN HealthCare's<a href='#' onclick='showPrivacyNoticeFunction()' style='font-size:19px;'> privacy notice</a> and <a href='#' onclick='showConditionNoticeFunction()' style='font-size:19px;'> conditions of use</a>.</h4>");
		pw.print("<br><h3 style='margin-top:-2px;margin-left:15px;font-size:30px;color:Black;font-weight:bold;'>Order Summary</h3>");
		pw.print("<br><h4 style='margin-top:-10px;margin-left:15px;font-size:21px;color:Black;'>Items:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$"+orginalTotal+"</h4>");
		pw.print("<br><h4 style='margin-top:-8px;margin-left:15px;font-size:21px;color:Black;'>Discount:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$"+totalDiscount+"</h4>");
		pw.print("<br><h4 style='margin-top:-8px;margin-left:15px;font-size:21px;color:Black;'>Rebate:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$"+totalRebate+"</h4>");
		pw.print("<br><h4 style='margin-top:-8px;margin-left:15px;margin-right:50px;font-size:21px;color:Black;'>Estimated Tax to be collected:&nbsp;&nbsp;&nbsp;&nbsp;$0.00</h4>");
		pw.print("<img src='images/site/2.png' alt='' style='height:1px;width:700px;margin-left:-120px;'/>");
		pw.print("<br><h4 style='color:#842121;margin-left:15px;font-size:25px;font-weight:bold;'>Order Total:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$"+finalDiscountedTotal+"</h4>");	
		pw.print("</div><br>");
		pw.print("<div id='entry'>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getDiscount()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getRebate()+"'>");
			//pw.print("<input type='hidden' name='quantity' value='"+oi.getQuantity()+"'><br><br>");
			
		}
		/*Quantity obj1 = Quantity.obj();
		String w=obj1.getQuantity();
		pw.print("<h3 style='margin-left:15px;font-size:30px;color:Black;font-weight:bold;'>"+w+"</h3><br>");*/
		pw.print("<input type='hidden' name='finalDiscountedTotal' value='"+finalDiscountedTotal+"'>");
		pw.print("<h3 style='margin-left:15px;font-size:30px;color:Black;font-weight:bold;'>Payment Information</h3>");
		pw.print("<table>");
		pw.print("<tr><td>");
		pw.print("<h3 style='margin-left:15px;font-size:21px;color:Black;'>Credit Card Number <font color='red'>*</font></h3></td></tr>");
		pw.print("<tr><td><input style='font-size:19px;margin-left:15px;border-radius:5px;width:350px;height:35px;' type='text' name='ccnumber' value='' class='input' required></input></td><td>&nbsp;&nbsp;</td><td><img src='images/site/creditcards.png' alt='' style='height:25px;width:200px;'/></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h3 style='margin-left:15px;font-size:21px;color:Black;'>Cardholder's Name <font color='red'>*</font></h3></td></tr>");
		pw.print("<tr><td><input style='font-size:19px;margin-left:15px;border-radius:5px;width:350px;height:35px;' type='text' name='ccname' value='' class='input' required></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h3 style='margin-left:15px;font-size:21px;color:Black;'>Expiry(mm/yyyy) <font color='red'>*</font></h3></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><h3 style='margin-left:1px;font-size:21px;color:Black;'>CVV <font color='red'>*</font></h3></td></tr>");
		pw.print("<tr><td><input style='font-size:19px;margin-left:15px;border-radius:5px;width:150px;height:35px;' type='text' name='expiry' value='' class='input' required></input></td><td>&nbsp;&nbsp;</td><td><input style='font-size:19px;margin-left:0px;border-radius:5px;width:150px;height:35px;' type='text' name='cvv' value='' class='input' required></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h3 style='margin-left:15px;font-size:21px;color:Black;'>Shipping Address <font color='red'>*</font></h3></td></tr>");
		pw.print("<tr><td><textarea style='font-size:19px;margin-left:15px;border-radius:5px;width:350px;height:100px;' rows='4' cols='50' name='address' placeholder='Enter address here...' required></textarea></td></tr>");
		pw.print("</table>");
		pw.print("<br>");
		pw.print("<input type='image' src='images/site/4.gif' class='btnCheckOut' name='BySubmit'></input>");
		pw.print("<br><br>");
		/*pw.print("<input type='image' src='images/site/submit.gif' alt='Submit' style='margin-left:15px;height:55px;width:150px;' class='btn_submit' name='BySubmit' value=''></input>");	*/
		pw.print("</form></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}		
}
