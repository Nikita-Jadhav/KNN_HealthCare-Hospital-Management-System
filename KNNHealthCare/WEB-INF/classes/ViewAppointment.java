import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/ViewAppointment")

public class ViewAppointment extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Appointments...!");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");		
		pw.print("<form name ='ViewAppointment' action='ViewAppointment' method='get'>");
		pw.print("<div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>View Appointment</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'>");	
		if(request.getParameter("Appointment")==null)
		{
			pw.print("<br><table align='center'><tr><td><h2 style='font-family:Georgia, Times New Roman, Times, serif;font-size:25px;color:black;'>Enter Appointment Id &nbsp&nbsp<input style='font-size:25px;border-radius:5px;' name='appointmentId' type='text'></h2></td></tr>");
			pw.print("<br><tr><td><input type='submit' name='Appointment' value='ViewAppointment' class='btn_appointment'></td></tr></table>");
		}
		HashMap<String, ArrayList<PatientAppointment>> patientAppointments = new HashMap<String, ArrayList<PatientAppointment>>();
		if(request.getParameter("Appointment")!=null && request.getParameter("Appointment").equals("ViewAppointment"))
		{
			if (request.getParameter("appointmentId") != null && request.getParameter("appointmentId") != "" )
			{	
				//int appointmentId=Integer.parseInt(request.getParameter("appointmentId"));
				String appointmentId=request.getParameter("appointmentId");
				pw.print("<input type='hidden' name='appointmentId' value='"+appointmentId+"'>");
				try
				{
					patientAppointments=MySqlDataStoreUtilities.selectAllAppointment();
				}
				catch(Exception e)
				{}
				int size=0;
				if(patientAppointments.get(appointmentId)!=null)
				{
				for(PatientAppointment od:patientAppointments.get(appointmentId))	
				if(od.getUserName().equals(username))
				size= patientAppointments.get(appointmentId).size();
				}
				if(size>0)
				{	
					pw.print("<br><br><table  class='appointmentDisplayTable'>");
					pw.print("<tr>");
					pw.print("<td><h4 style='color:brown;'>Select</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Appointment Id</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Patient Name</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Doctor Name</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Visiting Date (YYYY-MM-DD)</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Visiting Day & Time</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Appointment Made Date (YYYY-MM-DD)</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Appointment Status</h4></td>");
					pw.print("<td><h4 style='color:brown;'>Cancel Appointment</h4></td></tr>");
					for (PatientAppointment oi : patientAppointments.get(appointmentId)) 
					{
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
						pw.print("</tr>");
					}
					pw.print("</table>");
				}
				else
				{
					pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>You have not booked any appointment with this appointment id...!</h3>");
				}
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Please enter valid appointment id...!</h3>");	
			}
		}
		if(request.getParameter("Appointment")!=null && request.getParameter("Appointment").equals("CancelAppointment"))
		{
			if(request.getParameter("orderName") != null)
			{
				String orderName=request.getParameter("orderName");
				String appointmentId=request.getParameter("appointmentId");
				ArrayList<PatientAppointment> ListOrderPayment =new ArrayList<PatientAppointment>();
				try
				{		
					patientAppointments=MySqlDataStoreUtilities.selectAllAppointment();
				}	
				catch(Exception e)
				{
			
				}
				for (PatientAppointment oi : patientAppointments.get(appointmentId)) 
				{
					if(oi.getAppointmentId().equals(orderName) && oi.getUserName().equals(username))
					{
						MySqlDataStoreUtilities.deleteAppointment(appointmentId);								
						ListOrderPayment.add(oi);
						pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Your Appointment having id ("+oi.getAppointmentId()+") scheduled for day ("+oi.getDoctorVisitingDay()+") with ("+oi.getDoctorName()+") is Cancelled...!</h3>");	
					}
				}
				/*if(ListOrderPayment.size()==0)
				{
					pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Your Appointment can not be Cancelled as you can cancel 5 days before the appointment scheduled date...!</h3>");
				}*/
				patientAppointments.get(appointmentId).removeAll(ListOrderPayment);
				if(patientAppointments.get(appointmentId).size()==0)
				{
					patientAppointments.remove(appointmentId);
				}
			}
			else
			{
				pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Please select any appointment...!</h3>");
			}	
		}
		pw.print("<br><br></div></form>");		
		utility.printHtml("Footer.html");
	}
}


