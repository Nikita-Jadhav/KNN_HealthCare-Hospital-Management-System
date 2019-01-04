import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")

public class Login extends HttpServlet {
	private String error_msg;
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		HashMap<String, User> hm=new HashMap<String, User>();
		String dbconn=MySqlDataStoreUtilities.getConnection();
		if(!dbconn.equals("Successful"))
		{
			error_msg="MySql server is not up and running!";
			displayLogin(request, response, pw, true);
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
			User user = hm.get(username);
			if(user!=null)
			{
				String user_password = user.getPassword();
				String user_usertype = user.getUsertype();
				String role1="Patient";
				String role2="Doctor";
				String role3="Medicine Manager";
				if (password.equals(user_password) && usertype.equals(user_usertype)) 
				{
				
					if(user_usertype.equals(role1))
					{
						HttpSession session = request.getSession(true);
						session.setAttribute("username", user.getName());
						session.setAttribute("usertype", user.getUsertype());
						response.sendRedirect("Home");
						return;
					}
					if(user_usertype.equals(role2))
					{
						HttpSession session = request.getSession(true);
						session.setAttribute("username", user.getName());
						session.setAttribute("usertype", user.getUsertype());
						response.sendRedirect("HomeDoctor");
						return;
					}
					if(user_usertype.equals(role3))
					{
						HttpSession session = request.getSession(true);
						session.setAttribute("username", user.getName());
						session.setAttribute("usertype", user.getUsertype());
						response.sendRedirect("HomeMedicineManager");
						return;
					}
				}
			}
			error_msg="Please check the entered username, password and user type!";
			displayLogin(request, response, pw, true);
		}//else ends
	}
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request,response,pw,false);
	}
	
	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,HttpServletResponse response, PrintWriter pw, boolean error)throws ServletException, IOException
	{
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		pw.print("<body><br>");
		//pw.print("<div id='login'>");
		//pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>Login</a></h2></div>");
		//pw.print("<br>");
		pw.print("<div id='loginDetails'>");
		if (error)
			pw.print("<h3 style='margin-left:356px;font-weight:bold;font-family:Century Schoolbook;color:#e50000;'>"+error_msg+"</h3>");
		HttpSession session = request.getSession(true);
		if(session.getAttribute("login_msg")!=null)
		{			
			pw.print("<h3 style='margin-left:484px;color:#e50000;'>"+session.getAttribute("login_msg")+"</h3>");
			session.removeAttribute("login_msg");
		}
		pw.print("<form method='post' action='Login'>"
				+"<br><br><h2><a style='margin-left:555px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:44px;text-decoration:none;'>Login</a></h2>"
				+"<img src='images/site/login.ico' alt='' style='width:121px;height:89px;margin-left:678px;margin-top:-82px;'/>"
				+ "<br><table style='width:100%'><br>"
				+ "<tr><td><input style='padding: 20px 41px;margin-left:525px;border-radius:5px;font-size:21px;margin-top:20px;width:325px;height:49px;' type='text' name='username' value='' placeholder='Enter Username' class='username' required></input>"
				//<h3 style='margin-left:15px;color:black;'>User Name <font color='red'>*</font></h3><br></td><td><input style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='username' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+"<input style='padding: 20px 41px;margin-left:525px;border-radius:5px;font-size:21px;margin-top:44px;width:325px;height:49px;' type='password' name='password' placeholder='Enter Password' value='' class='password' required></input>"
				+ "</td></tr><tr><td>"
				+"<select style='margin-left:525px;margin-top:44px;border-radius:5px;font-size:21px;width:325px;height:49px;' name='usertype' class='input'><option value='SelectUserType' selected>Select User Type</option><option value='Patient'>Patient</option><option value='Doctor'>Doctor</option><option value='Medicine Manager'>Medicine Manager</option></select>"
				+ "</td></tr><tr><td>"
				+ "<input type='submit' class='btn_login' value='Login'></input>"
				+ "</td></tr><tr><td>"
				+ "<strong><a class='' href='Registration' style='margin-left:545px;color:white;height:20px;font-size:16px;font-family:Century Schoolbook;margin-top:85px;'>New User? Sign Up here!</a></strong>"
				/*+ "<h3 style='margin-left:15px;color:black;'>Password <font color='red'>*</font></h3></td><td><input style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='password' name='password' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-top:35px;margin-left:15px;color:black;'>User Type <font color='red'>*</font></h3></td><td><select style='margin-left:-260px;margin-top:35px;width:250px;height:35px;' name='usertype' class='input'><option value='Patient' selected>Patient</option><option value='Doctor'>Doctor</option><option value='Medicine Manager'>Medicine Manager</option></select>"
				+ "</td></tr><tr><td></td><td>"
				+ "<input type='submit' class='btn_login' value='Login'></input>"
				+ "</td></tr><tr><td></td><td>"
				+ "<strong><a class='' href='Registration' style='color:#193d5b;margin-left:-10px;height:20px;font-size:16px;font-family:Century Schoolbook;margin-top:-20px;'>New User? Register here!</a></strong>"*/
				+ "</td></tr></table>"
				+ "</form>" + "<br><br></div></body>");
		utility.printHtml("Footer.html");
	}
}