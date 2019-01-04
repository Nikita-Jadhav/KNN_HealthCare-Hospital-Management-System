import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

@WebServlet("/AvailableMedicineOnSale")

public class AvailableMedicineOnSale extends HttpServlet {
	ResultSet rs = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view available medicine on sale...!");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		//HashMap<String,ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReviewCount();		
		try
		{
			rs=MySqlDataStoreUtilities.selectProductOnSale();
		}
		catch(Exception e){}
		String productName = "";
		String productPrice="";
		double discount=0.0;
		
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		pw.print("<div id='content_1' style='margin-top:-737px;'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Medicine Available on Sale</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry_1' style='margin-top:-677px;'><br>");
		
		/*ResultSet rs = null;
		try
		{
			rs=MySqlDataStoreUtilities.selectProductOnSale();
		}
		catch(Exception e){}*/
		pw.print("<table class='appointmentDisplayTable' style='margin-left:15px;'>");
		pw.print("<tr><td><h4 style='color:brown;'>Sr.No</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Medicine Name&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Medicine Price($)&nbsp&nbsp</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Discount($)&nbsp&nbsp</h4></td></tr>");
		if(rs==null)
		{
			pw.println("<h2>MySql server is not up and running...!</h2>");
			//pw.println("<tr><td><h2>no</td></tr></h2>");
		}
		else
		{
				/*int j=1;
				Set keys = hm.keySet();
				for(Iterator i = keys.iterator(); i.hasNext(); )
				{
					String key = (String) i.next();
					for (Review r : hm.get(key)) 
					{				
						pw.print("<tr><td>"+j+".</td>");
						productName=r.getProductName();
						pw.print("<td>"+productName+"</td>");
						pw.print("<td>"+r.getPrice()+"</td>");
						pw.print("</tr>");
						j++;
					}
				}*/
				try
				{
			int j=1;
			while(rs.next())
			{
				pw.print("<tr>");	
				pw.print("<td>"+j+".</td>");							
				pw.print("<td>"+rs.getString("productName")+"</td>");
				pw.print("<td>"+rs.getDouble("productPrice")+"</td>");
				/*int x=0;
				x=rs.getInt("originalProductCount")-rs.getInt("soldProductCount");*/
				pw.print("<td>"+rs.getDouble("discount")+"</td>");
				pw.print("</tr>");
				j++;
			}
	}
	catch(Exception e){}
				
		}
					
		pw.print("</table>");		
		pw.println("<br><br></div>");
		utility.printHtml("Footer.html");
	}
}


