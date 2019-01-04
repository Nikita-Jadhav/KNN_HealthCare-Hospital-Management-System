import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;

@WebServlet("/HomeMedicineManager")

public class HomeMedicineManager extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(true);
		
		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		utility.printHtml("ContentMedicineManager.html");
		RequestDispatcher rd=request.getRequestDispatcher("DealMatchesManagerUtilities");
		rd.include(request,response);
		utility.printHtml("Footer.html");				
	}
}
