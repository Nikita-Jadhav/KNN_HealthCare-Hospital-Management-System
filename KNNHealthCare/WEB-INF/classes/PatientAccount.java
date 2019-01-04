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

@WebServlet("/PatientAccount")

public class PatientAccount extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayPatientAccount(request, response);
	}
	
	protected void displayPatientAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
         {  
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to view appointments...!");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='post'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Patient Appointment</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry'><br>");
			User user=utility.getUser();
			HashMap<String, ArrayList<PatientAppointment>> patientAppointments = new HashMap<String, ArrayList<PatientAppointment>>();			
			try
		    {
				patientAppointments=MySqlDataStoreUtilities.selectAllAppointment();
			}
			catch(Exception e)
			{}
			int size=0;
			for(Map.Entry<String, ArrayList<PatientAppointment>> entry : patientAppointments.entrySet())
			{
				for(PatientAppointment od:entry.getValue())	
				if(od.getUserName().equals(user.getName()))
				size= size+1;
			}				
			if(size>0)
			{
				pw.print("<br><br><table  class='appointmentDisplayTable'>");
				pw.print("<tr><td><h4 style='color:brown;'>Select</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Appointment Id</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Patient Name</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Doctor Name</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Visiting Date (YYYY-MM-DD)</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Visiting Day & Time</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Appointment Made Date (YYYY-MM-DD)</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Appointment Status</h4></td>");
				pw.print("<td><h4 style='color:brown;'>Cancel Appointment</h4></td></tr>");
				for(Map.Entry<String, ArrayList<PatientAppointment>> entry : patientAppointments.entrySet())
				{
					for(PatientAppointment oi:entry.getValue())	
					if(oi.getUserName().equals(user.getName())) 
					{
						pw.print("<form method='get' action='ViewAppointment'>");
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='orderName' value='"+oi.getAppointmentId()+"'></td>");
						pw.print("<td>"+oi.getAppointmentId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getDoctorName()+"</td><td>"+oi.getVisitingDate()+"</td><td>"+oi.getDoctorVisitingDay()+"</td><td>"+oi.getFixAppointmentDate()+"</td><td>"+oi.getAppointmentStatus()+"</td>");
						String temp=oi.getAppointmentStatus();
						if(temp.equals("Visited"))
						{						
							pw.print("<td><input type='submit' name='Appointment' value='CancelAppointment' class='btnbuy_1' disabled></td>");
						}
						else
						{
							pw.print("<td><input type='submit' name='Appointment' value='CancelAppointment' class='btnbuy_1'></td>");
						}
						pw.print("<td><input type='hidden' name='appointmentId' value='"+oi.getAppointmentId()+"'></td>");
						pw.print("</tr>");
						pw.print("</form>");							
					}					
				}					
				pw.print("</table>");
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>You have no appointments scheduled...!</h3>");
			}	
			pw.print("<br><br></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{}		
	}
}
