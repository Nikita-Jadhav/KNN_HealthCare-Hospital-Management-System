import java.io.IOException;
import java.io.*;


/* 
	Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

	Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
	  
	Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/	
			

public class ReviewM implements Serializable{
	private String productName;
	private String userName;
	private String productType;
	private String productMaker;
	private String reviewRating;
	private String reviewDate;
	private String reviewText;
	private String retailerpin;
	private String price;
	private String retailerCity;
	private String manufacturerRebate;
	private String retailerState;
//	private String productOnSale;
	private String userAge;
	private String userGender;
//  private String userOccupation;
	
	public ReviewM (String userName,String productName,String productType,String price,String productMaker,String reviewRating,
	String manufacturerRebate,String retailerpin,String retailerCity,String retailerState,
	String reviewDate,String userAge,String userGender,String reviewText){
		this.productName=productName;
		this.userName=userName;
		this.productType=productType;
		this.productMaker=productMaker;
	 	this.reviewRating=reviewRating;
		this.reviewDate=reviewDate;
	 	this.reviewText=reviewText;
		this.retailerpin=retailerpin;
		this.price= price;
		this.retailerCity= retailerCity;
		this.manufacturerRebate=manufacturerRebate;
		this.retailerState=retailerState;
//		this.productOnSale=productOnSale;
		this.userAge=userAge;
		this.userGender=userGender;
//		this.userOccupation=userOccupation;
	}

	public ReviewM(String productName, String retailerCity, String reviewRating, String reviewText) {
       this.productName = productName;
       this.retailerCity = retailerCity;
       this.reviewRating = reviewRating;
       this.reviewText = reviewText;
    }
	
	
	
	public String getuserGender() {
		return userGender;
	}

	public void setuserGender(String userGender) {
		this.userGender = userGender;
	}
	
	
	public String getuserAge() {
		return userAge;
	}

	public void setuserAge(String userAge) {
		this.userAge = userAge;
	}
	
	public String getmanufacturerRebate() {
		return manufacturerRebate;
	}

	public void setmanufacturerRebate(String manufacturerRebate) {
		this.manufacturerRebate = manufacturerRebate;
	}
	
	public String getretailerState() {
		return retailerState;
	}

	public void setretailerState(String retailerState) {
		this.retailerState = retailerState;
	}
	
	public String getProductName() {
		return productName;
	}
	public String getUserName() {
		return userName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductMaker() {
		return productMaker;
	}

	public void setProductMaker(String productMaker) {
		this.productMaker = productMaker;
	}

	public String getReviewRating() {
		return reviewRating;
	}

	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
    
	public String getRetailerPin() {
		return retailerpin;
	}

	public void setRetailerPin(String retailerpin) {
		this.retailerpin = retailerpin;
	}
	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
