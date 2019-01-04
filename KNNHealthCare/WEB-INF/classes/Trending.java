import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

    ArrayList <Mostpatientsvisited> mostpatientsvisited = new ArrayList <Mostpatientsvisited> ();
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		mostpatientsvisited = MongoDBDataStoreUtilities.mostpatientsvisited();
		bestrated = MongoDBDataStoreUtilities.topDoctors();
		String name = "Trending";
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='post'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Trending</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'><br>");		
		pw.print("<a style='margin-left:15px;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 26px;color: #000000;'>Top 5 Best Doctors</a>");
		pw.print("</h2><div class='entry_1'><table id='bestsellerTrending'>");
		Iterator itr2 = bestrated.iterator();
		pw.print("<tr>");
		pw.println("<td border: 1px >");
		pw.println("<h3 style='color:brown;'>Doctor Name</h3>");
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println("<h3 style='color:brown;'>Count</h3>");
		pw.println("</td>");
		pw.println("</tr>");
		while(itr2.hasNext()) {
        Bestrating best = (Bestrating)itr2.next();
 		pw.print("<tr>");
		pw.print("<td>");
		pw.print("<h4>"+best.getDoctorname()+"</h4>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<h4 style='margin-left:5px;'>"+best.getRating()+"</h4>");
		pw.print("</td>");
		pw.print("</tr>");
        }
		pw.print("</table></div>");
		pw.print("<hr style='background-color: #ffffff;'>");
		pw.print("<h2 class='title_1 meta_1'>");
		pw.print("<a style='margin-left:15px;font-family:Georgia, Times New Roman, Times, serif;text-decoration: none;font-size: 26px;color: #000000;'>Top 5 specialties where maximum number of patients visited</a>");
		pw.print("</h2><div class='entry_1'><table id='bestsellerTrending'>");
		Iterator itr1 = mostpatientsvisited.iterator();
		pw.print("<tr>");
		pw.println("<td border: 1px >");
		pw.println("<h3 style='color:brown;'>Doctor Specialty</h3>");
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println("<h3 style='color:brown;margin-left:16px;'>Count</h3>");
		pw.println("</td>");
		pw.println("</tr>");
         while(itr1.hasNext()) {
         Mostpatientsvisited mostSpecialty = (Mostpatientsvisited)itr1.next();
 		pw.print("<tr>");
		pw.println("<td border: 1px >");
		pw.println("<h4>"+mostSpecialty.getDoctorspecialty()+"</h4>");
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println("<h4 style='margin-left:21px;'>"+mostSpecialty.getCount()+"</h4>");
		pw.println("</td>");
		pw.println("</tr>");
		}
		pw.print("</table></div>");
		pw.print("<br><br></div>");
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
