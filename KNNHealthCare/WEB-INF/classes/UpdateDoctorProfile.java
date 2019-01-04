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

@WebServlet("/UpdateDoctorProfile")

public class UpdateDoctorProfile extends HttpServlet {
	String msg="";
	String dname = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);		
		displayMyProfile(request, response, false);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesDoctor utility = new UtilitiesDoctor(request, pw);
		String action = request.getParameter("button");
		System.out.print(action);
		String doctorDescription = request.getParameter("doctorDescription");
		String qualification   = request.getParameter("qualification");
		String yearsInPractice = request.getParameter("yearsInPractice"); 
		String languages = request.getParameter("languages");
		Double visitingPrice = Double.parseDouble(request.getParameter("visitingPrice"));
		String emailId = request.getParameter("emailId");
		String contactNumber_1   = request.getParameter("contactNumber_1");
		String contactNumber_2 = request.getParameter("contactNumber_2"); 
		String pic = request.getParameter("pic");
		
		String doctorName = "Dr. "+request.getSession(false).getAttribute("username").toString();
		try
		{
				msg = MySqlDataStoreUtilities.updateDoctorProfile(dname,doctorDescription,qualification,yearsInPractice,languages,visitingPrice,emailId,contactNumber_1,contactNumber_2,pic);
		}
		catch(Exception e)
		{ 
				msg = "Unable to update doctor profile...!";
		}
		displayMyProfile(request, response, true);
	}
	
	protected void displayMyProfile(HttpServletRequest request, HttpServletResponse response,boolean error) throws ServletException, IOException
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
					pw.print("<div id='entry_1'><br><div id='viewDoctorProfile'><ul>");
					if (error)
						pw.print("<h3 style='font-family:Century Schoolbook;color:#e50000;'>"+msg+"</h3>");
					pw.print("<form method='post' action='UpdateDoctorProfile'>");
					pw.print("<li><h3 style='color:black;font-weight:bold;'>Name: "+rs.getString("Name")+"</h3></li>");
					dname=rs.getString("Name");
					pw.print("<li><h3 style='color:black;font-size:20px;'>Description: </h3></li>");
					pw.print("<textarea style='font-size:20px;margin-left:3px;border-radius:5px;width:888px;height:225px;' rows='10' cols='50' name='doctorDescription'>"+rs.getString("doctorDescription")+"</textarea>");					
					pw.print("<li><h3 style='color:black;font-size:20px;'>Qualification: </h3></li>");
					pw.print("<input size='75' style='font-size:20px;margin-left:3px;border-radius:5px;width:250px;height:35px;' type='text' name='qualification' value='"+rs.getString("qualification")+"' class='input' required></input>");					
					pw.print("<li><h3 style='color:black;font-size:20px;'>Experience (in years): </h3></li>");
					pw.print("<input size='75' style='font-size:20px;margin-left:3px;border-radius:5px;width:250px;height:35px;' type='text' name='yearsInPractice' value='"+rs.getString("yearsInPractice")+"' class='input' required></input>");					
					pw.print("<li><h3 style='color:black;font-size:20px;'>Languages spoken: </h3></li>");
					pw.print("<input size='75' style='font-size:20px;margin-left:3px;border-radius:5px;width:250px;height:35px;' type='text' name='languages' value='"+rs.getString("languages")+"' class='input' required></input>");
					pw.print("<li><h3 style='color:black;font-size:20px;'>Price per Visit ($): </h3></li>");
					pw.print("<input size='75' style='font-size:20px;margin-left:3px;border-radius:5px;width:250px;height:35px;' type='text' name='visitingPrice' value='"+rs.getDouble("visitingPrice")+"' class='input' required></input>");
					pw.print("<li><h3 style='color:black;font-size:20px;'>Email Id: </h3></li>");
					pw.print("<input size='75' style='font-size:20px;margin-left:3px;border-radius:5px;width:450px;height:35px;' type='text' name='emailId' value='"+rs.getString("emailId")+"' class='input' required></input>");
					pw.print("<li><h3 style='color:black;font-size:20px;'>Contact Number: </h3></li>");
					pw.print("<input size='75' style='font-size:20px;margin-left:3px;border-radius:5px;width:250px;height:35px;' type='text' name='contactNumber_1' value='"+rs.getString("contactNumber_1")+"' class='input' required></input>");
					pw.print("<input size='75' style='font-size:20px;margin-left:20px;border-radius:5px;width:250px;height:35px;' type='text' name='contactNumber_2' value='"+rs.getString("contactNumber_2")+"' class='input' required></input></ul>");
					pw.print("<div id='profilePictureDoctor'><img src='images/doctors/"+rs.getString("doctorImage")+"' alt='' /></div>");
					pw.print("<br><li style='list-style:none;'><h3 style='margin-top:443px;margin-bottom:22px;color:black;font-size:20px;'>Visiting days and hours: </h3>");
					rsVisitingInfo=MySqlDataStoreUtilities.selectVisitingInformation(rs.getString("Name"));
					while(rsVisitingInfo.next())
					{
						pw.print("<input size='75' style='margin-top:-31px;font-size:20px;margin-left:3px;border-radius:5px;width:350px;height:35px;' type='text' name='visitingDay' value='"+rsVisitingInfo.getString("visitingDay")+"' class='input' required></input><br><br>");
					}
					pw.print("<ul><li><h3 style='margin-top:8px;color:black;font-size:20px;'>Upload profile picture: </h3></li>");
					pw.print("<input style='margin-top:0px;font-size:20px;margin-left:3px;border-radius:5px;' type='file' name='pic' id='pic' required></input></ul>");
					pw.print("<input type='submit' class='btn_prescription' name='button' value='Submit'></input><br><br>");
				}
			}
		}
		catch(Exception e){}
		pw.print("</form></div></div>");
		utility.printHtml("Footer.html");
	}	
}
