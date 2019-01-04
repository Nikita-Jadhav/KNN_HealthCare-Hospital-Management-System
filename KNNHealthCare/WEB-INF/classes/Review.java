import java.io.IOException;
import java.io.*;


/* 
	Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

	Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
	  
	Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/	
			

public class Review implements Serializable{
	private String doctorName;
	private String userName;
	private String specialityCategory;
	private String reviewRating;
	private String reviewDate;
	private String reviewText;
	//private String retailerpin;
	//private String retailerCity;
	//private String manufacturerRebate;
	//private String retailerState;
	//private String productOnSale;
	private String patientAge;
	private String patientGender;
	//private String userOccupation;
	
	public Review (String userName,String doctorName,String specialityCategory,String reviewRating,
	String reviewDate,String patientAge,String patientGender,String reviewText){
		this.doctorName=doctorName;
		this.userName=userName;
		this.specialityCategory=specialityCategory;
		//this.productMaker=productMaker;
	 	this.reviewRating=reviewRating;
		this.reviewDate=reviewDate;
	 	this.reviewText=reviewText;
		//this.retailerpin=retailerpin;
		//this.price= price;
		//this.retailerCity= retailerCity;
		//this.manufacturerRebate=manufacturerRebate;
		//this.retailerState=retailerState;
		//this.productOnSale=productOnSale;
		this.patientAge=patientAge;
		this.patientGender=patientGender;
		//this.userOccupation=userOccupation;
	}

	public Review(String doctorName, String reviewRating, String reviewText) {
       this.doctorName = doctorName;
       this.reviewRating = reviewRating;
       this.reviewText = reviewText;
    }
	
	
/*	public String getuserOccupation() {
		return userOccupation;
	}

	public void setuserOccupation(String userOccupation) {
		this.userOccupation = userOccupation;
	} */
	
	public String getpatientGender() {
		return patientGender;
	}

	public void setpatientGender(String patientGender) {
		this.patientGender = patientGender;
	} 
	
/*	public String getproductOnSale() {
		return productOnSale;
	}

	public void setproductOnSale(String productOnSale) {
		this.productOnSale = productOnSale;
	}*/
	/*
	public String getpatientGender() {
		return patientGender;
	}

	public void setpatientGender(String patientGender) {
		this.patientGender = patientGender;
	}*/
	/*
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
	*/
	
	public String getdoctorName() {
		return doctorName;
	}
	public String getUserName() {
		return userName;
	}

	public void setdoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getspecialityCategory() {
		return specialityCategory;
	}

	public void setspecialityCategory(String specialityCategory) {
		this.specialityCategory = specialityCategory;
	}
/*
	public String getProductMaker() {
		return productMaker;
	}

	public void setProductMaker(String productMaker) {
		this.productMaker = productMaker;
	}  */

	public String getReviewRating() {
		return reviewRating;
	}

	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public void setuserName(String userName) {
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
    /*
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
	}*/

}
