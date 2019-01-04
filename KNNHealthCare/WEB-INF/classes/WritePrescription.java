import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/WritePrescription")

public class WritePrescription extends HttpServlet {
	String msg="";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesDoctor utility = new UtilitiesDoctor(request, pw);
		String action = request.getParameter("button");
		System.out.print(action);
		String patientName = request.getParameter("patientName");
		String natureOfIllness   = request.getParameter("natureOfIllness");
		String medicinePrescription = request.getParameter("medicinePrescription"); 
		String additinalNotes = request.getParameter("additinalNotes");
		String prescriptionDate = request.getParameter("prescriptionDate");
		String doctorName = "Dr. "+request.getSession(false).getAttribute("username").toString();
		try
		{
				msg = MySqlDataStoreUtilities.addPrescription(doctorName,patientName,natureOfIllness,medicinePrescription,additinalNotes,prescriptionDate);
		}
		catch(Exception e)
		{ 
				msg = "Unable to write prescription...!";
		}
		displayWritePrescription(request, response, true);
	}

	protected void displayWritePrescription(HttpServletRequest request, HttpServletResponse response,boolean error) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
		utility.printHtml("HeaderDoctor.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Write Prescription</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'><br>");
		if (error)
			pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>"+msg+"</h3>");
		pw.print("<form method='post' action='WritePrescription'>"
				+ "<table style='width:100%'><tr><td style='width:650px'>"
				+"<br><h3 style='margin-left:15px;color:black;margin-top:20px;'>Patient Name <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;border-radius:5px;' type='text' name='patientName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;margin-top:20px;'>Nature of illness<font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;border-radius:5px;' type='text' name='natureOfIllness' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"				
				+"<br><h3 style='margin-left:15px;color:black;margin-top:-201px;'>Medicine Prescription <font color='red'>*</font></h3></td><td><textarea style='margin-left:-260px;margin-top:20px;border-radius:5px;width:986px;height:381px;' rows='10' cols='100' name='medicinePrescription' required></textarea>"
				+ "</td></tr><tr><td>"
				+"<br><h3 style='margin-left:15px;color:black;margin-top:-77px;'>Additional Notes </h3></td><td><textarea style='margin-left:-260px;margin-top:20px;border-radius:5px;width:554px;height:138px;' rows='10' cols='50' name='additinalNotes'></textarea>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;margin-top:20px;'>Prescription Date <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;border-radius:5px;' type='date' name='prescriptionDate' value='' class='input' required></input>"
				+ "</td></tr></table>"
				+ "<input type='submit' class='btn_prescription' name='button' value='Submit'></input>"
				+ "</form>" + "<br><br></div>");
		utility.printHtml("Footer.html");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);		
		displayWritePrescription(request, response, false);
	}
}
