import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/WriteReviewMedicine")
	
public class WriteReviewMedicine extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities utility= new Utilities(request, pw);
		reviewM(request, response);
	}
	
	protected void reviewM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    try
        {
            response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to Write a Review...!");
				response.sendRedirect("Login");
				return;
			}
            String productname=request.getParameter("medicineName");
            String producttype=request.getParameter("type");
			String productmaker=request.getParameter("manufacturer");
			String productprice=request.getParameter("price");			      
            utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<form name ='WriteReviewMedicine' action='SubmitReviewMedicine' method='post'>");
			pw.print("<div id='content_1'><h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>Write Medicine/Accessory Reviews</a></h2></div>");
			pw.print("<br>");
			pw.print("<div id='entry_1'><br>");
            pw.print("<table class='reviewDisplayTable'>");
			pw.print("<tr><td><h4 style='color:brown;'>Medicine Name: </h4></td><td>");
			pw.print("<h4 style='color:black;'>"+productname+"</h4>");
			pw.print("<input type='hidden' name='productname' value='"+productname+"'>");				
			pw.print("</td></tr>");
			pw.print("<tr><td><h4 style='color:brown;'>Medicine Category: </h4></td><td>");
			pw.print("<h4 style='color:black;'>"+producttype+"</h4>");
			pw.print("<input type='hidden' name='producttype' value='"+producttype+"'>");
			pw.print("</td></tr>");
			pw.print("<tr><td><h4 style='color:brown;'>Medicine Price ($): </h4></td><td>");
			pw.print("<h4 style='color:black;'>"+productprice+"</h4>");
			pw.print("<input type='hidden' name='productprice' value='"+productprice+"'>");
			pw.print("</td></tr>");
			pw.print("<tr><td><h4 style='color:brown;'>Retailer Name: </h4></td><td>");
			pw.print("<h4 style='color:black;'>KNNHealthCare</h4>");
			pw.print("<tr><td><h4 style='color:brown;'>Manufacturer Name: </h4></td><td>");
			pw.print("<h4 style='color:black;'>"+productmaker+"</h4>");
			pw.print("<input type='hidden' name='productmaker' value='"+productmaker+"'>");
            pw.print("</table><br>");
  			pw.print("<table style='margin-left:25px;'>");
			pw.print("<tr><td><h4 style='color:black;'>Review Rating</h4></td>");
			pw.print("<td>");
			pw.print("<select name='reviewrating' style='width:250px;height:35px;border-radius:5px;margin-left:30px;'>");
			pw.print("<option value='1' selected>1</option>");
			pw.print("<option value='2'>2</option>");
			pw.print("<option value='3'>3</option>");
			pw.print("<option value='4'>4</option>");
			pw.print("<option value='5'>5</option>");  
			pw.print("</td></tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>Manufacturer Rebate</h4></td>");
			pw.print("<td>");
			pw.print("<select name='manufacturerrebate' style='width:250px;height:35px;border-radius:5px;margin-left:30px;'>");
			pw.print("<option value='1' selected>Yes</option>");
			pw.print("<option value='2'>No</option>");
			pw.print("</td></tr>");		
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>Retailer Zip Code:</h4></td>");
			pw.print("<td> <input style='margin-top:20px;width:250px;height:35px;border-radius:5px;margin-left:30px;' type='text' name='zipcode'> </td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>Retailer City:</h4></td>");
			pw.print("<td> <input style='margin-top:20px;width:250px;height:35px;border-radius:5px;margin-left:30px;' type='text' name='retailercity'> </td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>Retailer State:</h4></td>");
			pw.print("<td><input style='margin-top:20px;width:250px;height:35px;border-radius:5px;margin-left:30px;' type='text' name='retailerstate'> </td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>Review Date:</h4></td>");
			pw.print("<td> <input style='margin-top:20px;width:250px;height:35px;border-radius:5px;margin-left:30px;' type='date' name='reviewdate'> </td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>User Name:</h4></td>");
			pw.print("<td><input style='margin-top:20px;width:250px;height:35px;border-radius:5px;margin-left:30px;' type='text' name='userid'> </td>");
		    pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>User Age:</h4></td>");
			pw.print("<td><input style='margin-top:20px;width:250px;height:35px;border-radius:5px;margin-left:30px;' type='text' name='userage'></td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>User Gender:</h4></td>");
			pw.print("<td>");
			pw.print("<select name='usergender' style='width:250px;height:35px;border-radius:5px;margin-left:30px;'>");
			pw.print("<option value='1' selected>Female</option>");
			pw.print("<option value='2'>Male</option>");
			pw.print("<option value='3'>Not Known</option></td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td><h4 style='margin-top:20px;color:black;'>Review Text:</h4></td>");				
			pw.print("<td><textarea style='margin-top:20px;width:531px;height:184px;border-radius:5px;margin-left:30px;' name='reviewtext' rows='4' cols='50' required> </textarea></td></tr>");
			pw.print("<tr><td colspan='2'><input style='margin-top:30px;margin-left:150px;' type='submit' class='btn_submit' name='SubmitReview' value='Submit Review'></td></tr></table>");
			pw.print("<br><br></div>");		
			utility.printHtml("Footer.html");
        }
        catch(Exception e)
		{
            System.out.println(e.getMessage());
		}  			
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
    }
}
