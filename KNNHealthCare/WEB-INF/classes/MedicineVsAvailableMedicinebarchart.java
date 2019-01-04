import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import java.io.*;

@WebServlet("/MedicineVsAvailableMedicinebarchart")

public class MedicineVsAvailableMedicinebarchart extends HttpServlet {
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view medicine Vs available medicine barchart..!");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		pw.print("<div id='post' style='margin-top:-760px;'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Medicine Vs Available Medicine Bar Chart</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'><br>");
		pw.print("<h3 style='margin-left: 340px;'><button id='btn_medicine' class='btn_medicine'>Generate Bar Chart</h3>");
		pw.println("<div id='chart_div' style='margin-left:14px;'></div>");
		pw.println("<br><br></div>");
		pw.println("<script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>");
        pw.println("<script type='text/javascript' src='MedicineVsAvailableMedicinebarchart.js'></script>");
        utility.printHtml("Footer.html");
	}
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
        try {
			ArrayList<ProductCount> productCounts=MySqlDataStoreUtilities.selectProductCountsForChart();
            String reviewJson = new Gson().toJson(productCounts);
            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(reviewJson);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}


