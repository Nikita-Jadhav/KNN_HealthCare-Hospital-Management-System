import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;

@WebServlet("/HomeDoctor")

public class HomeDoctor extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(true);
		UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
		utility.printHtml("HeaderDoctor.html");
		utility.printHtml("LeftNavigationBar.html");
		utility.printHtml("ContentDoctor.html");
		RequestDispatcher rd=request.getRequestDispatcher("DealMatchesDoctorUtilities");
		rd.include(request,response);
		utility.printHtml("Footer.html");				
	}
}
