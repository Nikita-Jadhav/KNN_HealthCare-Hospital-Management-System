import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatchesDoctorUtilities")

public class DealMatchesDoctorUtilities extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

			HashMap<String,Medicine> selectedproducts=new HashMap<String,Medicine>();
		try
			{
		pw.print("<div id='bookappointmentHeader'><br><h3 style='color:#4c4c4c;margin-left:650px;margin-top:-3px;font-size:30px;font-family:Arial, Helvetica, sans-serif;'>Deals on Medicines</h3></div>");
		pw.print("<div id='dealmatch'>");
		pw.print("<br> <br>");
		pw.print("<h3 align='center' style='font-weight:bold;'>The world trusts us to deliver SPEEDY service for medicines</h3>");
		pw.print("<br>");
		pw.print("<h1 align='center' style='color:red;font-weight:bold;'>We beat our competitors in all aspects. Price-Match Guaranteed!</h1><br><br>");
		
			String line=null;
			String TOMCAT_HOME = System.getProperty("catalina.home");

			HashMap<String,Medicine> productmap=MySqlDataStoreUtilities.getMedicines();
			
			for(Map.Entry<String, Medicine> entry : productmap.entrySet())
			{
			System.out.println("11111111111111111");	
			if(selectedproducts.size()<2 && !selectedproducts.containsKey(entry.getKey()))
			{		
				
				
			BufferedReader reader = new BufferedReader(new FileReader (new File(TOMCAT_HOME+"\\webapps\\KNNHealthCare\\DealMatches.txt")));
			line=reader.readLine().toLowerCase();
			
			//System.out.println(line);	
			if(line==null)
			{
				System.out.println("************************************");	
				pw.print("<h3 align='center' style='color:#337ab7;font-weight:bold;'>No Offers Found!!!</h3>");
				break;
			}	
			else
			{	
			do {	
			      //System.out.println("**%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55***");
				  System.out.println(entry.getValue().getProductName());
				  if(line.contains(entry.getValue().getProductName()))
				  {
				 System.out.println("*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*****");
				 System.out.println(line);
					pw.print("<h5 align='center' style='font-size:30px;color:crimson'>"+line+"</h5>");
					pw.print("<br>");
					selectedproducts.put(entry.getKey(),entry.getValue());
					break;
				  }
				  
			    }while((line = reader.readLine()) != null);
			
			 }
			 }
			}
			}
			catch(Exception e)
			{
			pw.print("<h2 align='center' style='color:#337ab7;font-weight:bold;'>No Offers Found!!!</h2>");
			}
		pw.print("</div>");
		pw.print("<div id='bookappointmentHeader'><br><h3 allign='center' style='color:#4c4c4c;margin-left:650px;margin-top:-3px;font-size:30px;font-family:Arial, Helvetica, sans-serif;'>Deals Matches</h3></div>");
		pw.print("<div id='dealmatchimg'>");
		System.out.println("11111111111111111"+selectedproducts.size());	
		if(selectedproducts.size()==0)
		{
		pw.print("<br><h2 align='center' style='color:#337ab7;font-weight:bold;font-weight:bold;'>No Deals Found!!!</h2>");	
		}
		else
		{
		pw.print("<div id='entry_2'><br><table id='bestseller_1'>");
		pw.print("<tr>");
		for(Map.Entry<String, Medicine> entry : selectedproducts.entrySet()){
		pw.print("<td><div id='shop_item_1'><h3>"+entry.getValue().getProductName()+"</h3>");
		pw.print("<strong> Price: $"+entry.getValue().getProductPrice()+"$</strong>");
		pw.print("<strong> Discount: $"+entry.getValue().getDiscount()+"</strong>");
		pw.print("<strong> Quantity Availiable: "+entry.getValue().getAvailableProductQuantity()+"</strong><ul>");	
		pw.print("<ul>");
		pw.print("<li id='item_1'><img src='images/medicines/"+entry.getValue().getProductImage()+"' alt='' />");
		pw.print("</li><li>");
		pw.print("<br><form action='ViewReviewMedicine' method='post'><input type='submit' class='btn_review1' value='ViewReview'>"
		+"<input type='hidden' name='type' value='"+entry.getValue().getProductType()+"'>"
		+"<input type='hidden' name='name' value='"+entry.getValue().getId()+"'>"
		+"<input type='hidden' name='maker' value='"+entry.getValue().getProductType()+"'>"
		+"<input type='hidden' name='medicineName' value='"+entry.getValue().getProductName()+"'>"
		+"<input type='hidden' name='manufacturer' value='"+entry.getValue().getProductManufacturer()+"'>"
		+"<input type='hidden' name='price' value='"+entry.getValue().getProductPrice()+"'>"
		+"</form></li></ul></div></td>");
		}
		pw.print("</tr></table>");
		}
		pw.print("</div></div></div></div>");
		
		pw.print("</div>");
		
	}
}