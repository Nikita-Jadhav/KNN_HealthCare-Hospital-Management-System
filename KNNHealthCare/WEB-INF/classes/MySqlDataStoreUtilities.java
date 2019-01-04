import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

                	
public class MySqlDataStoreUtilities
{
	static Connection conn = null;
	static String message;
	public static String getConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/knnhealthcare","root","root");	
			message="Successful";
			return message;	
		}
		catch(SQLException e)
		{
			message="unsuccessful";
			return message;
		}
		catch(Exception e)
		{
			message=e.getMessage();
			return message;
		}
	}

	public static void insertUser(String username,String password,String repassword,String emailid,String usertype)
	{
		try
		{	
			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,emailId,usertype) "
			+ "VALUES (?,?,?,?,?);";	
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,username);
			pst.setString(2,password);
			pst.setString(3,repassword);
			pst.setString(4,emailid);
			pst.setString(5,usertype);
			pst.execute();
		}
		catch(Exception e)
		{}	
	}

	public static HashMap<String,User> selectUser()
	{	
		HashMap<String,User> hm=new HashMap<String,User>();
		try 
		{
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  Registration";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{
				User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	
	public static void Insertdoctors()
	{
		try
		{
			getConnection();
			int i=1;
			String insertDoctorQurey = "INSERT INTO doctor_details(Id,Name,speciality,doctorImage,gender,languages,emailId,contactNumber_1,contactNumber_2,qualification,doctorDescription,yearsInPractice,"+
			"visitingPrice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";		
			for(Map.Entry<String,DoctorSpeciality> entry : SaxParserDataStore.doctorspecialities.entrySet())
			{   
				String name = "doctorspecialities";
				DoctorSpeciality doctorspeciality = entry.getValue();			
				PreparedStatement pst = conn.prepareStatement(insertDoctorQurey);
				pst.setString(1,doctorspeciality.getId());
				pst.setString(2,doctorspeciality.getName());
				pst.setString(3,doctorspeciality.getSpeciality());
				pst.setString(4,doctorspeciality.getDoctorImage());
				pst.setString(5,doctorspeciality.getGender());
				pst.setString(6,doctorspeciality.getLanguages());			
				pst.setString(7,doctorspeciality.getEmailId());
				pst.setString(8,doctorspeciality.getContactNumber_1());
				pst.setString(9,doctorspeciality.getContactNumber_2());
				pst.setString(10,doctorspeciality.getQualification());
				pst.setString(11,doctorspeciality.getDoctorDescription());
				pst.setString(12,doctorspeciality.getYearsInPractice());
				pst.setDouble(13,doctorspeciality.getVisitingPrice());
				pst.executeUpdate();
				try
				{
					HashMap<String,String> acc = doctorspeciality.getVisitingDetails();
					String insertAccessoryQurey = "INSERT INTO  doctors_visiting_details(visitingId,Id,Name,visitingDay)" +
					"VALUES (?,?,?,?);";
					for(Map.Entry<String,String> accentry : acc.entrySet())
					{
						PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
						pstacc.setInt(1,i);
						pstacc.setString(2,doctorspeciality.getId());
						pstacc.setString(3,doctorspeciality.getName());
						pstacc.setString(4,accentry.getValue());
						pstacc.executeUpdate();
						i++;
					}	
				}
				catch(Exception et)
				{
					et.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static HashMap<String,DoctorSpeciality> getDoctorspecialities()
	{	
		HashMap<String,DoctorSpeciality> hm=new HashMap<String,DoctorSpeciality>();
		try 
		{
			getConnection();
			String selectDoctors="select * from doctor_details;";
			PreparedStatement pst = conn.prepareStatement(selectDoctors);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				DoctorSpeciality doctorspeciality = new DoctorSpeciality(rs.getString("Name"),rs.getString("speciality"),rs.getString("doctorImage"),rs.getString("gender"),rs.getString("languages"),rs.getString("emailId"),rs.getString("contactNumber_1"),rs.getString("contactNumber_2"),rs.getString("qualification"),rs.getString("doctorDescription"),rs.getString("yearsInPractice"),rs.getDouble("visitingPrice"));
				hm.put(rs.getString("Id"),doctorspeciality);
				doctorspeciality.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static ResultSet selectDoctor(String name)
	{
		ResultSet rs = null;
		try
		{
			getConnection();
			//select the table 
			String selectDoctorInformation ="select * from doctor_details where Name = ?;";		
			PreparedStatement pst = conn.prepareStatement(selectDoctorInformation);
			pst.setString(1,name);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}
	public static ResultSet selectVisitingInformation(String name)
	{
		ResultSet rs = null;
		try
		{
			getConnection();
			//select the table 
			String selectDoctorInformation ="select * from doctors_visiting_details where Name = ?;";		
			PreparedStatement pst = conn.prepareStatement(selectDoctorInformation);
			pst.setString(1,name);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}
	public static void Insertmedicines()
	{
		try
		{
			getConnection();
			String insertMedicineQurey = "INSERT INTO medicine_details(Id,productName,productType,productPrice,productImage,productManufacturer,manufactureDate,bestBeforeDate,description,discount,originalProductquantity,soldProductQuantity,availableProductQuantity,"+
			"manufacturerRebate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";		
			for(Map.Entry<String,Medicine> entry : SaxParserDataStore.medicines.entrySet())
			{   
				String name = "medicines";
				int count=0;
				Medicine medicine = entry.getValue();			
				PreparedStatement pst = conn.prepareStatement(insertMedicineQurey);
				pst.setString(1,medicine.getId());
				pst.setString(2,medicine.getProductName());
				pst.setString(3,medicine.getProductType());
				pst.setDouble(4,medicine.getProductPrice());
				pst.setString(5,medicine.getProductImage());
				pst.setString(6,medicine.getProductManufacturer());
				DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
				pst.setString(7,medicine.getManufactureDate());
				pst.setString(8,medicine.getBestBeforeDate());
				pst.setString(9,medicine.getDescription());
				pst.setDouble(10,medicine.getDiscount());
				pst.setInt(11,medicine.getOriginalProductquantity());
				pst.setInt(12,count);
				pst.setInt(13,medicine.getOriginalProductquantity());
				pst.setDouble(14,medicine.getManufacturerRebate());	
				pst.executeUpdate();
			}
			for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
			{   
				String name = "accessories";
				int count=0;
				Accessory accessory = entry.getValue();			
				PreparedStatement pst = conn.prepareStatement(insertMedicineQurey);
				pst.setString(1,accessory.getId());
				pst.setString(2,accessory.getProductName());
				pst.setString(3,accessory.getProductType());
				pst.setDouble(4,accessory.getProductPrice());
				pst.setString(5,accessory.getProductImage());
				pst.setString(6,accessory.getProductManufacturer());
				DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
				pst.setString(7,accessory.getManufactureDate());
				pst.setString(8,accessory.getBestBeforeDate());
				pst.setString(9,accessory.getDescription());
				pst.setDouble(10,accessory.getDiscount());
				pst.setInt(11,accessory.getOriginalProductquantity());
				pst.setInt(12,count);
				pst.setInt(13,accessory.getOriginalProductquantity());
				pst.setDouble(14,accessory.getManufacturerRebate());	
				pst.executeUpdate();	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static HashMap<String,Medicine> getMedicines()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try 
		{
			getConnection();
			String selectMedicines="select * from medicine_details;";
			PreparedStatement pst = conn.prepareStatement(selectMedicines);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Accessory> getAccessories()
	{	
		HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
		try 
		{
			getConnection();
			String selectAccessories="select * from medicine_details;";
			PreparedStatement pst = conn.prepareStatement(selectAccessories);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				Accessory accessory = new Accessory(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),accessory);
				accessory.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	
	public static HashMap<String,Medicine> getAllergies()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Allergies");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Medicine> getAntivirals()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Anti Viral");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Medicine> getAntibiotics()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Antibiotics");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Medicine> getHeartDisease()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Heart Disease");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Medicine> getKidneyDisease()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Kidney Disease");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Medicine> getSkincare()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Skincare");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
	public static HashMap<String,Accessory> getAccessory()
	{	
		HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
		try
		{
			getConnection();
			String selectAllergy="select * from  medicine_details where productType=?";
			PreparedStatement pst = conn.prepareStatement(selectAllergy);
			pst.setString(1,"Accessory");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Accessory accessory = new Accessory(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),accessory);
				accessory.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}

	public static String addproducts(String medicineType,String medicineId,String medicineName,double medicinePrice,String medicineImage,String medicineManufacturer,String medicineManufactureDate,String medicineBestBeforeDate,String medicineDescription,double medicineDiscount,int originalMedicineQuantity,double manufacturerRebate,String prod)
	{
		String msg = "";
		try
		{
			getConnection();
			String addMedicineQurey = "INSERT INTO  medicine_details(Id,productName,productType,productPrice,productImage,productManufacturer,manufactureDate,bestBeforeDate,description,discount,originalProductquantity,soldProductQuantity,availableProductQuantity,manufacturerRebate)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";  
			String name = medicineType;
			int count=0;
			PreparedStatement pst = conn.prepareStatement(addMedicineQurey);
			pst.setString(1,medicineId);
			pst.setString(2,medicineName);
			pst.setString(3,medicineType);
			pst.setDouble(4,medicinePrice);
			pst.setString(5,medicineImage);
			pst.setString(6,medicineManufacturer);
			pst.setString(7,medicineManufactureDate);
			pst.setString(8,medicineBestBeforeDate);
			pst.setString(9,medicineDescription);
			pst.setDouble(10,medicineDiscount);
			pst.setInt(11,originalMedicineQuantity);
			pst.setInt(12,count);
			pst.setInt(13,originalMedicineQuantity);
			pst.setDouble(14,manufacturerRebate);
			pst.executeUpdate();
			msg = "Medicine is added successfully in the medicine store...!";
		}
		catch(Exception e)
		{
			msg = "Error while adding medicine in the medicine store..!";
			e.printStackTrace();		
		}
		return msg;
	}

	public static String deletemedicines(String medicineId)
	{
		String msg = "";
		try
		{		
			getConnection();
			String deleteMedicinesQuery ="Delete from medicine_details where Id=?";
			PreparedStatement pst = conn.prepareStatement(deleteMedicinesQuery);
			pst.setString(1,medicineId);		
			pst.executeUpdate();
			msg = "Medicine is deleted successfully from the medicine store...!";
		}
		catch(Exception e)
		{
			msg = "Medicine cannot be deleted from the medicine store...!";
		}
		return msg;
	}

	public static String updatemedicines(String medicineType,String medicineId,String medicineName,double medicinePrice,String medicineImage,String medicineManufacturer,String medicineManufactureDate,String medicineBestBeforeDate,String medicineDescription,double medicineDiscount,int originalMedicineQuantity,int soldMedicineQuantity,int availableMedicineQuantity,double manufacturerRebate)
	{
		String msg = "";
		try
		{
			getConnection();
			String updateMedicineQurey = "UPDATE medicine_details SET productType=?,productName=?,productPrice=?,productImage=?,productManufacturer=?,manufactureDate=?,bestBeforeDate=?,description=?,discount=?,originalProductquantity=?,soldProductQuantity=?,availableProductQuantity=?,manufacturerRebate=? where Id =?;" ;
			PreparedStatement pst = conn.prepareStatement(updateMedicineQurey);
			pst.setString(1,medicineType);
			pst.setString(2,medicineName);
			pst.setDouble(3,medicinePrice);
			pst.setString(4,medicineImage);
			pst.setString(5,medicineManufacturer);
			pst.setString(6,medicineManufactureDate);
			pst.setString(7,medicineBestBeforeDate);
			pst.setString(8,medicineDescription);
			pst.setDouble(9,medicineDiscount);
			pst.setInt(10,originalMedicineQuantity);
			pst.setInt(11,soldMedicineQuantity);
			pst.setInt(12,availableMedicineQuantity);
			pst.setDouble(13,manufacturerRebate);
			pst.setString(14,medicineId);
			pst.executeUpdate();
			msg = "Medicine is updated successfully...!!";		
		}
		catch(Exception e)
		{
			msg = "Medicine cannot be updated...!";
			e.printStackTrace();	
		}
	return msg;	
	}
	public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
	{
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		try
		{
			getConnection();
			String selectOrderQuery ="select * from customer_orders;";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();	
			ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
			while(rs.next())
			{
				if(!orderPayments.containsKey(rs.getInt("OrderId")))
				{
					ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
					orderPayments.put(rs.getInt("orderId"), arr);
				}
				ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
				System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));
				OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("customertName"),rs.getString("orderName"),rs.getDouble("orderOriginalPrice"),rs.getDouble("orderDiscount"),rs.getDouble("orderManufecturarRebate"),rs.getDouble("orderPayablePrice"),rs.getString("customerAddress"),rs.getString("creditCardNo"),rs.getString("creditCardName"),rs.getString("creditCardExpiry"),rs.getString("creditCardCVV"),rs.getString("orderDate"),rs.getString("deliveryDate"),rs.getString("orderStatus"));
				listOrderPayment.add(order);	
			}
		}
		catch(Exception e)
		{}
		return orderPayments;
	}
	public static void insertOrder(int orderId,String userName,String orderName,double orderOriginalPrice,double orderDiscount,double orderManufecturarRebate,double orderPayablePrice,String customerAddress,String creditCardNo,String creditCardName,String creditCardExpiry,String creditCardCVV, String orderDate,String deliveryDate,String orderStatus,int quantity)
	{
		int value=0;
		double total=0.0;
		try
		{
			getConnection();
			String selectIntoProductQuery = "select quantity from temp_table where productName=?;";	
			PreparedStatement pstI = conn.prepareStatement(selectIntoProductQuery);
			pstI.setString(1,orderName);	
			ResultSet rs = pstI.executeQuery();
			while(rs.next())
			{
				value = rs.getInt("quantity");
			}
			if(value>0)
			{
				total=(orderOriginalPrice*value) - ((orderDiscount*value) +(orderManufecturarRebate*value));
				String insertIntoCustomerOrderQueryI = "INSERT INTO customer_orders(OrderId,customertName,OrderName,orderOriginalPrice,orderDiscount,orderManufecturarRebate,quantityOrderd,orderPayablePrice,customerAddress,creditCardNo,creditCardName,creditCardExpiry,creditCardCVV,orderDate,deliveryDate,orderStatus) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";				
				PreparedStatement pstII = conn.prepareStatement(insertIntoCustomerOrderQueryI);
				pstII.setInt(1,orderId);
				pstII.setString(2,userName);
				pstII.setString(3,orderName);
				pstII.setDouble(4,orderOriginalPrice);
				pstII.setDouble(5,orderDiscount);
				pstII.setDouble(6,orderManufecturarRebate);
				pstII.setInt(7,value);
				pstII.setDouble(8,total);
				pstII.setString(9,customerAddress);
				pstII.setString(10,creditCardNo);
				pstII.setString(11,creditCardName);
				pstII.setString(12,creditCardExpiry);
				pstII.setString(13,creditCardCVV);
				pstII.setString(14,orderDate);
				pstII.setString(15,deliveryDate);
				pstII.setString(16,orderStatus);
				pstII.executeUpdate();
			}
			else
			{
				String insertIntoCustomerOrderQuery = "INSERT INTO customer_orders(OrderId,customertName,OrderName,orderOriginalPrice,orderDiscount,orderManufecturarRebate,quantityOrderd,orderPayablePrice,customerAddress,creditCardNo,creditCardName,creditCardExpiry,creditCardCVV,orderDate,deliveryDate,orderStatus) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";				
				PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
				pst.setInt(1,orderId);
				pst.setString(2,userName);
				pst.setString(3,orderName);
				pst.setDouble(4,orderOriginalPrice);
				pst.setDouble(5,orderDiscount);
				pst.setDouble(6,orderManufecturarRebate);
				pst.setInt(7,quantity);
				pst.setDouble(8,orderPayablePrice);
				pst.setString(9,customerAddress);
				pst.setString(10,creditCardNo);
				pst.setString(11,creditCardName);
				pst.setString(12,creditCardExpiry);
				pst.setString(13,creditCardCVV);
				pst.setString(14,orderDate);
				pst.setString(15,deliveryDate);
				pst.setString(16,orderStatus);
				pst.execute();
			}
		}
		catch(Exception e)
		{}		
	}
	public static void selectOrderCount(String productName,int quantity)
	{
		int originalProductquantity=0;
		int soldProductQuantity=0;
		int availableProductQuantity=0;
		int value=0;
		String productNameUpdate="";
		try
		{
			getConnection();
			String selectCountQuantityQuery = "select quantity from temp_table where productName=?;";	
			PreparedStatement pstI = conn.prepareStatement(selectCountQuantityQuery);
			pstI.setString(1,productName);	
			ResultSet rsI = pstI.executeQuery();
			while(rsI.next())
			{
				value = rsI.getInt("quantity");
			}
			if(value>0)
			{
				String selectCountQuery ="select productName,originalProductquantity,soldProductQuantity,availableProductQuantity from medicine_details where productName=?";			
				PreparedStatement pst = conn.prepareStatement(selectCountQuery);
				pst.setString(1,productName);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) 
				{
					productNameUpdate= rs.getString("productName");
					originalProductquantity = rs.getInt("originalProductquantity");
					soldProductQuantity = rs.getInt("soldProductQuantity");
					availableProductQuantity = rs.getInt("availableProductQuantity");
				}
				soldProductQuantity=soldProductQuantity+value;
				availableProductQuantity=availableProductQuantity-value;
				String updateProductCountQurey = "UPDATE medicine_details SET soldProductQuantity=?,availableProductQuantity=? where productName =?;" ;
				PreparedStatement pst1 = conn.prepareStatement(updateProductCountQurey);
				pst1.setInt(1,soldProductQuantity);
				pst1.setInt(2,availableProductQuantity);
				pst1.setString(3,productNameUpdate);
				pst1.executeUpdate();
			}
			else
			{
				String selectCountQuery ="select productName,originalProductquantity,soldProductQuantity,availableProductQuantity from medicine_details where productName=?";			
				PreparedStatement pst = conn.prepareStatement(selectCountQuery);
				pst.setString(1,productName);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) 
				{
					productNameUpdate= rs.getString("productName");
					originalProductquantity = rs.getInt("originalProductquantity");
					soldProductQuantity = rs.getInt("soldProductQuantity");
					availableProductQuantity = rs.getInt("availableProductQuantity");
				}
				soldProductQuantity=soldProductQuantity+quantity;
				availableProductQuantity=availableProductQuantity-quantity;
				String updateProductCountQurey = "UPDATE medicine_details SET soldProductQuantity=?,availableProductQuantity=? where productName =?;" ;
				PreparedStatement pst1 = conn.prepareStatement(updateProductCountQurey);
				pst1.setInt(1,soldProductQuantity);
				pst1.setInt(2,availableProductQuantity);
				pst1.setString(3,productNameUpdate);
				pst1.executeUpdate();
			}
		}
		catch(Exception e)
		{}
	}
	public static void RemoveData()
	{
		try
		{
			getConnection();
			String truncateData = "TRUNCATE temp_table;";
			PreparedStatement pst = conn.prepareStatement(truncateData);
			pst.executeUpdate();
		}
		catch(Exception e)
		{}		
	}
	public static int selectAppointments()
	{
		int count = 0;
		try
		{
			getConnection();
			String selectTotalRowsQuery = "Select count(*) from book_appointment;"; 
			PreparedStatement pst = conn.prepareStatement(selectTotalRowsQuery);
			ResultSet rs = pst.executeQuery();
			rs.next();
			count = rs.getInt(1);
			count=count+1;			
		}
		catch(Exception e)
		{
			e.printStackTrace();		
		}
		return count;
	}
	public static String storeAppointment(String userName,String doctorSpeciality,String doctoreName,String doctorVistingDay,String appointmentDate,int id,String date)
	{
		String msg = "";
		String status = "Upcoming";
		try
		{
			getConnection();
			String addAppointmentQuery = "INSERT INTO  book_appointment(appointmentId,userName,doctorName,doctorVisitingDay,visitingDate,fixAppointmentDate,appointmentStatus)" + "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(addAppointmentQuery);
			pst.setInt(1,id);
			pst.setString(2,userName);
			pst.setString(3,doctoreName);
			pst.setString(4,doctorVistingDay);			
			pst.setString(5,appointmentDate);			
			pst.setString(6,date);
			pst.setString(7,status);
			pst.executeUpdate();
			msg = "Appointment has been scheduled succesfully...!";
		}
		catch(Exception e)
		{
			msg = "Error occurred while scheduling an appointment..!";
			e.printStackTrace();		
		}
		return msg;
	}
	public static void deleteOrder(int orderId,String orderName)
	{
		try
		{
			getConnection();
			String deleteOrderQuery ="Delete from customer_orders where orderId=? and orderName=?";
			PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
			pst.setInt(1,orderId);
			pst.setString(2,orderName);
			pst.executeUpdate();
		}
		catch(Exception e)
		{}
	}
	public static void reduceProductCount(String productName)
	{
		int originalProductCount=0;
		int soldProductCount=0;
		int availableProductCount=0;
		int quantity=0;
		String productNameUpdate="";
		try
		{
			getConnection();
			String selectCountQuery ="select productName,originalProductquantity,soldProductQuantity,availableProductQuantity from medicine_details where productName=?";			
			PreparedStatement pst = conn.prepareStatement(selectCountQuery);
			pst.setString(1,productName);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) 
			{
				productNameUpdate= rs.getString("productName");
				originalProductCount = rs.getInt("originalProductquantity");
				soldProductCount = rs.getInt("soldProductQuantity");
				availableProductCount = rs.getInt("availableProductQuantity");
			}
			
			String selectQuery ="select quantityOrderd from customer_orders where orderName=?";			
			PreparedStatement pstI = conn.prepareStatement(selectQuery);
			pstI.setString(1,productName);
			ResultSet rs1 = pstI.executeQuery();
			while (rs1.next()) 
			{
				quantity = rs1.getInt("quantityOrderd");
			}			
			soldProductCount=soldProductCount-quantity;
			availableProductCount=availableProductCount+quantity;
			String updateProductCountQurey = "UPDATE medicine_details SET soldProductQuantity=?,availableProductQuantity=? where productName =?;" ;
			PreparedStatement pst1 = conn.prepareStatement(updateProductCountQurey);
			pst1.setInt(1,soldProductCount);
			pst1.setInt(2,availableProductCount);
			pst1.setString(3,productNameUpdate);
			pst1.executeUpdate();
		}
		catch(Exception e)
		{}
	}
	public static HashMap<String, ArrayList<PatientAppointment>> selectAllAppointment()
	{
		HashMap<String, ArrayList<PatientAppointment>> patientAppointments=new HashMap<String, ArrayList<PatientAppointment>>();
		try
		{
			getConnection();
			String selectAppointmentQuery ="select * from book_appointment order by appointmentId asc;";			
			PreparedStatement pst = conn.prepareStatement(selectAppointmentQuery);
			ResultSet rs = pst.executeQuery();	
			ArrayList<PatientAppointment> orderList=new ArrayList<PatientAppointment>();
			while(rs.next())
			{
				if(!patientAppointments.containsKey(rs.getString("appointmentId")))
				{
					ArrayList<PatientAppointment> arr = new ArrayList<PatientAppointment>();
					patientAppointments.put(rs.getString("appointmentId"), arr);
				}
				ArrayList<PatientAppointment> listOrderPayment = patientAppointments.get(rs.getString("appointmentId"));		
				PatientAppointment order= new PatientAppointment(rs.getString("appointmentId"),rs.getString("userName"),rs.getString("doctorName"),rs.getString("doctorVisitingDay"),rs.getString("visitingDate"),rs.getString("fixAppointmentDate"),rs.getString("appointmentStatus"));
				listOrderPayment.add(order);	
			}
		}
		catch(Exception e)
		{}
		return patientAppointments;
	}
	public static void deleteAppointment(String appointmentId)
	{
		try
		{
			getConnection();
			String deleteAppointmentQuery ="Delete from book_appointment where appointmentId=?;";
			PreparedStatement pst = conn.prepareStatement(deleteAppointmentQuery);
			pst.setString(1,appointmentId);
			pst.executeUpdate();
		}
		catch(Exception e)
		{}
	}
	public static HashMap<String, ArrayList<PatientAppointment>> selectUpcomingAppointment(String doctorName)
	{
		HashMap<String, ArrayList<PatientAppointment>> patientAppointments=new HashMap<String, ArrayList<PatientAppointment>>();
		try
		{
			getConnection();
			String selectAppointmentQuery ="select * from book_appointment where appointmentStatus='Upcoming' and doctorName=?;";			
			PreparedStatement pst = conn.prepareStatement(selectAppointmentQuery);
			pst.setString(1,doctorName);
			ResultSet rs = pst.executeQuery();	
			ArrayList<PatientAppointment> orderList=new ArrayList<PatientAppointment>();
			while(rs.next())
			{
				if(!patientAppointments.containsKey(rs.getString("appointmentId")))
				{
					ArrayList<PatientAppointment> arr = new ArrayList<PatientAppointment>();
					patientAppointments.put(rs.getString("appointmentId"), arr);
				}
				ArrayList<PatientAppointment> listOrderPayment = patientAppointments.get(rs.getString("appointmentId"));		
				PatientAppointment order= new PatientAppointment(rs.getString("appointmentId"),rs.getString("userName"),rs.getString("doctorName"),rs.getString("doctorVisitingDay"),rs.getString("visitingDate"),rs.getString("fixAppointmentDate"),rs.getString("appointmentStatus"));
				listOrderPayment.add(order);	
			}
		}
		catch(Exception e)
		{}
		return patientAppointments;
	}
	public static HashMap<String, ArrayList<PatientAppointment>> selectVisitedAppointment(String doctorName)
	{
		HashMap<String, ArrayList<PatientAppointment>> patientAppointments=new HashMap<String, ArrayList<PatientAppointment>>();
		try
		{
			getConnection();
			String selectAppointmentQuery ="select * from book_appointment where appointmentStatus='Visited' and doctorName=?;";			
			PreparedStatement pst = conn.prepareStatement(selectAppointmentQuery);
			pst.setString(1,doctorName);
			ResultSet rs = pst.executeQuery();	
			ArrayList<PatientAppointment> orderList=new ArrayList<PatientAppointment>();
			while(rs.next())
			{
				if(!patientAppointments.containsKey(rs.getString("appointmentId")))
				{
					ArrayList<PatientAppointment> arr = new ArrayList<PatientAppointment>();
					patientAppointments.put(rs.getString("appointmentId"), arr);
				}
				ArrayList<PatientAppointment> listOrderPayment = patientAppointments.get(rs.getString("appointmentId"));		
				PatientAppointment order= new PatientAppointment(rs.getString("appointmentId"),rs.getString("userName"),rs.getString("doctorName"),rs.getString("doctorVisitingDay"),rs.getString("visitingDate"),rs.getString("fixAppointmentDate"),rs.getString("appointmentStatus"));
				listOrderPayment.add(order);	
			}
		}
		catch(Exception e)
		{}
		return patientAppointments;
	}
	
	public static String getEmailId(String userName)
	{
		ResultSet rs = null;
		String emailId=null;
		try
		{
			getConnection();
			//select the table 
			String selectUserInformation ="select * from Registration where username = ?;";		
			PreparedStatement pst = conn.prepareStatement(selectUserInformation);
			pst.setString(1,userName);
			rs = pst.executeQuery();	
			while(rs.next())
			{
				emailId=rs.getString("emailId");
			}
		}
		catch(Exception e)
		{}
		return emailId;
	}
	
	public static String addPrescription(String doctorName,String patientName,String natureOfIllness,String medicinePrescription,String additinalNotes,String prescriptionDate)
	{
		String msg = "";
		try
		{
			getConnection();
			int count=0;
			String selectTotalRowsQuery = "Select count(*) from patient_prescription;"; 
			PreparedStatement pstCount = conn.prepareStatement(selectTotalRowsQuery);
			ResultSet rs = pstCount.executeQuery();
			rs.next();
			count = rs.getInt(1);
			count=count+1;	
			String addPrescriptionQurey = "INSERT INTO  patient_prescription(prescriptionId,patientName,doctorName,natureOfIllness,medicinePrescription,additionalNotes,prescriptionDate)" + "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(addPrescriptionQurey);
			pst.setInt(1,count);
			pst.setString(2,patientName);
			pst.setString(3,doctorName);
			pst.setString(4,natureOfIllness);
			pst.setString(5,medicinePrescription);
			pst.setString(6,additinalNotes);
			pst.setString(7,prescriptionDate);
			pst.executeUpdate();
			msg = "Prescription has been added successfully...!";
		}
		catch(Exception e)
		{
			msg = "Error while adding prescription ..!";
			e.printStackTrace();		
		}
		return msg;
	}

	public static ResultSet selectPrescription(String userName)
	{
		ResultSet rs = null;
		try
		{
			getConnection(); 
			String selectPrescriptionInformation ="select * from patient_prescription where patientName = ?;";		
			PreparedStatement pst = conn.prepareStatement(selectPrescriptionInformation);
			pst.setString(1,userName);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}
	
	public static String updateDoctorProfile(String dname,String doctorDescription,String qualification,String yearsInPractice,String languages,double visitingPrice,String emailId,String contactNumber_1,String contactNumber_2,String pic)
	{
		String msg = "";
		try
		{
			getConnection();
			String updateDoctorQurey = "UPDATE doctor_details SET doctorImage=?,emailId=?,contactNumber_1=?,contactNumber_2=?,qualification=?,doctorDescription=?,yearsInPractice=?,languages=?,visitingPrice=? where Name =?;" ;
			PreparedStatement pst = conn.prepareStatement(updateDoctorQurey);
			pst.setString(1,pic);
			pst.setString(2,emailId);
			pst.setString(3,contactNumber_1);
			pst.setString(4,contactNumber_2);
			pst.setString(5,qualification);
			pst.setString(6,doctorDescription);
			pst.setString(7,yearsInPractice);
			pst.setString(8,languages);
			pst.setDouble(9,visitingPrice);
			pst.setString(10,dname);
			pst.executeUpdate();
			msg = "Doctor profile is updated successfully...!!";		
		}
		catch(Exception e)
		{
			msg = "Doctor profile cannot be updated...!";
			e.printStackTrace();	
		}
		return msg;	
	}
	
	public static ResultSet selectAllProductCount()
	{
		ResultSet rs = null;
		try
		{					
		getConnection();
		String selectOrderQuery ="select distinct(productName),productPrice,availableProductQuantity from medicine_details order by productName asc;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}
	
	public static ArrayList<ProductCount> selectProductCountsForChart()
	{
		ArrayList<ProductCount> productCountList = new ArrayList<ProductCount>();
		try 
		{
			getConnection();
			String selectOrderQuery ="select distinct(productName),availableProductQuantity from medicine_details order by productName asc;";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				ProductCount productCount = new ProductCount(rs.getString("productName"),rs.getInt("availableProductQuantity"));
				productCountList.add(productCount);
			}
			return productCountList;
		}
		catch(Exception e)
		{
			productCountList=null;
			return productCountList;
		}
	}
	
	public static ResultSet selectProductOnSale()
	{
		ResultSet rs = null;
		try
		{
			getConnection();
			String selectOrderQuery ="select distinct(productName),productPrice,discount from medicine_details where discount>0.0 order by productName asc;";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}

	public static ResultSet selectProductHavingManufacturerRebate()
	{
		ResultSet rs = null;
		try
		{
			getConnection();
			String selectOrderQuery ="select distinct(productName),productPrice,manufacturerRebate from medicine_details where manufacturerRebate>0.0 order by productName asc;";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}

	public static ResultSet selectTotalDailyTransaction()
	{
		ResultSet rs = null;
		try
		{					
			getConnection();
			String selectOrderQuery ="select orderDate,SUM(orderPayablePrice) as TotalSales from Customer_Orders group by orderDate order by orderDate asc;";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}

	public static ResultSet selectProductSold()
	{	
		ResultSet rs = null;
		try
		{					
			getConnection();
			String selectOrderQuery ="select count(*),orderName,SUM(orderPayablePrice) as totalOrderPayablePrice,SUM(quantityOrderd) as totalquantityOrdered from customer_orders group by orderName order by orderName asc;";
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			rs = pst.executeQuery();					
		}
		catch(Exception e)
		{}
		return rs;
	}
	
	public static HashMap<String,Medicine> getAllMedicines()
	{	
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();
			String selectMedicines="select * from  medicine_details;";
			PreparedStatement pst = conn.prepareStatement(selectMedicines);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{				
				Medicine medicine = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),medicine);
				medicine.setId(rs.getString("Id"));
				System.out.println(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{}
		return hm;			
	}
}