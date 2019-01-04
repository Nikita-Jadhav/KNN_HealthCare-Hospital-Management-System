import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Registration")

public class Registration extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayRegistration(request, response, pw, false);
	}

	/*   Username,Password,Usertype information are Obtained from HttpServletRequest variable and validates whether
		 the User Credential Already Exists or else User Details are Added to the Users HashMap */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String emailid = request.getParameter("emailid");
		String usertype = request.getParameter("usertype");
		if(utility.isLoggedin())
			usertype = request.getParameter("usertype");

		//if password and repassword does not match show error message

		if(!password.equals(repassword))
		{
			error_msg = "Password and Repassword doesn't match...!";
		}
		else
		{
			
			HashMap<String, User> hm=new HashMap<String, User>();
			String dbconn=MySqlDataStoreUtilities.getConnection();
			if(!dbconn.equals("Successful"))
			{
				error_msg="MySql server is not up and running...!";
				//displayLogin(request, response, pw, true);
			}
			else
			{
				try
				{
					hm=MySqlDataStoreUtilities.selectUser();
				}
				catch(Exception e)
				{
				
				}

				// if the user already exist show error that already exist

				if(hm.containsKey(username))
					error_msg = "Username already exist as " + usertype + "...!";
				else
				{
					User user = new User(username,password,usertype);
					hm.put(username, user);
					MySqlDataStoreUtilities.insertUser(username,password,repassword,emailid,usertype);					
					HttpSession session = request.getSession(true);				
					session.setAttribute("login_msg","Your "+usertype+" account has been created. Please login...!");
					if(!utility.isLoggedin())
					{
						response.sendRedirect("Login"); return;
					} 
					else 
					{
						response.sendRedirect("Account"); return;
					}
				}
			}
		}

		//display the error message
		if(utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", error_msg);
			response.sendRedirect("Account"); return;
		}
		displayRegistration(request, response, pw, true);
		
	}

	/*  displayRegistration function displays the Registration page of New User */
	
	protected void displayRegistration(HttpServletRequest request,HttpServletResponse response, PrintWriter pw, boolean error)throws ServletException,IOException
	{
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<body><br>");
		/*pw.print("<div id='login'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>User Registration</a></h2></div>");
		pw.print("<br>");*/
		pw.print("<div id='loginDetails'>");
		if (error)
			pw.print("<h3 style='margin-left:455px;font-weight:bold;font-family:Century Schoolbook;color:#e50000;'>"+error_msg+"</h3>");
			pw.print("<form method='post' action='Registration'>"
				+"<br><br><h2><a style='margin-left:555px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:44px;text-decoration:none;'>Sign Up</a></h2>"
				+ "<table style='width:100%'><tr><td style='width:650px'>"
				+"<br><h3 style='margin-left:421px;font-size:29px;color:white;margin-top:20px;'>User Name <font color='red'>*</font></h3></td><td><input style='margin-left:-4px;border-radius:5px;font-size:21px;margin-top:20px;width:325px;height:44px;' type='text' name='username' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:421px;color:white;'>Password <font color='red'>*</font></h3></td><td><input style='margin-left:-4px;border-radius:5px;font-size:21px;margin-top:0px;width:325px;height:44px;' type='password' name='password' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:421px;color:white;'>Re-Password <font color='red'>*</font></h3></td><td><input style='margin-left:-4px;border-radius:5px;font-size:21px;margin-top:0px;width:325px;height:44px;' type='password' name='repassword' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:421px;color:white;'>Email ID <font color='red'>*</font></h3></td><td><input style='margin-left:-4px;border-radius:5px;font-size:21px;margin-top:0px;width:325px;height:44px;' type='email' name='emailid' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:421px;color:white;'>User Type <font color='red'>*</font></h3></td><td><select style='margin-left:-4px;border-radius:5px;font-size:21px;margin-top:0px;width:325px;height:44px;' name='usertype' class='input'><option value='Patient' selected>Patient</option><option value='Doctor'>Doctor</option><option value='Medicine Manager'>Medicine Manager</option></select>"
				+ "</td></tr></table>"
				+ "<br><input type='submit' class='btn_reg' name='ByUser' value='Create User'></input>"
				+ "</form>" + "<br><br></div>");
		utility.printHtml("Footer.html");
	}
}
