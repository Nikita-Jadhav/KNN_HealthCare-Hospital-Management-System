import java.io.IOException;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;
import java.sql.*;

//@WebServlet("/Quantity")
public class Quantity
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
			 message="unsuccessful";
		     return message;
		}
	}
	public static String storeData(String quantityUpdated,String productName)
	{
		String msg="";
		String value=null;
		try
		{		
			getConnection();
			String selectIntoProductQuery = "select * from temp_table where productName=?;";	
			PreparedStatement pst = conn.prepareStatement(selectIntoProductQuery);
			pst.setString(1,productName);	
			ResultSet rs = pst.executeQuery();
			if(!rs.next())
			{
				String insertIntoProductQuery = "INSERT INTO temp_table(productName,quantity)"
				+ "VALUES (?,?);";	
				PreparedStatement pstI = conn.prepareStatement(insertIntoProductQuery);
				pstI.setString(1,productName);
				pstI.setInt(2,Integer.parseInt(quantityUpdated));
				pstI.execute();
				msg="Success";
				return msg;
			}
			else
			{				
				while(rs.next())
				{
					value=rs.getString("productName");
				}
				String updateIntoProductQuery = "Update temp_table set quantity=? where productName=?;";
				PreparedStatement pstII = conn.prepareStatement(updateIntoProductQuery);
				pstII.setInt(1,Integer.parseInt(quantityUpdated));
				pstII.setString(2,productName);
				pstII.executeUpdate();
				msg="Success";
				return msg;	
			}			
		}
		catch(Exception e)
		{	
	
		}
		msg="Error";
		return msg;	
	}
}
