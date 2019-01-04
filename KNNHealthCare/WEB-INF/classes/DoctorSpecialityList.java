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

@WebServlet("/DoctorSpecialityList")

public class DoctorSpecialityList extends HttpServlet {
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
		else
		{
		   if(specialityCategory.equals("Cardiologist"))
		   {
			for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet()) 
			 {
				if(entry.getValue().getSpeciality().equals("Cardiologist"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Cardiologist";
		   }
		   else if(specialityCategory.equals("Dermatologist"))
		    {
			for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet())
				{
				 if(entry.getValue().getSpeciality().equals("Dermatologist"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Dermatologist";
			}
			else if(specialityCategory.equals("Hematologist"))
			{
				for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet())
				{
				 if(entry.getValue().getSpeciality().equals("Hematologist"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Hematologist";
			}
			else if(specialityCategory.equals("Neonatologist"))
			{
				for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet())
				{
				 if(entry.getValue().getSpeciality().equals("Neonatologist"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Neonatologist";
			}
			else if(specialityCategory.equals("Nephrologist"))
			{
				
				for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet())
				{
				 if(entry.getValue().getSpeciality().equals("Nephrologist"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Nephrologist";
			}
			else if(specialityCategory.equals("Neurologist"))
			{
				
				for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet())
				{
				 if(entry.getValue().getSpeciality().equals("Neurologist"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Neurologist";
			}
			else if(specialityCategory.equals("Physician"))
			{
				
				for(Map.Entry<String,DoctorSpeciality> entry : allDoctorSpecialist.entrySet())
				{
				 if(entry.getValue().getSpeciality().equals("Physician"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Physician";
			}
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
				pw.print("<br><li><form method='get' action='ViewProfile'>" +
					"<input type='hidden' name='categoryName' value='"+name+"'>"+
					"<input type='hidden' name='doctorName' value='"+doctorspeciality.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='View Profile'></form></li>");
				pw.print("<br><li><form method='post' action='WriteReviewDoctor'>"+
					"<input type='hidden' name='categoryName' value='"+doctorspeciality.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+doctorspeciality.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btn_review'></form></li>");
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
				pw.print("<td><div id='shop_item_1'>");
				pw.print("<h3> "+doctorspeciality.getName()+"</h3>");
				pw.print("<strong> Visiting Charges: $"+doctorspeciality.getVisitingPrice()+"</strong>");
				pw.print("<ul><li id='item_1'><img src='images/doctors/"+doctorspeciality.getDoctorImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='get' action='ViewProfile'>" +
					"<input type='hidden' name='categoryName' value='"+doctorspeciality.getSpeciality()+"'>"+
					"<input type='hidden' name='doctorName' value='"+doctorspeciality.getName()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='View Profile'></form></li>");
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
