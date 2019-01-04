import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitReviewDoctor")

public class SubmitReviewDoctor extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities utility= new Utilities(request, pw);
		storeReview(request, response);
	}
	protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try
                {
					response.setContentType("text/html");
					PrintWriter pw = response.getWriter();
					Utilities utility = new Utilities(request,pw);
					if(!utility.isLoggedin()){
						HttpSession session = request.getSession(true);				
						session.setAttribute("login_msg", "Please Login to add items to cart");
						response.sendRedirect("Login");
						return;
					}
					String docname=request.getParameter("doctorname");	
					System.out.println(docname);
					String docspeciality=request.getParameter("doctorspeciality");
					String reviewrating=request.getParameter("reviewrating");
					String reviewdate=request.getParameter("reviewdate");
					String patientid=request.getParameter("patientid");
					String patientage=request.getParameter("patientage");
					String patientgender=request.getParameter("patientgender");
					String reviewtext=request.getParameter("reviewtext");
					String message=utility.storeReview(docname,docspeciality,reviewrating,reviewdate,patientid,patientage,patientgender,reviewtext);
       		
					utility.printHtml("Header.html");
					utility.printHtml("LeftNavigationBar.html");pw.print("<div id='post'>");
					pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Review</a></h2></div>");
					pw.print("<br>");
					pw.print("<div id='entry'><br>");
					if(message.equals("Successfull"))
						pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Review for &nbsp"+docname+" Stored...!</h3>");
					else
						pw.print("<h3 style='margin-left:15px;font-family:Century Schoolbook;color:#e50000;'>Mongo Db is not up and running </h3>");
                
					pw.print("</div></div></div></div>");		
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