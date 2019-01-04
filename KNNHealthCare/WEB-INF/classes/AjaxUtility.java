import java.io.*;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;

import java.sql.*;

import java.io.IOException;
import java.io.*;


//@WebServlet("/AjaxUtility")
public class AjaxUtility
{
	StringBuffer sb = new StringBuffer();
	boolean namesAdded = false;
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
			 message="unsuccessful";
		     return message;
		}
	}
	
	public  StringBuffer readdata(String searchId)
	{	
		HashMap<String,DoctorSearch> data;
		data=getData();		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				DoctorSearch p=(DoctorSearch)pi.getValue();                   
                if (p.getName().toLowerCase().startsWith(searchId))
                {
                    sb.append("<doctor>");
                    sb.append("<id>" + p.getId() + "</id>");
                    sb.append("<doctorName>" + p.getName() + "</doctorName>");
                    sb.append("</doctor>");
                }
			}
       }	   
	   return sb;
	}
	
	public  StringBuffer readMedicineData(String searchId)
	{	
		HashMap<String,Medicine> data;
		data=getMedicineData();		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				Medicine p=(Medicine)pi.getValue();                   
                if (p.getProductName().toLowerCase().startsWith(searchId))
                {
                    sb.append("<medicine>");
                    sb.append("<id>" + p.getId() + "</id>");
                    sb.append("<medicineName>" + p.getProductName() + "</medicineName>");
                    sb.append("</medicine>");
                }
			}
       }	   
	   return sb;
	}
	
	public static HashMap<String,DoctorSearch> getData()
	{
		HashMap<String,DoctorSearch> hm=new HashMap<String,DoctorSearch>();
		try
		{
			getConnection();			
		    String selectDoctor="select * from  doctor_details;";
		    PreparedStatement pst = conn.prepareStatement(selectDoctor);
			ResultSet rs = pst.executeQuery();			
			while(rs.next())
			{
				DoctorSearch d = new DoctorSearch(rs.getString("Id"),rs.getString("Name"),rs.getString("speciality"),rs.getString("doctorImage"),rs.getString("gender"),rs.getString("languages"),rs.getString("emailId"),rs.getString("contactNumber_1"),rs.getString("contactNumber_2"),rs.getString("qualification"),rs.getString("doctorDescription"),rs.getString("yearsInPractice"),rs.getDouble("visitingPrice"));
				hm.put(rs.getString("Id"), d);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
	public static HashMap<String,Medicine> getMedicineData()
	{
		HashMap<String,Medicine> hm=new HashMap<String,Medicine>();
		try
		{
			getConnection();			
		    String selectDoctor="select * from  medicine_details;";
		    PreparedStatement pst = conn.prepareStatement(selectDoctor);
			ResultSet rs = pst.executeQuery();			
			while(rs.next())
			{				
				Medicine m = new Medicine(rs.getString("Id"),rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
				hm.put(rs.getString("Id"),m);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
	
	public static void storeData(HashMap<String,DoctorSearch> doctorData)
	{
		try
		{
			getConnection();				
			String insertIntoDoctorQuery = "INSERT INTO doctor_details(Id,Name,speciality,doctorImage,gender,languages,emailId,contactNumber_1,contactNumber_2,qualification,doctorDescription,yearsInPractice,"+
			"visitingPrice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";	
			for(Map.Entry<String, DoctorSearch> entry : doctorData.entrySet())
			{
				PreparedStatement pst = conn.prepareStatement(insertIntoDoctorQuery);
				pst.setString(1,entry.getValue().getId());
				pst.setString(2,entry.getValue().getName());
				pst.setString(3,entry.getValue().getSpeciality());
				pst.setString(4,entry.getValue().getDoctorImage());
				pst.setString(5,entry.getValue().getGender());
				pst.setString(6,entry.getValue().getLanguages());			
				pst.setString(7,entry.getValue().getEmailId());
				pst.setString(8,entry.getValue().getContactNumber_1());
				pst.setString(9,entry.getValue().getContactNumber_2());
				pst.setString(10,entry.getValue().getQualification());
				pst.setString(11,entry.getValue().getDoctorDescription());
				pst.setString(12,entry.getValue().getYearsInPractice());
				pst.setDouble(13,entry.getValue().getVisitingPrice());
				pst.execute();
			}
		}
		catch(Exception e)
		{}		
	}
}