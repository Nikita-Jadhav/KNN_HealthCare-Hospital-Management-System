import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL; 
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file)
	{
		String result = HtmlToString(file);
		if (file == "Header.html") 
		{
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result + "<span style='margin-left:330px;'>"+
						"<li style='margin-left:-165px;'><a href='MyPrescription' style='padding-right:0px;font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static;'>My Prescription</span></a></li>"
						+"<li style='margin-left:15px;'><a href='ViewAppointment' style='padding-right:0px;font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static;'>View Appointment</span></a></li>"
						+ "<li style='margin-left:15px;'><a href='ViewOrder' style='font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static'>View Order</span></a></li>"
						+ "<li style='margin-left:15px;'><a href='PatientAccount' style='font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static'>Patient Appointment</span></a></li>"
						+ "<li style='margin-left:15px;'><a href='OrderAccount' style='font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static'>Patient Account</span></a></li>"
						+ "<li style='margin-left:15px;'><a style='font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static;'>Hello,"+username+"</span></a></li>"
						+ "<li style='margin-left:15px;'><a href='Logout' style='font-size: 20px;font-family:Arial, Helvetica, sans-serif;font-weight:normal;word-spacing:-15px;'><span class='glyphicon' style='position:static;'>Logout</span></a></li>"
						+"<li style='margin-left:15px;'><a href='Cart'><span class='glyphicon glyphicon-shopping-cart' style='position:static;'>Cart("+CartCount()+")</span></a></li></span></ul></div></div>";				
			}
			else
			{
				result = result +"<span style='margin-left:1000px;'>"+
				"<li style='margin-left:15px;'><a href='Login''><span class='glyphicon' style='position:static;'>Login</span></a></li>"+
				"<li style='margin-left:15px;'><a href='ViewOrder'><span class='glyphicon' style='position:static;'>View Order</span></a></li>"+
				"<li style='margin-left:15px;'><a href='Cart'><span class='glyphicon glyphicon-shopping-cart' style='position:static;'>Cart("+CartCount()+")</span></a></li></span></ul></div></div>";
			}	
				pw.print(result);
		}
		else
			pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}
	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	}
	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser()
	{
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		try
		{		
			hm=MySqlDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{
		}			
		User user = hm.get(username());
		return user;
	}
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders()
	{
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc)
	{
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		String quantity = "1";
		HashMap<String,Medicine> allMedicines = new HashMap<String,Medicine> ();
		HashMap<String,Accessory> allAccessories=new HashMap<String,Accessory>();		
		if(type.equals("Allergies")){
			Medicine medicine;
			//phone = SaxParserDataStore.phones.get(name);
			try{
			allMedicines = MySqlDataStoreUtilities.getAllergies();
			
			}
			catch(Exception e){
				
			}
			medicine = allMedicines.get(name);
			OrderItem orderitem = new OrderItem(medicine.getProductName(), medicine.getProductPrice(), medicine.getDiscount(), medicine.getManufacturerRebate(), medicine.getProductImage(), medicine.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
		if(type.equals("Antibiotics")){
			Medicine medicine = null;
			//voiceassistant = SaxParserDataStore.voiceassistants.get(name);
			try{
			allMedicines = MySqlDataStoreUtilities.getAntibiotics();
			
			}
			catch(Exception e){
				
			}
			medicine = allMedicines.get(name);
			OrderItem orderitem = new OrderItem(medicine.getProductName(), medicine.getProductPrice(), medicine.getDiscount(), medicine.getManufacturerRebate(), medicine.getProductImage(), medicine.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
		if(type.equals("Anti Viral")){
			Medicine medicine = null;
			//laptop = SaxParserDataStore.laptops.get(name);
			try{
			allMedicines = MySqlDataStoreUtilities.getAntivirals();
			
			}
			catch(Exception e){
				
			}
			medicine = allMedicines.get(name);
			OrderItem orderitem = new OrderItem(medicine.getProductName(), medicine.getProductPrice(), medicine.getDiscount(), medicine.getManufacturerRebate(), medicine.getProductImage(), medicine.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
		if(type.equals("Heart Disease")){
			Medicine medicine = null;
			//laptop = SaxParserDataStore.laptops.get(name);
			try{
			allMedicines = MySqlDataStoreUtilities.getHeartDisease();
			
			}
			catch(Exception e){
				
			}
			medicine = allMedicines.get(name);
			OrderItem orderitem = new OrderItem(medicine.getProductName(), medicine.getProductPrice(), medicine.getDiscount(), medicine.getManufacturerRebate(), medicine.getProductImage(), medicine.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
		if(type.equals("Kidney Disease")){
			Medicine medicine = null;
			//laptop = SaxParserDataStore.laptops.get(name);
			try{
			allMedicines = MySqlDataStoreUtilities.getKidneyDisease();
			
			}
			catch(Exception e){
				
			}
			medicine = allMedicines.get(name);
			OrderItem orderitem = new OrderItem(medicine.getProductName(), medicine.getProductPrice(), medicine.getDiscount(), medicine.getManufacturerRebate(), medicine.getProductImage(), medicine.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
		if(type.equals("Skincare")){
			Medicine medicine = null;
			//laptop = SaxParserDataStore.laptops.get(name);
			try{
			allMedicines = MySqlDataStoreUtilities.getSkincare();
			
			}
			catch(Exception e){
				
			}
			medicine = allMedicines.get(name);
			OrderItem orderitem = new OrderItem(medicine.getProductName(), medicine.getProductPrice(), medicine.getDiscount(), medicine.getManufacturerRebate(), medicine.getProductImage(), medicine.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
		if(type.equals("Accessory"))
		{	
			Accessory accessory = null;
			try
			{
				allAccessories = MySqlDataStoreUtilities.getAccessory();
			
			}
			catch(Exception e){
				
			}
			accessory = allAccessories.get(name);
			OrderItem orderitem = new OrderItem(accessory.getProductName(), accessory.getProductPrice(), accessory.getDiscount(), accessory.getManufacturerRebate(), accessory.getProductImage(), accessory.getProductManufacturer(),quantity);
			orderItems.add(orderitem);
		}
	}
	public int getOrderPaymentSize()
	{
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		int size=0;
		try
		{
			orderPayments =MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{}
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet())
		{
			size=size + 1;					 
		}
		return size;		
	}
	public void storePayment(int orderId,String orderName,double orderOriginalPrice,double orderDiscount,double orderManufecturarRebate,double orderPayablePrice,String customerAddress,String creditCardNo,String creditCardName,String creditCardExpiry,String creditCardCVV,String orderDate,String deliveryDate,String orderStatus,String quantity)
	{
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		HashMap<Integer, ArrayList<UpdateCount>> updateCounts= new HashMap<Integer, ArrayList<UpdateCount>>();
			try
			{
				orderPayments=MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			if(!orderPayments.containsKey(orderId))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
			OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderOriginalPrice,orderDiscount,orderManufecturarRebate,orderPayablePrice,customerAddress,creditCardNo,creditCardName,creditCardExpiry,creditCardCVV,orderDate,deliveryDate,orderStatus);
			listOrderPayment.add(orderpayment);
			try
			{	
				int quantityFinal = Integer.parseInt(quantity);
				double originalPrice,discount,rebate,finalPrice = 0.0;
				originalPrice = orderOriginalPrice*quantityFinal;
				discount = orderDiscount*quantityFinal;
				rebate = orderManufecturarRebate*quantityFinal;
				finalPrice = orderPayablePrice*quantityFinal;
				MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,originalPrice,discount,rebate,finalPrice,customerAddress,creditCardNo,creditCardName,creditCardExpiry,creditCardCVV,orderDate,deliveryDate,orderStatus,quantityFinal);
				MySqlDataStoreUtilities.selectOrderCount(orderName,quantityFinal);
			}
			catch(Exception e)
			{
				System.out.println("Inside exception file not written properly...!");
			}			
	}
	public void truncateData()
	{
		try
		{	
			MySqlDataStoreUtilities.RemoveData();
		}
		catch(Exception e)
		{
			System.out.println("Inside exception...!");
		}
	}
	public int getAppointmentId()
	{
		int count=0;
		try
		{
			count =MySqlDataStoreUtilities.selectAppointments();
		}
		catch(Exception e)
		{}
		return count;		
	}
	public String storeAppointmentInformation(String userName,String doctorSpeciality,String doctoreName,String doctorVistingDay,String appointmentDate,int id,String date)
	{
		String msg="";
		try
		{	
			msg=MySqlDataStoreUtilities.storeAppointment(userName,doctorSpeciality,doctoreName,doctorVistingDay,appointmentDate,id,date);
		}
		catch(Exception e)
		{
			System.out.println("Inside exception...!");
			msg = "Error occurred while scheduling an appointment..!";
		}
		return msg;
	}
	public String storeReview(String doctorname,String doctorspeciality,String reviewrating,String reviewdate,
	String userid,String patientage,String patientgender,String reviewtext)
	{
		String message=MongoDBDataStoreUtilities.insertReview(doctorname,doctorspeciality,reviewrating,reviewdate,userid,patientage,patientgender,reviewtext);
		if(!message.equals("Successfull"))
		{
			return "UnSuccessfull";
		}
		else
		{
			HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
			try
			{
				reviews=MongoDBDataStoreUtilities.selectReview();
			}
			catch(Exception e)
			{}
			if(reviews==null)
			{
				reviews = new HashMap<String, ArrayList<Review>>();
			}
			if(!reviews.containsKey(doctorname))
			{	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(doctorname, arr);
			}
			ArrayList<Review> listReview = reviews.get(doctorname);		
			Review review = new Review(username(),doctorname,doctorspeciality,reviewrating,
			reviewdate,patientage,patientgender,reviewtext);
			listReview.add(review);	
			return "Successfull";		
		}
	}
	public String storeReviewM(String productname,String producttype,String price,String productmaker,String reviewrating,	String manufacturerrebate,String reatilerpin,String city,String retailerstate,String reviewdate,
	String userid,String userage,String usergender,String reviewtext)
	{
		String message=MongoDBDataStoreUtilitiesM.insertReviewM(productname,producttype,price,productmaker,reviewrating,manufacturerrebate,reatilerpin,city,retailerstate,reviewdate,userid,userage,usergender,reviewtext);
		if(!message.equals("Successfull"))
		{
			return "UnSuccessfull";
		}
		else
		{
			HashMap<String, ArrayList<ReviewM>> reviewsM= new HashMap<String, ArrayList<ReviewM>>();
			try
			{
				reviewsM=MongoDBDataStoreUtilitiesM.selectReviewM();
			}
			catch(Exception e)
			{}
			if(reviewsM==null)
			{
			reviewsM = new HashMap<String, ArrayList<ReviewM>>();
			}
			if(!reviewsM.containsKey(productname))
			{	
				ArrayList<ReviewM> arr = new ArrayList<ReviewM>();
				reviewsM.put(productname, arr);
			}
			ArrayList<ReviewM> listReviewM = reviewsM.get(productname);		
			ReviewM reviewM = new ReviewM(username(),productname,producttype,price,productmaker,reviewrating,manufacturerrebate,reatilerpin,city,retailerstate,reviewdate,userage,usergender,reviewtext);
			listReviewM.add(reviewM);
			return "Successfull";	
		}
	}
}
