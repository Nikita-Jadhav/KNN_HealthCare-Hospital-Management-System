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

@WebServlet("/MyPrescription")

public class MyPrescription extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String userName=request.getSession(false).getAttribute("username").toString();
		ResultSet rsTemp = null;
		ResultSet rs = null;
		ResultSet rsVisitingInfo = null;
		try
		{
			rsTemp=MySqlDataStoreUtilities.selectPrescription(userName);
			rs=MySqlDataStoreUtilities.selectPrescription(userName);
		}
			catch(Exception e){}		
			Utilities utility = new Utilities(request,pw);
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='post'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>My Prescription</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry'><br>");
			try
			{
				if(!rsTemp.next())
				{
					pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>No prescription...!</h3>");
				}
				else
				{
					pw.print("<br><br><table  class='prescriptionDisplayTable'>");
					pw.print("<td><h4 style='color:brown;'>Sr. No</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Patient Name</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Doctor Name</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Nature Of Illness</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Medicine Prescription</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Additional Notes</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Prescription Date   (YYYY-MM-DD)</h4></td></tr>");
					int count=1;
					while(rs.next())
					{
						String x=rs.getString("additionalNotes");;
						if(x.isEmpty()||x==null || x=="")
						{
							x="Not Applicable";
						}
						pw.print("<tr>");			
						pw.print("<td>"+count+".</td><td>"+rs.getString("patientName")+"</td><td>"+rs.getString("doctorName")+"</td><td>"+rs.getString("natureOfIllness")+"</td><td>"+rs.getString("medicinePrescription")+"</td><td>"+x+"</td><td>"+rs.getString("prescriptionDate")+"</td>");
						pw.print("</tr>");
						count++;
					}
				}
			}
			catch(Exception e){}
			pw.print("</table>");
			pw.print("<br><br></div>");
			utility.printHtml("Footer.html");
	}	
}
