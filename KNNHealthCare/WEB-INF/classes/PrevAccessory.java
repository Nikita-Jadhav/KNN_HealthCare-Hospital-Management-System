import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PrevAccessory")

public class PrevAccessory extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
		String ProductName = request.getParameter("access");
		int j = Integer.parseInt(request.getParameter("value"));
		if (j-2 < 0){
	        j =0;	
		}else
		{
			j = j-2;
		}
		HashMap<String, Medicine> hm = new HashMap<String, Medicine>();
		//HashMap<String, Laptop> hmlaptop = new HashMap<String, Laptop>();
		if(CategoryName==null){
			hm.putAll(SaxParserDataStore.medicines);
			//hmlaptop.putAll(SaxParserDataStore.laptops);
			name = "";
		}
		else
		{
		   if(CategoryName.equals("Allergies"))
		   {
			for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet()) 
			 {
				if(entry.getValue().getProductType().equals("Allergies"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Allergies";
		   }
		   else if(CategoryName.equals("Antibiotics"))
		    {
			for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet())
				{
				 if(entry.getValue().getProductType().equals("Antibiotics"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Antibiotics";
			}
			else if(CategoryName.equals("AntiViral"))
			{
				for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet())
				{
				 if(entry.getValue().getProductType().equals("Anti Viral"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Anti Viral";
			}
			else if(CategoryName.equals("HeartDisease"))
			{
				for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet())
				{
				 if(entry.getValue().getProductType().equals("Heart Disease"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Heart Disease";
			}
			else if(CategoryName.equals("KidneyDisease"))
			{
				
				for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet())
				{
				 if(entry.getValue().getProductType().equals("Kidney Disease"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Kidney Disease";
			}
			else if(CategoryName.equals("Skincare"))
			{
				
				for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet())
				{
				 if(entry.getValue().getProductType().equals("Skincare"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Skincare";
			}
			
		}
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content_1'><div class='post_1'><h2 class='title_1 meta_1'>");
		pw.print("<a style='font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 24px;color: #000000;'>"+name+" Phones</a>");
		pw.print("</h2><div class='entry_1'><table id='bestseller_1'>");
	//	int i = 1; int size= hm.size();
		for(Map.Entry<String, Medicine> entry : hm.entrySet())
		{
			Medicine medicine = entry.getValue();
			if (medicine.getProductName().equals(ProductName))
			{
			pw.print("<tr>");
			pw.print("<td><div id='shop_item_1'>");
			pw.print("<h3>"+medicine.getProductName()+"</h3>");
			pw.print("<strong>$"+medicine.getProductPrice()+"</strong><ul>");
			pw.print("<li id='item_1'><img src='images/medicines/"+medicine.getProductImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='medicines'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReviewM'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='medicines'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReviewM' class='btnreview_1'></form></li>");
			pw.print("<li><form method='post' action='ViewReviewM'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='medicines'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReviewM' class='btnreview_1'></form></li>");
			pw.print("</ul></div></td>");
			pw.print("</tr>");
			
			}
		}
		pw.print("</table></div></div></div>");	
		
		pw.print("<div id='content_1'><div class='post_1'><h2 class='title_1 meta_1'>");
		pw.print("<a style='font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 24px;color: #000000;'>"+ProductName+" Accessories</a>");
		pw.print("</h2><div class='entry_1'><table id='bestseller'_1>");
		Medicine phone1 = hm.get(ProductName);
		//Laptop laptop1 = hmlaptop.get(ProductName);
		System.out.print(ProductName);
		int i = 1; int size= hm.size();
		pw.print("<tr>");
		ArrayList<String> arr = new ArrayList<String> ();
		for(Map.Entry<String, String> acc:phone1.getAccessories().entrySet())
		{
			
			arr.add(acc.getValue());
		}

		if (j > 0){
		pw.print("<td><li><form method='post' action='PrevAccessory'>" +
					"<input type='hidden' name='name' value='"+arr+"'>"+
					"<input type='hidden' name='value' value='"+j+"'>"+
					"<input type='hidden' name='type' value='accessories'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value='"+ProductName+"'>"+
					"<input type='submit' class='btn_viewpProfile' value='Prev'></form></li></td>");
					}
		for (int k =1;j < arr.size()&& k <= 2;j++,k++){
	    Accessory accessory= SaxParserDataStore.accessories.get(arr.get(j));
		    
			
			pw.print("<td><div id='shop_item_1'>");
			pw.print("<h3>"+accessory.getProductName()+"</h3>");
			pw.print("<strong>"+accessory.getProductPrice()+"$</strong><ul>");
			pw.print("<li id='item_1'><img src='images/accessories/"+accessory.getProductImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+arr.get(j)+"'>"+
					"<input type='hidden' name='type' value='accessories'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value='"+ProductName+"'>"+
					"<input type='submit' class='btn_viewpProfile' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReviewM'>"+"<input type='hidden' name='name' value='"+arr+"'>"+
					"<input type='hidden' name='type' value='accessories'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value='"+ProductName+"'>"+
				    "<input type='submit' value='WriteReviewM' class='btnreview_1'></form></li>");
			pw.print("<li><form method='post' action='ViewReviewM'>"+"<input type='hidden' name='name' value='"+arr+"'>"+
					"<input type='hidden' name='type' value='accessories'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value='"+ProductName+"'>"+
				    "<input type='submit' value='ViewReviewM' class='btnreview_1'></form></li>");
		
			pw.print("</ul></div></td>");
			
			
			
			
		}
		pw.print("<td><li><form method='post' action='NextAccessory'>" +
					"<input type='hidden' name='name' value='"+arr+"'>"+
					"<input type='hidden' name='value' value='"+j+"'>"+
					"<input type='hidden' name='type' value='accessories'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value='"+ProductName+"'>"+
					"<input type='submit' class='btn_viewpProfile' value='Next'></form></li></td>");
		pw.print("</tr>");		
		pw.print("</table><a class='prev' onclick='plusSlides(-1)'>&#10094</a>"+
       "<a class='next' onclick='plusSlides(1)'>&#10095</a></div></div></div></div>");
     		
		utility.printHtml("Footer.html");
		
	}
}
