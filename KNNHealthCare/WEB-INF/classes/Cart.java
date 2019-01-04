import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
//my
@WebServlet("/Cart")

public class Cart extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String maker = request.getParameter("maker");
		String access = request.getParameter("access");
		System.out.print("name" + name + "type" + type + "maker" + maker + "accesee" + access);		
		utility.storeProduct(name, type, maker, access);
		displayCart(request, response);
	}

/* displayCart Function shows the products that users has bought, these products will be displayed with Total Amount.*/
	protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		Carousel carousel = new Carousel();
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<body onload='demo1()'><div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Cart("+utility.CartCount()+")</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'>");
		pw.print("<script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>");
		pw.print("<form name ='form' action='CheckOut' method='post'>");
		if(utility.CartCount()>0)
		{
			pw.print("<table id='tbUser' class='gridtable' style='width:1525px;'>");
			int i = 1;
			double total = 0;
			double totalDiscount= 0;
			double totalRebate = 0;
			double totalDiscountedTotal = 0;
			double discountedTotal = 0.0;
			int j =1;
			String t="";
			pw.print("<br><td><h4 style='color:brown;'>Sr.Number</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Medicine/Accessory Name</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Medicine/Accessory Price($)</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Discount($)</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Manufacturer Rebate($)</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Quantity</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Total Price($)</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Total Discount ($)</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Total Rebate ($)</h4></td>");
			pw.print("<td><h4 style='color:brown;'>Total Discounted Price($)</h4></td>");
			//pw.print("<td><h4 style='color:brown;'>Delete Order</h4></td></tr>");
			pw.print("<script language='text/javascript' src='javascript.js'></script>");
			for (OrderItem oi : utility.getCustomerOrders()) 
			{
				total = total+(oi.getPrice());
				totalDiscount = totalDiscount+(oi.getDiscount());
				totalRebate = totalRebate+(oi.getRebate());
				totalDiscountedTotal = total - (totalDiscount + totalRebate);
				discountedTotal = oi.getPrice() - (oi.getDiscount() + oi.getRebate());
				pw.print("<tr>");		
				pw.print("<td>"+i+".</td><td><input style='color:black;border:none;background-color:white;' type='text' class='proName' id='proName' name='proName' readonly value='"+oi.getName()+"'></td><td>"+oi.getPrice()+"</td><td>"+oi.getDiscount()+"</td><td>"+oi.getRebate()+"</td>");
				//this.form.submit()
				pw.print("<input type='hidden' class='orderName' id='orderName' name='orderName' value='"+oi.getName()+"'>");
				pw.print("<input type='hidden' class ='total' id='total' name='total' value='"+oi.getPrice()+"'>");
				//originalOrderPrice
				pw.print("<input type='hidden' class ='orderDiscount' name='orderDiscount' value='"+oi.getDiscount()+"'>");
				pw.print("<input type='hidden' class ='orderRebate' name='orderRebate' value='"+oi.getRebate()+"'>");				
				pw.print("<td><input class='quantity' id='quantity' name='quantity' type='number' style='width:70px;' min='1' max='10' value='1'></td>");
				pw.print("<td><span class='total_price'>"+oi.getPrice()+"</span></td>");
				pw.print("<td><span class='total_discount'>"+oi.getDiscount()+"</span></td>");	
				pw.print("<td><span class='total_rebate'>"+oi.getRebate()+"</span></td>");
				//"<input type='text' id='qT' class='qT' name='qT' value='' style='visibility:hidden;'></input>"
				//c-pw.print("<br><table id='demo4' style='margin-left:200px;border:1px solid black'></table>");
					/*Quantity obj1 = Quantity.obj();
					String w=obj1.getQuantity();
					if(w!=null)
					{
						pw.print("<h2 style='color:black;margin-left:200px;'>"+obj1.getQuantity()+"</h2>");
						pw.print("<h2 style='color:black;margin-left:200px;'>Success in getting 9...!</h2>");
						oi.setQuantity(w);
					}
					else
					{
						pw.print("<h2 style='color:black;margin-left:200px;'>Failed to get...!</h2>");
					
					}*/
				pw.print("<td><span class='total_amount' id='total_amount'>"+discountedTotal+"</span></td>");				
				//pw.print("<td><input type='button' value='Delete Order' class='btnRemove'></input></td>");				
				pw.print("</tr>");
				i++;
			}		
			pw.print("</table>");
			pw.print("<br>");
			pw.print("<input type='hidden' name='finalTotal' value='"+total+"'>");
			pw.print("<input type='hidden' name='finalDiscount' value='"+totalDiscount+"'>");
			pw.print("<input type='hidden' name='finalRebate' value='"+totalRebate+"'>");
			pw.print("<input type='hidden' name='finalDiscountedTotal' value='"+totalDiscountedTotal+"'>");
			pw.print("<h4 style='margin-left:15px;color:#842121;'>Subtotal: $</h4>");
//			pw.print("<h4 style='margin-left:104px;margin-top:-29px;color:#842121;'></h4>"); style='visibility:hidden;'
			//pw.print("<h4 class='subTotal' name='subTotal' id='subTotal' //style='margin-left:103px;margin-top:-29px;color:#842121;'>"+total+"</h4>");
			pw.print("<h4 style='margin-left:101px;margin-top:-29px;color:#842121;'><input type='text' style='background-color:#c1d7e9;border:none;color:#842121;' class='subTotal' name='subTotal' value='"+total+"' readonly></input></h4>");
			pw.print("<h4 style='margin-left:15px;color:#842121;'>Total Discount: $</h4>");
			//pw.print("<h4 class='orderFinalDiscount' name='orderFinalDiscount' style='margin-left:149px;margin-top:-29px;color:#842121;'>"+totalDiscount+"</h4>");
			pw.print("<h4 style='margin-left:151px;margin-top:-30px;color:#842121;'><input type='text' style='background-color:#c1d7e9;border:none;color:#842121;' class='orderFinalDiscount' name='orderFinalDiscount' value='"+totalDiscount+"' readonly></input></h4>");
			pw.print("<h4 style='margin-left:15px;color:#842121;'>Total Manufacturer Rebate: $</h4>");
			//pw.print("<h4 class='orderFinalRebate' name='orderFinalRebate' style='margin-left:249px;margin-top:-29px;color:#842121;'>"+totalRebate+"</h4>");
			pw.print("<h4 style='margin-left:248px;margin-top:-30px;color:#842121;'><input type='text' style='background-color:#c1d7e9;border:none;color:#842121;' class='orderFinalRebate' name='orderFinalRebate' value='"+totalRebate+"' readonly></input></h4>");
			pw.print("<h4 style='margin-left:15px;color:#842121;'>Amount to Pay: $</h4>");
			//pw.print("<h4 id='total_all_amounts' name='total_all_amounts' class='total_all_amounts' style='margin-left:155px;margin-top:-29px;color:#842121;'>"+totalDiscountedTotal+"</h4>");
			pw.print("<h4 style='margin-left:155px;margin-top:-30px;color:#842121;'><input type='text' style='background-color:#c1d7e9;border:none;color:#842121;' class='total_all_amounts' name='total_all_amounts' value='"+totalDiscountedTotal+"' readonly></input></h4>");			
			pw.print("<input type='submit' name='CheckOut' value='Proceed to CheckOut' class='btnCheckOut' />");
			pw.print("<br><br>");
			pw.print("</form>");
			pw.print(carousel.carouselfeature(utility));
		}
		else
		{
			pw.print("<h3 style='color:red;margin-left:15px;'>Your Cart is empty...!</h3>");
		}
		pw.print("</div></body>");	
		utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);		
		displayCart(request, response);
	}
}
