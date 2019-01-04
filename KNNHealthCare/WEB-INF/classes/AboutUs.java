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

@WebServlet("/AboutUs")

public class AboutUs extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String userType=request.getSession(false).getAttribute("usertype").toString();
		if(userType.equals("Patient") || userType.equals("1"))
		{
			Utilities utility = new Utilities(request,pw);
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>About Us</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry_1'><br>");
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>The KNN HealthCare is one of the oldest tertiary care, multi-specialty Trust hospitals of the country. In the late 60s, when the establishment of large private hospitals was not common, the institution was conceptualized & endowed to the city of Chicago by Seth Lokoomal Chanrai. Seth Chanrai came from a family of philanthropists who had businesses in many countries. The family had already supported many small & large projects. Seth Lokoomal entrusted Dr. Shantilal Mehta the task of establishing an ultra-modern centrally located medical centre where people of all races and creeds could receive the benefits of advanced health care. Jaslok Hospital is situated at Dr. G Deshmukh Marg, Peddar Road which is a main artery of Chicago and overlooking the Michigan Lake. Seth Chanrai’s vision was implemented and brought to reality largely by his Brother-in-law Dada Mathradas Assomull. The hospital was inaugurated on 6th July 1973 by the erstwhile President Mr. Richard Nixon.</h4><br>");	
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>KNN HealthCare also attracts a significant number of patients from out of country. The institution now has around 7 established specialities and these are growing steadily. The departments are equipped with state of the art equipment and manned by trained & dedicated staff, many of whom now run training programs.</h4><br>");		
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>Well-developed specialties and services available at KNN HealthCare are Department of Cardiologist, Dermatologist, Hematologist, Neonatologist, Nephrologist, Neurologist and Physician.</h4>");
			pw.print("<br><br></div>");
			utility.printHtml("Footer.html");
		}
		if(userType.equals("Doctor"))
		{
			UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
			utility.printHtml("HeaderDoctor.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>About Us</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry_1'><br>");
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>The KNN HealthCare is one of the oldest tertiary care, multi-specialty Trust hospitals of the country. In the late 60s, when the establishment of large private hospitals was not common, the institution was conceptualized & endowed to the city of Chicago by Seth Lokoomal Chanrai. Seth Chanrai came from a family of philanthropists who had businesses in many countries. The family had already supported many small & large projects. Seth Lokoomal entrusted Dr. Shantilal Mehta the task of establishing an ultra-modern centrally located medical centre where people of all races and creeds could receive the benefits of advanced health care. Jaslok Hospital is situated at Dr. G Deshmukh Marg, Peddar Road which is a main artery of Chicago and overlooking the Michigan Lake. Seth Chanrai’s vision was implemented and brought to reality largely by his Brother-in-law Dada Mathradas Assomull. The hospital was inaugurated on 6th July 1973 by the erstwhile President Mr. Richard Nixon.</h4><br>");	
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>KNN HealthCare also attracts a significant number of patients from out of country. The institution now has around 7 established specialities and these are growing steadily. The departments are equipped with state of the art equipment and manned by trained & dedicated staff, many of whom now run training programs.</h4><br>");		
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>Well-developed specialties and services available at KNN HealthCare are Department of Cardiologist, Dermatologist, Hematologist, Neonatologist, Nephrologist, Neurologist and Physician.</h4>");
			pw.print("<br><br></div>");
			utility.printHtml("Footer.html");
		}
		if(userType.equals("Medicine Manager"))
		{
			UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);
			utility.printHtml("HeaderMedicineManager.html");
			utility.printHtml("LeftNavigationBarMedicineManager.html");
			pw.print("<div id='content_1' style='margin-top:-737px;'>");
			pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>About Us</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry_1' style='margin-top:-678px;'><br>");
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>The KNN HealthCare is one of the oldest tertiary care, multi-specialty Trust hospitals of the country. In the late 60s, when the establishment of large private hospitals was not common, the institution was conceptualized & endowed to the city of Chicago by Seth Lokoomal Chanrai. Seth Chanrai came from a family of philanthropists who had businesses in many countries. The family had already supported many small & large projects. Seth Lokoomal entrusted Dr. Shantilal Mehta the task of establishing an ultra-modern centrally located medical centre where people of all races and creeds could receive the benefits of advanced health care. Jaslok Hospital is situated at Dr. G Deshmukh Marg, Peddar Road which is a main artery of Chicago and overlooking the Michigan Lake. Seth Chanrai’s vision was implemented and brought to reality largely by his Brother-in-law Dada Mathradas Assomull. The hospital was inaugurated on 6th July 1973 by the erstwhile President Mr. Richard Nixon.</h4><br>");	
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>KNN HealthCare also attracts a significant number of patients from out of country. The institution now has around 7 established specialities and these are growing steadily. The departments are equipped with state of the art equipment and manned by trained & dedicated staff, many of whom now run training programs.</h4><br>");		
			pw.print("<h4 style='margin-left:15px;margin-right:15px;font-size:23px;font-family:Calibri;font-weight:normal;'>Well-developed specialties and services available at KNN HealthCare are Department of Cardiologist, Dermatologist, Hematologist, Neonatologist, Nephrologist, Neurologist and Physician.</h4>");
			pw.print("<br><br></div>");
			utility.printHtml("Footer.html");
		}
	}	
}
