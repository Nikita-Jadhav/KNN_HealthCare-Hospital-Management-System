import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Authenticator;

@WebServlet("/BookAppointment")

public class BookAppointment extends HttpServlet {
	HttpSession session; 
	String error_msg;
	String emailId=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to book an appointment...!");
			response.sendRedirect("Login");
			return;
		}
		String doctorSpeciality=request.getParameter("ddlSpecialty");
		String doctoreName=request.getParameter("ddlDoctor");
		String doctorVistingDay=request.getParameter("ddlDay");
		String appointmentDate=request.getParameter("appointmentDate");
		String userName=request.getSession(false).getAttribute("username").toString();
		int id=0;
		if(doctorVistingDay.equals("0") || doctoreName.equals("-1") || doctorSpeciality.equals("-1"))
		{
			error_msg="Please select appropriate Speciality, Doctor, Visiting Day and Date...!";
			displayAppointmentInformation(request,response,pw,error_msg,id,userName,doctorVistingDay,doctoreName,doctorSpeciality,appointmentDate);			
		}
		else
		{
			id=utility.getAppointmentId();
			Calendar now = Calendar.getInstance();
			String date=now.get(Calendar.YEAR) + "-"+ (now.get(Calendar.MONTH) +1) + "-"+ now.get(Calendar.DATE);
			error_msg=utility.storeAppointmentInformation(userName,doctorSpeciality,doctoreName,doctorVistingDay,appointmentDate,id,date);
			displayAppointmentInformation(request,response,pw,error_msg,id,userName,doctorVistingDay,doctoreName,doctorSpeciality,appointmentDate);
		}
	}
	
	protected void displayAppointmentInformation(HttpServletRequest request,HttpServletResponse response, PrintWriter pw, String error,int id,String userName,String doctorVistingDay,String doctoreName,String doctorSpeciality,String appointmentDate)throws ServletException, IOException
	{
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Appointment Confirmation</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'>");
		if(doctorVistingDay.equals("0") || doctoreName.equals("-1") || doctorSpeciality.equals("-1"))
		{
			pw.print("<h3 style='margin-left:416px;font-family:Century Schoolbook;color:#e50000;'>"+error_msg+"</h3>");
		}
		else
		{
		pw.print("<br><img src='images/site/book.png' alt='' style='height:200px;width:200px;margin-left:660px;'/>");
		pw.print("<br><h2 style='font-family:Georgia, Times New Roman, Times, serif;margin-left:621px;font-size:35px;color:black;'>Thank you "+userName+",</h2>");
		pw.print("<h3 style='margin-top:-5px;padding:0px;margin-left:581px;color:#008000;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Your appointment is confirmed...!</h3><br>");
		pw.print("<br><div id='apointmentCofirmationDetails'>");
		pw.print("<table>");
		pw.print("<tr><td><h3 style='margin-left:-10px;padding:0px 0px 0px 80px;color:#842121;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Appointment Id</h3></td>");
		pw.print("<td><h3 style='margin-left:-57px;padding:0px 0px 0px 80px;color:#842121;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Doctor Name</h3></td>");
		pw.print("<td><h3 style='margin-left:-10px;padding:0px 0px 0px 0px;color:#842121;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Appointment Date & Time</h3></td></tr>");
		pw.print("<tr><td><img src='images/site/2.png' alt='' style='height:90px;width:338px;margin-left:10px;margin-top:-45px;padding:0px;'/></td>");
		pw.print("<td><img src='images/site/2.png' alt='' style='height:90px;width:338px;margin-left:-35px;margin-top:-45px;padding:0px;'/></td>");
		pw.print("<td><img src='images/site/2.png' alt='' style='height:90px;width:445px;margin-left:-89px;margin-top:-45px;padding:0px;'/></td></tr>");
		pw.print("<tr><td><h3 style='padding:0px 0px 0px 80px;margin:-35px 0px 0px 4px;color:black;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;'>"+id+"</h3></td>");
		pw.print("<td><h3 style='padding:0px 0px 0px 80px;margin:-35px 0px 0px -48px;color:black;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;'>"+doctoreName+"</h3></td>");
		pw.print("<td><h3 style='padding:0px 0px 0px 80px;margin:-35px 0px 0px -81px;color:black;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;'>"+doctorVistingDay+"</h3></td></tr>");
		pw.print("</table></div>");
		}
		//pw.print("<br><br></div>");
		String tempUserName=request.getSession(false).getAttribute("username").toString();
					try
					{	
						emailId=MySqlDataStoreUtilities.getEmailId(tempUserName);
					}
					catch(Exception e)
					{
						System.out.println("Inside exception...!");
					}
					pw.print("<br><h3 style='margin-left:500px;text-decoration: none;'> An email has been sent at your registered email id.</h3>");
					pw.print("<br><br></div>");
					// GMail user name (just the part before "@gmail.com")
					String USER_NAME = "niharika.morishetti22";
					String PASSWORD = "Manhattan123"; // GMail password
					String RECIPIENT = emailId;
					String from = USER_NAME;
					String pass = PASSWORD;
					String[] to = { RECIPIENT }; // list of recipient email addresses 587/465
					String subject = "Appointment Confirmation from KNNHealthCare";
					String body = "Your appointment with "+doctoreName+" is confirmed.\nYour Appointment ID is "+id+".\nThe Appointment Date and Time is "+doctorVistingDay+".\n\n\n"+"Best Regards,\n"+"KNNHealthCare";
					Properties props = System.getProperties();
					String host = "smtp.gmail.com";
					props.put("mail.smtp.host", host);
					props.put("mail.smtp.user", from);
					props.put("mail.smtp.password", pass);
					props.put("mail.smtp.port", "465");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.socketFactory.port","465");
					props.put("mail.smtp.socketFactory.fallback","false");
					Session session = Session.getDefaultInstance(props,null);         
					MimeMessage message = new MimeMessage(session);
					try
					{
						message.setFrom(new InternetAddress(from));
						InternetAddress[] toAddress = new InternetAddress[to.length];
						for( int i = 0; i < to.length; i++ )
						{
							toAddress[i] = new InternetAddress(to[i]);
						}
						for( int i = 0; i < toAddress.length; i++)
						{
							message.addRecipient(Message.RecipientType.TO, toAddress[i]);
						}
						message.setSubject(subject);
						message.setText(body);
						Transport transport = session.getTransport("smtp");
						transport.connect(host, from, pass);
						transport.sendMessage(message, message.getAllRecipients());
						transport.close();
					}
					catch (AddressException ae)
					{
						ae.printStackTrace();
					}
					catch (MessagingException me)
					{
						me.printStackTrace();
					}
		utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);		
	}
}
