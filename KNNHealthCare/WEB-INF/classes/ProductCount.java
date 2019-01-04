import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

//made all public.
public class ProductCount implements Serializable{
	public String productName;
	//public double productPrice;
	//public int availableProductCount;
	public int originalProductQuantity;
	public int soldProductQuantity;
	public int availableProductQuantity;
	
	public ProductCount(String productName,int availableProductQuantity){
		this.productName=productName;
		//this.productPrice=productPrice;
		this.availableProductQuantity=availableProductQuantity;
		//this.originalProductCount=originalProductCount;
		//this.soldProductCount=soldProductCount;
		}
	public ProductCount()
	{}
	public int getAvailableProductQuantity() {
		return availableProductQuantity;
	}

	public void setAvailableProductQuantity(int availableProductQuantity) {
		this.availableProductQuantity= availableProductQuantity;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getSoldProductQuantity() {
		return soldProductQuantity;
	}

	public void setSoldProductQuantity(int soldProductQuantity) {
		this.soldProductQuantity = soldProductQuantity;
	}
	
	public int getOriginalProductQuantity() {
		return originalProductQuantity;
	}

	public void setOriginalProductQuantity(int originalProductQuantity) {
	this.originalProductQuantity= originalProductQuantity;
	}
	/*
	public int getAvailableProductCount() {
		return availableProductCount;
	}

	public void setAvailableProductCount(int availableProductCount) {
	this.availableProductCount = availableProductCount;
	}
*/
}
