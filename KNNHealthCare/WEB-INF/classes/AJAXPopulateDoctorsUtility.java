import java.io.IOException;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;
import java.sql.*;

//@WebServlet("/AJAXPopulateDoctorsUtility")
public class AJAXPopulateDoctorsUtility
{	
	static Connection conn = null;
	static String message;
	StringBuffer sb = new StringBuffer();
	boolean namesAdded = false;
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
	public StringBuffer readdata(String speciality)
	{	
		HashMap<String,Doctor> data;
		data=getData(speciality);		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				Doctor p=(Doctor)pi.getValue();                   
				sb.append("<doctor>");
                sb.append("<id>" + p.getId() + "</id>");
                sb.append("<doctorName>" + p.getDoctorName() + "</doctorName>");
                sb.append("</doctor>");
			}
       }	   
	   return sb;
	}
	public static HashMap<String,Doctor> getData(String speciality)
	{
		HashMap<String,Doctor> hm=new HashMap<String,Doctor>();
		try
		{
			getConnection();			
		    String selectproduct="select * from  doctor_details where speciality=?;";
		    PreparedStatement pst = conn.prepareStatement(selectproduct);
			pst.setString(1,speciality);	
			ResultSet rs = pst.executeQuery();			
			
			while(rs.next())
			{	
				Doctor d = new Doctor(rs.getString("Id"),rs.getString("Name"));
				hm.put(rs.getString("Id"),d);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
}
