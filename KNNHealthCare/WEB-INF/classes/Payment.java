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

@WebServlet("/Payment")

public class Payment extends HttpServlet
{
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
				session.setAttribute("login_msg", "Please Login to Pay");
				response.sendRedirect("Login");
				return;
			}
			String userAddress=request.getParameter("address");
			String creditCardNo=request.getParameter("ccnumber");
			String creditCardName=request.getParameter("ccname");
			String expiry=request.getParameter("expiry");
			String cvv=request.getParameter("cvv");
			String userName=request.getSession(false).getAttribute("username").toString();
			String dbconn=MySqlDataStoreUtilities.getConnection();
			if(!dbconn.equals("Successful"))
			{
				error_msg="MySql server is not up and running...!";
			}
			if(!userAddress.isEmpty() && !creditCardNo.isEmpty() && !creditCardName.isEmpty() && !expiry.isEmpty() && !cvv.isEmpty())
			{			
				int orderId=utility.getOrderPaymentSize()+1;
				//setting current date.	
				OrderPayment op2=new OrderPayment();
				Calendar now1 = Calendar.getInstance();	   
				now1.add(Calendar.DATE,15);
				String s=(now1.get(Calendar.MONTH) +1) + "/"+ now1.get(Calendar.DATE) + "/" + now1.get(Calendar.YEAR);
				op2.setDeliveryDate(s);			
				Calendar currentDate = Calendar.getInstance();
				//currentDate.add(Calendar.MONTH,1);
				String s2=(currentDate.get(Calendar.MONTH) +1) + "/"+ currentDate.get(Calendar.DATE) + "/" + currentDate.get(Calendar.YEAR);
				op2.setOrderDate(s2);
				String orderStatus = "In Progress";
				for (OrderItem oi : utility.getCustomerOrders())
				{				
					double orderPayablePrice=0.0;
					double calculation=0.0;
					calculation = oi.getDiscount() + oi.getRebate();
					orderPayablePrice = oi.getPrice() - calculation;
					utility.storePayment(orderId,oi.getName(),oi.getPrice(),oi.getDiscount(),oi.getRebate(),orderPayablePrice,userAddress,creditCardNo,creditCardName,expiry,cvv,op2.getOrderDate(),op2.getDeliveryDate(),orderStatus,oi.getQuantity());
				}
				utility.truncateData();
				OrdersHashMap.orders.remove(utility.username());	
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='post'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Order Confirmation</a></h2></div>");
				pw.print("<br>");
				if(error_msg==null)
				{
					pw.print("<div id='entry'>");				
					pw.print("<br><img src='images/site/edit_1.gif' alt='' style='height:200px;width:200px;margin-left:660px;'/>");
					pw.print("<br><h2 style='font-family:Georgia, Times New Roman, Times, serif;margin-left:621px;font-size:35px;color:black;'>Thank you "+userName+",</h2>");
					pw.print("<h3 style='margin-top:-5px;padding:0px;margin-left:485px;color:black;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Sit back and relax, your order's is confirmed & on its way!</h3><br>");
					pw.print("<img src='images/site/2.png' alt='' style='height:1px;width:800px;margin-left:375px;margin-top:-45px;padding:0px;'/>");
					pw.print("<br><div id='orderCofirmationDetails'>");
					pw.print("<table>");
					pw.print("<tr><td><h3 style='padding:0px 0px 0px 80px;color:#842121;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Order/Confirmation No</h3></td>");
					pw.print("<td><h3 style='padding:0px 0px 0px 0px;color:#842121;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 23px;'>Expected Delivery Date</h3></td></tr>");
					pw.print("<tr><td><img src='images/site/2.png' alt='' style='height:90px;width:450px;margin-left:5px;margin-top:-45px;padding:0px;'/></td>");
					pw.print("<td><img src='images/site/2.png' alt='' style='height:90px;width:450px;margin-left:-80px;margin-top:-45px;padding:0px;'/></td></tr>");
					pw.print("<tr><td><h3 style='padding:0px 0px 0px 80px;margin:-35px 0px 0px 15px;color:black;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;'>"+(orderId)+"</h3></td>");
					//Delivery Date logic.
					Calendar now = Calendar.getInstance();		   
					now.add(Calendar.DATE,15);
					String s1=(now.get(Calendar.MONTH) +1) + "/"+ now.get(Calendar.DATE) + "/" + now.get(Calendar.YEAR);
					pw.print("<td><h3 style='padding:0px 0px 0px 80px;margin:-35px 0px 0px -67px;color:black;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;'>"+s1+"</h3></td></tr>");
					pw.print("</table></div>");
					pw.print("<br><br><h3 style='padding:0px;margin:-20px 0px 0px 337px;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;color:red;'>You can cancel an order at a later time, though it must be 3 business days before the delivery date.</h3>");
					pw.print("<h3 style='padding:0px;margin:10px 0px 0px 581px;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 20px;color:black;'>Questions? Call us at +1 (666)-666-6666</h3>");
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
					String subject = "Payment Confirmation from KNNHealthCare";
					String body = "Your payment has been received & your order is confirmed.\nYour Order/Confirmation No. is "+orderId+".\nThe Expected Delivery date is "+s1+".\n\n\n"+"Best Regards,\n"+"KNNHealthCare";
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
			}
			else
			{
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='post'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Order Confirmation</a></h2></div>");
				pw.print("<br>");
				pw.print("<div id='entry'>");
				pw.print("<h2 style='margin-left:15px; font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 25px;color:red;'>Please enter valid credit card name and customer address and creditcard number...!");
				pw.print("&nbsp&nbsp");		
				pw.print("</h2></div>");		
				utility.printHtml("Footer.html");
			}
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);		
	}
}
