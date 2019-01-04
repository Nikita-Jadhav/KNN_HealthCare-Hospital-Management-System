import java.io.*;
import java.sql.*;
import java.io.IOException;
import java.util.*;

public class MedicineRecommenderUtility{
	
	static Connection conn = null;
    static String message;
	
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/knnhealthcare","root","root");							
			message="Successfull";
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

	public HashMap<String,String> readOutputFile(){

		String TOMCAT_HOME = System.getProperty("catalina.home");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		try {

            br = new BufferedReader(new FileReader(new File(TOMCAT_HOME+"\\webapps\\knnhealthcare\\output.csv")));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] prod_recm = line.split(cvsSplitBy,2);
				prodRecmMap.put(prod_recm[0],prod_recm[1]);
            }
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return prodRecmMap;
	}
	
	public static Medicine getMedicine(String medicine){
		Medicine prodObj = new Medicine();
		try 
		{
			String msg = getConnection();
			String selectProd="select * from  medicine_details where Id=?";
			PreparedStatement pst = conn.prepareStatement(selectProd);
			pst.setString(1,medicine);
			ResultSet rs = pst.executeQuery();
		
			while(rs.next())
			{	
				prodObj = new Medicine(rs.getString("productName"),rs.getString("productType"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("manufactureDate"),rs.getString("bestBeforeDate"),rs.getString("description"),rs.getDouble("discount"),rs.getInt("originalProductquantity"),rs.getInt("soldProductQuantity"),rs.getInt("availableProductQuantity"),rs.getDouble("manufacturerRebate"));
			}
			rs.close();
			pst.close();
			conn.close();
		}
		catch(Exception e)
		{
		}
		return prodObj;	
	}
}