import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@WebServlet("/MedicineData")
public class MedicineData extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String userType=request.getSession(false).getAttribute("usertype").toString();		
			PrintWriter pw= response.getWriter();
			response.setContentType("text/html");
			if(userType.equals("Medicine Manager"))
			{
				pw.println("<html>");
				pw.println("<body>");
				UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);
				Medicine data= (Medicine)request.getAttribute("data");
				utility.printHtml("HeaderMedicineManager.html");
				utility.printHtml("LeftNavigationBarMedicineManager.html");
				pw.print("<div id='content_1' style='margin-top:-740px;'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+data.getProductType()+"</a></h2></div>");
				pw.print("<br>");
				pw.print("<div id='entry_1' style='margin-top:-678px;'><br><table id='bestseller_1'>");
				pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");				
				pw.print("<h3>"+data.getProductName()+"</h3>");
				pw.print("<strong> Price: $"+data.getProductPrice()+"</strong>");
				pw.print("<strong> Discount: $"+data.getDiscount()+"</strong>");
				pw.print("<strong> Quantity Availiable: "+data.getAvailableProductQuantity()+"</strong>");
				if(data.getAvailableProductQuantity()==0)
				{
					pw.print("<strong style='color:red;'> Out of Stock!!!</strong>");
				}
				pw.print("<ul><li id='item_2'><img src='images/medicines/"+data.getProductImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='post' action='ViewReviewMedicine'>"+
				"<input type='hidden' name='type' value='"+data.getProductType()+"'>"+
				"<input type='hidden' name='maker' value='"+data.getProductType()+"'>"+
				"<input type='hidden' name='medicineName' value='"+data.getProductName()+"'>"+
				"<input type='hidden' name='manufacturer' value='"+data.getProductManufacturer()+"'>"+
				"<input type='hidden' name='price' value='"+data.getProductPrice()+"'>"+
				"<input type='hidden' name='access' value=''>"+
				"<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				pw.print("</tr>");
				pw.print("</table><br></div></div>");		
				utility.printHtml("Footer.html");
			}
			if(userType.equals("Patient")||userType.equals("1"))
			{
				pw.println("<html>");
				pw.println("<body>");
				Utilities utility = new Utilities(request,pw);
				Medicine data= (Medicine)request.getAttribute("data");
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content_1'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+data.getProductType()+"</a></h2></div>");
				pw.print("<br>");
				pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
				pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");				
				pw.print("<h3>"+data.getProductName()+"</h3>");
				pw.print("<strong> Price: $"+data.getProductPrice()+"</strong>");
				pw.print("<strong> Discount: $"+data.getDiscount()+"</strong>");
				pw.print("<strong> Quantity Availiable: "+data.getAvailableProductQuantity()+"</strong>");
				if(data.getAvailableProductQuantity()==0)
				{
					pw.print("<strong style='color:red;'> Out of Stock!!!</strong>");
				}
				pw.print("<ul><li id='item_2'><img src='images/medicines/"+data.getProductImage()+"' alt='' /></li>");
				if(data.getAvailableProductQuantity()==0)
				{		
					pw.print("<br><li><form method='post' action='Cart'>"+
					"<input type='hidden' name='name' value='"+data.getId()+"'>"+
					"<input type='hidden' name='type' value='"+data.getProductType()+"'>"+
					"<input type='hidden' name='medicineName' value='"+data.getProductName()+"'>"+
					"<input type='hidden' name='maker' value='"+data.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+data.getProductManufacturer()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_disable' value='Buy Now' disabled='disabled'></form></li>");
				}
				else
				{
					pw.print("<br><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+data.getId()+"'>"+
					"<input type='hidden' name='type' value='"+data.getProductType()+"'>"+
					"<input type='hidden' name='medicineName' value='"+data.getProductName()+"'>"+
					"<input type='hidden' name='maker' value='"+data.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+data.getProductManufacturer()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='Buy Now'></form></li>");
				}
				pw.print("<br><li><form method='post' action='WriteReviewMedicine'>"+
					"<input type='hidden' name='type' value='"+data.getProductType()+"'>"+
					"<input type='hidden' name='maker' value='"+data.getProductType()+"'>"+
					"<input type='hidden' name='medicineName' value='"+data.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+data.getProductManufacturer()+"'>"+
					"<input type='hidden' name='price' value='"+data.getProductPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btn_review'></form></li>");
					
				pw.print("<br><li><form method='post' action='ViewReviewMedicine'>"+
				"<input type='hidden' name='type' value='"+data.getProductType()+"'>"+
				"<input type='hidden' name='maker' value='"+data.getProductType()+"'>"+
				"<input type='hidden' name='medicineName' value='"+data.getProductName()+"'>"+
				"<input type='hidden' name='manufacturer' value='"+data.getProductManufacturer()+"'>"+
				"<input type='hidden' name='price' value='"+data.getProductPrice()+"'>"+
				"<input type='hidden' name='access' value=''>"+
				"<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				pw.print("</tr>");
				pw.print("</table><br></div></div>");		
				utility.printHtml("Footer.html");
			}
			if(userType.equals("Doctor"))
			{
				pw.println("<html>");
				pw.println("<body>");
				UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
				Medicine data= (Medicine)request.getAttribute("data");
				utility.printHtml("HeaderDoctor.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content_1'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+data.getProductType()+"</a></h2></div>");
				pw.print("<br>");
				pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
				pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");				
				pw.print("<h3>"+data.getProductName()+"</h3>");
				pw.print("<strong> Price: $"+data.getProductPrice()+"</strong>");
				pw.print("<strong> Discount: $"+data.getDiscount()+"</strong>");
				pw.print("<strong> Quantity Availiable: "+data.getAvailableProductQuantity()+"</strong>");
				if(data.getAvailableProductQuantity()==0)
				{
					pw.print("<strong style='color:red;'> Out of Stock!!!</strong>");
				}
				pw.print("<ul><li id='item_2'><img src='images/medicines/"+data.getProductImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='post' action='ViewReviewMedicine'>"+
				"<input type='hidden' name='type' value='"+data.getProductType()+"'>"+
				"<input type='hidden' name='maker' value='"+data.getProductType()+"'>"+
				"<input type='hidden' name='medicineName' value='"+data.getProductName()+"'>"+
				"<input type='hidden' name='manufacturer' value='"+data.getProductManufacturer()+"'>"+
				"<input type='hidden' name='price' value='"+data.getProductPrice()+"'>"+
				"<input type='hidden' name='access' value=''>"+
				"<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				pw.print("</tr>");
				pw.print("</table><br></div></div>");		
				utility.printHtml("Footer.html");
			}
		}
		catch(Exception e)
		{}
	}
	
	public void destroy()
	{
      // do nothing.
	}
}