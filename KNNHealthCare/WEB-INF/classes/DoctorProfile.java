import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

@WebServlet("/DoctorProfile")

public class DoctorProfile extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
			String doctorName="Dr. "+request.getSession(false).getAttribute("username").toString();
			ResultSet rs = null;
			ResultSet rsVisitingInfo = null;
			try
			{
				rs=MySqlDataStoreUtilities.selectDoctor(doctorName);
			}
			catch(Exception e){}
			UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
			utility.printHtml("HeaderDoctor.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			try
			{
				if(rs==null)
				{
					pw.print("<h3 style='color:black;'>Dr.</h3>");
				}
				else
				{
					while(rs.next())
					{
						pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+rs.getString("speciality")+"</a></h2></div>");
						pw.print("<br>");
						pw.print("<div id='entry_1'><br><div id='viewProfile'><ul>");
						String dname = null;
						pw.print("<li><h3 style='color:black;font-weight:bold;'>Name: "+rs.getString("Name")+"</h3></li>");
						dname=rs.getString("Name");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Description: "+rs.getString("doctorDescription")+"</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Qualification: "+rs.getString("qualification")+"</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Experience: "+rs.getString("yearsInPractice")+" years</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Languages spoken: "+rs.getString("languages")+"</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Price per Visit: $"+rs.getString("visitingPrice")+"</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Email Id: "+rs.getString("emailId")+"</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;'>Contact Number: "+rs.getString("contactNumber_1")+"</h3></li>");
						pw.print("<li><h3 style='color:black;font-size:20px;margin-left:156px;'>"+rs.getString("contactNumber_2")+"</h3></li></ul>");
						pw.print("<div id='profilePicture'><img src='images/doctors/"+rs.getString("doctorImage")+"' alt='' /></div>");
						pw.print("<br><li style='list-style:none;'><h3 style='margin-top:-30px;color:black;font-size:20px;'>Visiting days and hours: </h4>");
						rsVisitingInfo=MySqlDataStoreUtilities.selectVisitingInformation(rs.getString("Name"));
						while(rsVisitingInfo.next())
						{
							pw.print("<h3 style='margin-top:-31px;margin-left:220px;color:black;font-size:20px;'>"+rsVisitingInfo.getString("visitingDay")+"</h3><br><br>");
						}
					}
				}
			}
			catch(Exception e){}
			pw.print("</div></div>");
			utility.printHtml("Footer.html");
	}	
}
