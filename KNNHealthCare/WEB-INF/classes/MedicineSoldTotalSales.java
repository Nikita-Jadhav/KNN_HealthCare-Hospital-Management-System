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

@WebServlet("/MedicineSoldTotalSales")

public class MedicineSoldTotalSales extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View Medicine sold and total sales...!");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		pw.print("<div id='content_1' style='margin-top:-737px;'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Product Sold & Total Sales Information</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry_1' style='margin-top:-677px;'><br>");
		pw.print("<table class='appointmentDisplayTable' style='margin-left:15px;'>");
		ResultSet rs = null;
		try
		{
			rs=MySqlDataStoreUtilities.selectProductSold();
		}
		catch(Exception e){}
		pw.print("<tr><td><h4 style='color:brown;'>Sr.No</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Order Name&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Order Price($)&nbsp&nbsp</h4></td>");
		pw.print("<td><h4 style='color:brown;'>Number of Quantity Sold&nbsp&nbsp</h4></td>");
		//pw.print("<td>Total Sales($)</td></tr>");
		try
		{
			int j=1;
			while(rs.next())
			{
				pw.print("<tr>");	
				pw.print("<td>"+j+".</td>");							
				pw.print("<td>"+rs.getString("orderName")+"</td>");
				pw.print("<td>"+rs.getDouble("totalOrderPayablePrice")+"</td>");
				pw.print("<td>"+rs.getInt("totalquantityOrdered")+"</td>");
			//	double totalSale= rs.getDouble("orderPrice")* rs.getInt("quantityOrderd");
			//	pw.print("<td>"+totalSale+"</td>");
				pw.print("</tr>");
				j++;
			}
	}
	catch(Exception e){}
		pw.print("</table>");
		pw.print("<br><br>");
		pw.print("</div>");
		utility.printHtml("Footer.html");
	}
}

