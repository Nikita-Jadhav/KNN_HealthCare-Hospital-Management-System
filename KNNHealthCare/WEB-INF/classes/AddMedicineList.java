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
import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/AddMedicineList")

public class AddMedicineList extends HttpServlet {
	String msg="";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request, pw);
		String action = request.getParameter("button");
		System.out.print(action);
		msg = "good";
		String medicineType= "",medicineId="",medicineName="",medicineImage="",medicineManufacturer="";
		String medicineManufactureDate = "";
		String medicineBestBeforeDate= "";
		String medicineDescription="",prod = "";
		double medicinePrice=0.0,medicineDiscount = 0.0,manufacturerRebate=0.0;
		int originalMedicineQuantity=0;
		HashMap<String,Medicine> allAllergies = new HashMap<String,Medicine> ();
		HashMap<String,Medicine> allAntivirals = new HashMap<String,Medicine> ();
		HashMap<String,Medicine> allAntibiotics = new HashMap<String,Medicine> ();
		HashMap<String,Medicine> allHeartDisease = new HashMap<String,Medicine> ();
		HashMap<String,Medicine> allKidneyDisease = new HashMap<String,Medicine> ();
		HashMap<String,Medicine> allSkincare = new HashMap<String,Medicine> ();
		HashMap<String,Medicine> allAccessory = new HashMap<String,Medicine> ();
		medicineType = request.getParameter("medicineType");
		medicineId   = request.getParameter("medicineId");
		medicineName = request.getParameter("medicineName"); 
		medicinePrice = Double.parseDouble(request.getParameter("medicinePrice"));
		medicineImage = request.getParameter("medicineImage");
		medicineManufacturer = request.getParameter("medicineManufacturer");
		medicineManufactureDate = request.getParameter("medicineManufactureDate");
		medicineBestBeforeDate = request.getParameter("medicineBestBeforeDate");
		medicineDescription = request.getParameter("medicineDescription");
		medicineDiscount = Double.parseDouble(request.getParameter("medicineDiscount"));
		originalMedicineQuantity = Integer.parseInt(request.getParameter("originalMedicineQuantity"));
		manufacturerRebate = Double.parseDouble(request.getParameter("manufacturerRebate"));
		prod = request.getParameter("product");
		
		if(medicineType.equals("Allergies"))
		{
			allAllergies = MySqlDataStoreUtilities.getAllergies();
			if(allAllergies.containsKey(medicineId))
			{
				msg = "Medicine is already available in the medicine store...!";
				System.out.print(medicineId);
			}
		}
		else if(medicineType.equals("AntiViral"))
		{
			allAntivirals = MySqlDataStoreUtilities.getAntivirals();
			if(allAntivirals.containsKey(medicineId))
			{
				msg = "Medicine is already available in the medicine store...!";
			}
		}
		else if(medicineType.equals("Antibiotics"))
		{
			allAntibiotics = MySqlDataStoreUtilities.getAntibiotics();
			if(allAntibiotics.containsKey(medicineId))
			{
				msg = "Medicine is already available in the medicine store...!";
			}
		}
		else if(medicineType.equals("HeartDisease"))
		{
			allHeartDisease = MySqlDataStoreUtilities.getHeartDisease();
			if(allHeartDisease.containsKey(medicineId))
			{
				msg = "Medicine is already available in the medicine store...!";
			}
		}
		else if(medicineType.equals("KidneyDisease"))
		{
			allKidneyDisease = MySqlDataStoreUtilities.getKidneyDisease();
			if(allKidneyDisease.containsKey(medicineId))
			{
				msg = "Medicine is already available in the medicine store...!";
			}
		}
		else if(medicineType.equals("Skincare"))
		{
			allSkincare = MySqlDataStoreUtilities.getSkincare();
			if(allSkincare.containsKey(medicineId))
			{
				msg = "Medicine is already available in the medicine store...!";
			}
		}
		if (msg.equals("good"))
		{  
			try
			{
				msg = MySqlDataStoreUtilities.addproducts(medicineType,medicineId,medicineName,medicinePrice,medicineImage,medicineManufacturer,medicineManufactureDate,medicineBestBeforeDate,medicineDescription,medicineDiscount,originalMedicineQuantity,manufacturerRebate,prod);
			}
			catch(Exception e)
			{ 
				msg = "Unable to add medicine in a medicine store ..!";
			}
		}
		displayCreateMedicine(request, response, true);
	}

	protected void displayCreateMedicine(HttpServletRequest request, HttpServletResponse response,boolean error) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);
		utility.printHtml("HeaderMedicineManager.html");
		utility.printHtml("LeftNavigationBarMedicineManager.html");
		pw.print("<div id='post' style='margin-top:-737px;'>");
		pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration: none;'>Add Medicine</a></h2></div>");
		pw.print("<br>");
		pw.print("<div id='entry'><br>");
		if (error)
			pw.print("<h3 style='margin-left:400px;font-family:Century Schoolbook;color:#e50000;'>"+msg+"</h3>");
		pw.print("<form method='post' action='AddMedicineList'>"
				+ "<table style='width:100%'><tr><td style='width:650px'>"
				+"<br><h3 style='margin-left:15px;color:black;'>Medicine Category <font color='red'>*</font></h3></td><td><select style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' name='medicineType' class='input'><option value='Allergies' selected>Allergies</option><option value='Antibiotics'>Antibiotics</option><option value='AntiViral'>Anti Viral</option><option value='HeartDisease'>Heart Disease</option><option value='KidneyDisease'>Kidney Disease</option><option value='Skincare'>Skincare</option><option value='MedicineAccessory'>Medicine Accessory</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Id <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='medicineId' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"				
				+"<br><h3 style='margin-left:15px;color:black;'>Medicine Name <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='medicineName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Accessory Product </h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='product' value='' class='input'></input>"
				+ "</td></tr><tr><td>"				
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Price <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='number' step='any' placeholder='please enter numeric data' name='medicinePrice' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Image Name <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='medicineImage' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Manufacturer <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='medicineManufacturer' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Manufacturer Date <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='date' name='medicineManufactureDate' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Best Before Date <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='date' name='medicineBestBeforeDate' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Description <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='text' name='medicineDescription' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Discount <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='number' step='any' placeholder='please enter numeric data' name='medicineDiscount' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Medicine Quantity <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='number' step='any' placeholder='please enter numeric data' name='originalMedicineQuantity' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3 style='margin-left:15px;color:black;'>Manufacturer Rebate <font color='red'>*</font></h3></td><td><input size='75' style='margin-left:-260px;margin-top:20px;width:250px;height:35px;' type='number' step='any' placeholder='please enter numeric data' name='manufacturerRebate' value='' class='input' required></input>"
				+ "</td></tr></table>"
				+ "<input type='submit' class='btn_medicine' name='button' value='Add Medicine Details'></input>"
				+ "</form>" + "<br><br></div>");
		utility.printHtml("Footer.html");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);		
		displayCreateMedicine(request, response, false);
	}
}
