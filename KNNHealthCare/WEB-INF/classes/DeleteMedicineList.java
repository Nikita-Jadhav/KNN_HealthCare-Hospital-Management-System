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

@WebServlet("/DeleteMedicineList")

public class DeleteMedicineList extends HttpServlet {
	String msg="";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		msg = "bad";
		String action = request.getParameter("button");
		String medicineId="";
		HashMap<String,Medicine> allMedicines = new HashMap<String,Medicine> ();
		medicineId   = request.getParameter("medicineId");		
		allMedicines = MySqlDataStoreUtilities.getAllMedicines();
		if(allMedicines.containsKey(medicineId))
		{
			msg = "good";
			System.out.print("console");
		}
		if (msg.equals("good"))
		{			
			try
			{  
				msg = MySqlDataStoreUtilities.deletemedicines(medicineId);
			}
			catch(Exception e)
			{ 
				msg = "Medicine cannot be deleted";
			}
		}
		else
		{
			msg = "Medicine not available in the medicine store...!";
		}
		displayDeleteMedicine(request, response, true);
	}
	
	protected void displayDeleteMedicine(HttpServletRequest request, HttpServletResponse response, boolean error) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		pw.print("<div id='post' style='margin-top:-737px;'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Delete Medicine</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'><br>");
		if (error)
			pw.print("<h3 style='margin-left:400px;font-family:Century Schoolbook;color:#e50000;'>"+msg+"</h3>");
		pw.print("<form method='post' action='DeleteMedicineList'>"
				+ "<table style='width:100%'><tr><td style='width:650px'>"
				+"<br><h3 style='margin-left:15px;color:black;'>Medicine Id</h3></td><td><input size='75' style='margin-left:-400px;margin-top:27px;width:250px;height:35px;' type='text' name='medicineId' value='' class='input' required></input>"
				+ "</td></tr></table>"
				+ "<input type='submit' class='btn_delete' name='button' value='Delete Medicine'></input>"
				+ "</form>" + "<br><br></div>");
		utility.printHtml("Footer.html");
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);		
		displayDeleteMedicine(request, response, false);
	}
}
