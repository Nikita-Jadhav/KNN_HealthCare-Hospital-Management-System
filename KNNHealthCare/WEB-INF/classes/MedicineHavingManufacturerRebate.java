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

@WebServlet("/MedicineHavingManufacturerRebate")

public class MedicineHavingManufacturerRebate extends HttpServlet {
	ResultSet rs = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();		
		try
		{
			rs=MySqlDataStoreUtilities.selectProductHavingManufacturerRebate();
		}
		catch(Exception e){}		
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		pw.print("<div id='post' style='margin-top:-737px;'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Medicines having Manufacturer Rebate</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'><br>");
		pw.print("<table class='appointmentDisplayTable' style='margin-left:15px;'>");
		pw.print("<tr><td><h4 style='color:brown;'>Sr.No</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Medicine Name&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Medicine Price($)&nbsp&nbsp</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Manufacturer Rebate($)&nbsp&nbsp</h4></td></tr>");
		if(rs==null)
		{
			pw.println("<h2>MySql server is not up and running...!</h2>");
		}
		else
		{
			try
			{
				int j=1;
				while(rs.next())
				{
					pw.print("<tr>");	
					pw.print("<td>"+j+".</td>");							
					pw.print("<td>"+rs.getString("productName")+"</td>");
					pw.print("<td>"+rs.getDouble("productPrice")+"</td>");
					pw.print("<td>"+rs.getDouble("manufacturerRebate")+"</td>");
					pw.print("</tr>");
					j++;
				}
			}
			catch(Exception e){}		
		}
		pw.print("</table>");		
		pw.print("<br><br>");
		pw.print("</div>");
		utility.printHtml("Footer.html");
	}
}





