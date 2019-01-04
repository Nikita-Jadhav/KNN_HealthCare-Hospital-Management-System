import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReviewDoctor")

public class ViewReviewDoctor extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    try
        {           
            response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
			if(!utility.isLoggedin()){
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to view Review...!");
				response.sendRedirect("Login");
				return;
			}
			String doctorName=request.getParameter("doctorName");		 
			HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
			String userName = "";
			String reviewRating = "";
			String reviewDate;
			String reviewText = "";
            utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<form name ='WriteReview' action='SubmitReviewDoctor' method='post'>");
            pw.print("<div id='content_1'><h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>Doctor Reviews</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry_1'><br>");
			if(hm==null)
			{
				pw.println("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Mongo Db server is not up and running...!</h3>");
			}
			else
			{
                if(!hm.containsKey(doctorName))
				{
					pw.println("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>There are no reviews for this doctor...!</h3>");
				}
				else
				{
					int i=1;
					for (Review r : hm.get(doctorName)) 
					{		
						pw.print("<table class='reviewDisplayTable'>");
						pw.print("<tr>");
						pw.print("<h4 style='color:brown;margin-left:25px;'>Review Number: "+i+"</h4>");
						pw.print("</tr>");
						pw.print("<tr>");
						pw.print("<td><h4 style='color:brown;'>Doctor Name: </h4></td>");
						doctorName = r.getdoctorName();
						pw.print("<td><h4 style='color:black;'>" +doctorName+ "</h4></td>");
						pw.print("</tr>");
						pw.print("<tr>");
						pw.print("<td><h4 style='color:brown;'>Patient name: </h4></td>");
						userName = r.getUserName();
						pw.print("<td><h4 style='color:black;'>"+userName+ "</h4></td>");
						pw.print("</tr>");
						pw.print("<tr>");
						pw.println("<td><h4 style='color:brown;'>Review Rating: </h4></td>");
						reviewRating = r.getReviewRating().toString();
						pw.print("<td><h4 style='color:black;'>" +reviewRating+ "</h4></td>");
						pw.print("</tr>");
						pw.print("<tr>");
						pw.print("<td><h4 style='color:brown;'>Review Date: </h4></td>");
						reviewDate = r.getReviewDate().toString();
						pw.print("<td><h4 style='color:black;'>" +reviewDate+ "</h4></td>");
						pw.print("</tr>");			
						pw.print("<tr>");
						pw.print("<td><h4 style='color:brown;'>Review Text: </h4></td>");
						reviewText = r.getReviewText();
						pw.print("<td><h4 style='color:black;'>" +reviewText+ "</h4></td>");
						pw.print("</tr>");
						pw.println("</table>");
						pw.println("<br>");
						i++;
					}					
							
				}
			}	       
            pw.print("<br><br></div>");			
			utility.printHtml("Footer.html");
	                     	
        }
        catch(Exception e)
		{
            System.out.println(e.getMessage());
		}  			

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}
