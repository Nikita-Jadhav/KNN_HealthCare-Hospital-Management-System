import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/VisitedAppointment")

public class VisitedAppointment extends HttpServlet {
	private String error_msg;
	String doctorName="";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayVisitedAppointment(request, response);
	}
	
	protected void displayVisitedAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesDoctor utility = new UtilitiesDoctor(request, pw);
		try
         {  
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to view visited patients...!");
				response.sendRedirect("Login");
				return;
			}
			doctorName="Dr. "+request.getSession(false).getAttribute("username").toString();	
			utility.printHtml("HeaderDoctor.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='post'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Visited Patient Information</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry'><br>");
			User user=utility.getUser();
			HashMap<String, ArrayList<PatientAppointment>> patientAppointments = new HashMap<String, ArrayList<PatientAppointment>>();			
			try
		    {
				patientAppointments=MySqlDataStoreUtilities.selectVisitedAppointment(doctorName);
			}
			catch(Exception e)
			{}
			
			//pw.print("<h4 style='color:brown;'>"+patientAppointments+"</h4>");
			int size=0;
			for(Map.Entry<String, ArrayList<PatientAppointment>> entry : patientAppointments.entrySet())
			{
				for(PatientAppointment od:entry.getValue())	
				//if(od.getUserName().equals(user.getName()))
				size= size+1;
			}				
			if(size>0)
			{
				pw.print("<br><br><table  class='upcomingAppointmentDisplayTable'>");;
				pw.print("<tr><td><h4 style='color:brown;'>Appointment Id</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Patient Name</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Visited Day & Time</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Visited Date (YYYY-MM-DD)</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Appointment Status</h4></td></tr>");
				for(Map.Entry<String, ArrayList<PatientAppointment>> entry : patientAppointments.entrySet())
				{
					for(PatientAppointment oi:entry.getValue())
					//if(oi.getUserName().equals(user.getName())) 
					{
						pw.print("<tr>");			
						pw.print("<td>"+oi.getAppointmentId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getDoctorVisitingDay()+"</td><td>"+oi.getVisitingDate()+"</td><td>"+oi.getAppointmentStatus()+"</td>");
						pw.print("</tr>");							
					}					
				}					
				pw.print("</table>");
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>You have no visited patients till date...!</h3>");
			}	
			pw.print("<br><br></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{}		
	}
}
