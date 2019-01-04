import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.io.*;


/* 
	OrderPayment class contains class variables username,ordername,price,image,address,creditcardno.

	OrderPayment  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	OrderPayment  class contains getters and setters for username,ordername,price,image,address,creditcardno
*/
//made all public.
public class UpdateCount implements Serializable{
	
	public String productName;
	public int originalProductCount;
	public int soldProductCount;
	
	public UpdateCount(String productName,int originalProductCount,int soldProductCount){
		
		this.productName=productName;
	 	this.originalProductCount=originalProductCount;
		this.soldProductCount=soldProductCount;
		}
	public UpdateCount()
	{}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getOriginalProductCount() {
		return originalProductCount;
	}

	public void setOriginalProductCount(int originalProductCount) {
		this.originalProductCount = originalProductCount;
	}
	public int getSoldProductCount() {
		return soldProductCount;
	}

	public void setSoldProductCount(int soldProductCount) {
		this.soldProductCount = soldProductCount;
	}

}
