import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;



/* 
	Users class contains class variables id,name,password,usertype.

	Users class has a constructor with Arguments name, String password, String usertype.
	  
	Users  class contains getters and setters for id,name,password,usertype.

*/

public class User implements Serializable{
	private int id;
	private String name;
	private String password;
	private String emailid;
	private String usertype;
	private String usertypecustomer;
	private String customername;
	
	
	
	public User(String name, String password, String usertype) {
		this.name=name;
		this.password=password;
		this.usertype=usertype;
	}
	public User(){}
/*public User(String tp)
	{
		String v=tp;
		if(v.equals("Customer"))
		{
		this.usertype=v;
		}
		if(!v.equals("Customer"))
		{
		this.name=v;
		}
	}*/
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public String getUsertypeCustomer() {
		return usertype;
	}

	public void setUsertypeCustomer(String usertype) {
		this.usertype = usertype;
	}
	
	public String getCustomerName() {
		return customername;
	}

	public void setCustomerName(String customername) {
		this.customername = customername;
	}
	public String getEmailId() {
		return emailid;
	}
	public void setEmailId(String emailid) {
		this.emailid = emailid;
	}
}
