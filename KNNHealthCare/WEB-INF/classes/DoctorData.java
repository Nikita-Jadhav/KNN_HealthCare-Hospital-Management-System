import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@WebServlet("/DoctorData")
public class DoctorData extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String userType=request.getSession(false).getAttribute("usertype").toString();		
			PrintWriter pw= response.getWriter();
			response.setContentType("text/html");
			if(userType.equals("Patient") || userType.equals("1"))
			{
				pw.println("<html>");
				pw.println("<body>");
				Utilities utility = new Utilities(request,pw);
				DoctorSearch data= (DoctorSearch)request.getAttribute("data");
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content_1'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+data.getSpeciality()+"</a></h2></div>");
				pw.print("<br>");
				pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
				pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				
				pw.print("<h3>"+data.getName()+"</h3>");
				pw.print("<strong> Visiting Charges: $"+data.getVisitingPrice()+"</strong><ul>");
				pw.print("<ul><li id='item_2'><img src='images/doctors/"+data.getDoctorImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='get' action='ViewProfile'>" +
					"<input type='hidden' name='categoryName' value='"+data.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+data.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='View Profile'></form></li>");
				pw.print("<br><li><form method='post' action='WriteReviewDoctor'>"+
					"<input type='hidden' name='categoryName' value='"+data.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+data.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btn_review'></form></li>");
				pw.print("<br><li><form method='post' action='ViewReviewDoctor'>"+
					"<input type='hidden' name='categoryName' value='"+data.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+data.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				pw.print("</tr>");
				pw.print("</table></div></div>");		
				utility.printHtml("Footer.html");
			}
			
			if(userType.equals("Doctor"))
			{
				pw.println("<html>");
				pw.println("<body>");
				UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
				DoctorSearch data= (DoctorSearch)request.getAttribute("data");
				utility.printHtml("HeaderDoctor.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content_1'>");
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+data.getSpeciality()+"</a></h2></div>");
				pw.print("<br>");
				pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
				pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				pw.print("<h3>"+data.getName()+"</h3>");
				pw.print("<strong> Visiting Charges: $"+data.getVisitingPrice()+"</strong><ul>");
				pw.print("<ul><li id='item_2'><img src='images/doctors/"+data.getDoctorImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='get' action='ViewProfile'>" +
					"<input type='hidden' name='categoryName' value='"+data.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+data.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='View Profile'></form></li>");
				pw.print("<br><li><form method='post' action='ViewReviewDoctor'>"+
					"<input type='hidden' name='categoryName' value='"+data.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+data.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				pw.print("</tr>");
				pw.print("</table></div></div>");		
				utility.printHtml("Footer.html");
			}
		}
		catch(Exception e)
		{}
	}
	
	public void destroy()
	{
      // do nothing.
	}
}