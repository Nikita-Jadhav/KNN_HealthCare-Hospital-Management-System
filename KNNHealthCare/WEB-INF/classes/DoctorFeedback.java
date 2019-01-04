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

@WebServlet("/DoctorFeedback")

public class DoctorFeedback extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,DoctorSpeciality> allDoctorSpecialist = new HashMap<String,DoctorSpeciality> ();
		try{
		     allDoctorSpecialist = MySqlDataStoreUtilities.getDoctorspecialities();
		}
		catch(Exception e)
		{
			
		}		
		String name = null;
		String specialityCategory = request.getParameter("doctorSpeciality");        
		HashMap<String,DoctorSpeciality> hm = new HashMap<String,DoctorSpeciality>();
		if(specialityCategory==null){
			hm.putAll(allDoctorSpecialist);
			name = "";
		}
		String userType=request.getSession(false).getAttribute("usertype").toString();
		if(userType.equals("Patient") || userType.equals("1"))
		{
			Utilities utility = new Utilities(request,pw);
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			if(name != null && !name.isEmpty())
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+name+"s</a></h2></div>");
			}
			else
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'> Doctors </a></h2></div>");
			}
			pw.print("<br>");
			pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
			int i = 1; int size= hm.size();
			for(Map.Entry<String,DoctorSpeciality> entry : hm.entrySet())
			{
				DoctorSpeciality doctorspeciality = entry.getValue();
				if(i%4==1) pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				pw.print("<h3> "+doctorspeciality.getName()+"</h3>");
				pw.print("<strong> Visiting Charges: $"+doctorspeciality.getVisitingPrice()+"</strong>");
				pw.print("<ul><li id='item_2'><img src='images/doctors/"+doctorspeciality.getDoctorImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='post' action='ViewReviewDoctor'>"+
					"<input type='hidden' name='categoryName' value='"+doctorspeciality.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+doctorspeciality.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				if(i%4==0 || i == size) pw.print("</tr>");
				i++;
			}	
			pw.print("</table></div></div>");
			utility.printHtml("Footer.html");
		}
		if(userType.equals("Doctor"))
		{
			UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
			utility.printHtml("HeaderDoctor.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			if(name != null && !name.isEmpty())
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+name+"s</a></h2></div>");
			}
			else
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'> Doctors </a></h2></div>");
			}
			pw.print("<br>");
			pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
			int i = 1; int size= hm.size();
			for(Map.Entry<String,DoctorSpeciality> entry : hm.entrySet())
			{
				DoctorSpeciality doctorspeciality = entry.getValue();
				if(i%4==1) pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				pw.print("<h3> "+doctorspeciality.getName()+"</h3>");
				pw.print("<strong> Visiting Charges: $"+doctorspeciality.getVisitingPrice()+"</strong>");
				pw.print("<ul><li id='item_2'><img src='images/doctors/"+doctorspeciality.getDoctorImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='post' action='ViewReviewDoctor'>"+
					"<input type='hidden' name='categoryName' value='"+doctorspeciality.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+doctorspeciality.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				if(i%4==0 || i == size) pw.print("</tr>");
				i++;
			}	
			pw.print("</table></div></div>");
			utility.printHtml("Footer.html");
		}
	}	
}
