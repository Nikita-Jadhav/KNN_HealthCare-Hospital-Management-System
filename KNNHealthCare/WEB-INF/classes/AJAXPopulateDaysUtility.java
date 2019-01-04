import java.io.IOException;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;
import java.sql.*;

//@WebServlet("/AJAXPopulateDaysUtility")
public class AJAXPopulateDaysUtility
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
	public StringBuffer readdata(String doctor)
	{	
		HashMap<String,Day> data;
		data=getData(doctor);		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				Day p=(Day)pi.getValue();                   
				sb.append("<day>");
                sb.append("<id>" + p.getId() + "</id>");
                sb.append("<dayName>" + p.getVisitingDay() + "</dayName>");
                sb.append("</day>");
			}
       }	   
	   return sb;
	}
	public static HashMap<String,Day> getData(String doctor)
	{
		HashMap<String,Day> hm=new HashMap<String,Day>();
		try
		{
			String temp="ID";
			int i=1;
			getConnection();			
		    String selectproduct="select * from  doctors_visiting_details where Name=?;";
		    PreparedStatement pst = conn.prepareStatement(selectproduct);
			pst.setString(1,doctor);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				String temp1=temp+i;
				Day d = new Day(rs.getInt("visitingId"),rs.getString("visitingDay"));
				hm.put(temp1,d);
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
}
