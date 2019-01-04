import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	

			
			
public class Carousel{
			
	public String  carouselfeature(Utilities utility){
				
		MedicineRecommenderUtility prodRecUtility = new MedicineRecommenderUtility();
		HashMap<String, Medicine> hm = new HashMap<String, Medicine>();
		StringBuilder sb = new StringBuilder();
		String myCarousel = null;
		String name = null;
		String CategoryName = null;
		if(CategoryName==null){
			try{
			hm=MySqlDataStoreUtilities.getMedicines();
			name = "";
			System.out.println("Entered");
			System.out.println("HM size"+hm.size());
			}
			catch(Exception e)
			{
				
			}
		}
			HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		prodRecmMap = prodRecUtility.readOutputFile();
		int l =0;
		for(String user: prodRecmMap.keySet())
		{
			if(user.equals(utility.username()))
			{
				String products = prodRecmMap.get(user);
				products=products.replace("[","");
				products=products.replace("]","");
				products=products.replace("\"", " ");
				ArrayList<String> productsList = new ArrayList<String>(Arrays.asList(products.split(",")));
		        myCarousel = "myCarousel"+l;					
				sb.append("<div><div><h2>");
				sb.append("<a style='margin-left:639px;color:brown;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 26px;color: #000000;'>"+""+" Recommended Products</a><br>");				
				sb.append("</h2>");
				sb.append("<div class='container'>");				
				sb.append("<div class='carousel slide' id='"+myCarousel+"' data-ride='carousel'>");
				sb.append("<div class='carousel-inner'>");						
				int k = 0;
				for(String prod : productsList){
					prod= prod.replace("'", "");
					Medicine prodObj = new Medicine();
					prodObj = MedicineRecommenderUtility.getMedicine(prod.trim());
					if (k==0 )
					{
						
						sb.append("<div class='item active'><div class='col-md-6' style = 'background-color: #58acfa;border :1px solid #cfd1d3'>");
					}
					else
					{
						sb.append("<div class='item'><div class='col-md-6' style = 'background-color: #58acfa ;border :1px solid #cfd1d3' >");
					}
					sb.append("<div id='shop_item_1'>");
					sb.append("<h3>"+prodObj.getProductName()+"</h3>");
					//sb.append("<strong>"+prodObj.getProductPrice()+"$</strong><ul>");
					sb.append("<strong> Price: $"+prodObj.getProductPrice()+"</strong>");
					sb.append("<strong> Discount: $"+prodObj.getDiscount()+"</strong>");
					sb.append("<strong> Quantity Availiable: "+prodObj.getAvailableProductQuantity()+"</strong>");
					if(prodObj.getAvailableProductQuantity()==0)
					{
						sb.append("<strong style='color:red;'> Out of Stock!!!</strong>");
					}
					sb.append("<ul><li id='item_1'><img src='images/medicines/"+prodObj.getProductImage()+"' alt='' /></li>");
					if(prodObj.getAvailableProductQuantity()==0)
					{		
						sb.append("<br><li><form method='post' action='Cart'>" +
						"<input type='hidden' name='name' value='"+prod.trim()+"'>"+
						"<input type='hidden' name='type' value='"+prodObj.getProductType()+"'>"+
						"<input type='hidden' name='medicineName' value='"+prodObj.getProductName()+"'>"+
						"<input type='hidden' name='maker' value='"+prodObj.getProductName()+"'>"+
						"<input type='hidden' name='manufacturer' value='"+prodObj.getProductManufacturer()+"'>"+
						"<input type='hidden' name='access' value=''>"+
						"<input type='submit' class='btn_disable' value='Buy Now' disabled='disabled'></form></li>");
					}
					else
					{
						sb.append("<br><li><form method='post' action='Cart'>" +
						"<input type='hidden' name='name' value='"+prod.trim()+"'>"+
						"<input type='hidden' name='type' value='"+prodObj.getProductType()+"'>"+
						"<input type='hidden' name='medicineName' value='"+prodObj.getProductName()+"'>"+
						"<input type='hidden' name='maker' value='"+prodObj.getProductName()+"'>"+
						"<input type='hidden' name='manufacturer' value='"+prodObj.getProductManufacturer()+"'>"+
						"<input type='hidden' name='access' value=''>"+
						"<input type='submit' class='btn_viewpProfile' value='Buy Now'></form></li>");
					}
					sb.append("<br><li><form method='post' action='WriteReviewMedicine'>"+
							"<input type='hidden' name='name' value='"+prodObj.getProductName()+"'>"+
							"<input type='hidden' name='type' value='"+prodObj.getProductType()+"'>"+
							"<input type='hidden' name='medicineName' value='"+prodObj.getProductName()+"'>"+
							"<input type='hidden' name='maker' value='"+prodObj.getProductType()+"'>"+
							"<input type='hidden' name='manufacturer' value='"+prodObj.getProductManufacturer()+"'>"+
							"<input type='hidden' name='access' value='"+" "+"'>"+
							"<input type='hidden' name='price' value='"+prodObj.getProductPrice()+"'>"+
							"<input type='submit' value='WriteReview' class='btn_review'></form></li>");							
					sb.append("<br><li><form method='post' action='ViewReviewMedicine'>"+
							"<input type='hidden' name='name' value='"+prodObj.getProductName()+"'>"+
							"<input type='hidden' name='type' value='"+prodObj.getProductType()+"'>"+
							"<input type='hidden' name='medicineName' value='"+prodObj.getProductName()+"'>"+
							"<input type='hidden' name='maker' value='"+prodObj.getProductType()+"'>"+
							"<input type='hidden' name='manufacturer' value='"+prodObj.getProductManufacturer()+"'>"+
							"<input type='hidden' name='access' value='"+" "+"'>"+
							"<input type='hidden' name='price' value='"+prodObj.getProductPrice()+"'>"+
							"<input type='submit' value='ViewReview' class='btn_review'></form></li>");

					sb.append("</ul></div></div>");
					sb.append("</div>");				
					k++;
				}
				sb.append("</div>");
				sb.append("<a class='left carousel-control' href='#"+myCarousel+"' data-slide='prev' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>"+
						"<span class='sr-only'>Previous</span>"+
						"</a>");
				sb.append("<a class='right carousel-control' href='#"+myCarousel+"' data-slide='next' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>"+

							"<span class='sr-only'>Next</span>"+
							"</a>");
				sb.append("</div>");			
				sb.append("</div></div>");
				sb.append("<br><br></div>");
				l++;
				}
			}
			return sb.toString();
		}
	}
	