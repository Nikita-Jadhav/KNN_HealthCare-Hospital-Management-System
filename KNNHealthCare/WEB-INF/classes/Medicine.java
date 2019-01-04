import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@WebServlet("/Medicine")

public class Medicine extends HttpServlet{
	private String Id;
	private String productName;
	private String productType;
	private double productPrice;
	private String productImage;
	private String productManufacturer;
	private String manufactureDate;
	private String bestBeforeDate;
	private String description;
	private double discount;
	private int originalProductquantity;
	private int soldProductQuantity;
	private int availableProductQuantity;
	private double manufacturerRebate;
	HashMap<String,String> accessories;
	
	public Medicine(String productName, String productType, double productPrice, String productImage, String productManufacturer, String manufactureDate, String bestBeforeDate, String description, double discount, int originalProductquantity, int soldProductQuantity, int availableProductQuantity, double manufacturerRebate){
		//this.Id=Id;
		this.productName=productName;
		this.productType=productType;
		this.productPrice = productPrice;
		this.productImage=productImage;
		this.productManufacturer = productManufacturer;
		this.manufactureDate = manufactureDate;
		this.bestBeforeDate = bestBeforeDate;
		this.description = description;
		this.discount = discount;
		this.originalProductquantity =originalProductquantity;
		this.soldProductQuantity = soldProductQuantity;
		this.availableProductQuantity = availableProductQuantity;
		this.manufacturerRebate = manufacturerRebate;
        this.accessories=new HashMap<String,String>();
	}
	
	public Medicine(String Id,String productName, String productType, double productPrice, String productImage, String productManufacturer, String manufactureDate, String bestBeforeDate, String description, double discount, int originalProductquantity, int soldProductQuantity, int availableProductQuantity, double manufacturerRebate){
		this.Id=Id;
		this.productName=productName;
		this.productType=productType;
		this.productPrice = productPrice;
		this.productImage=productImage;
		this.productManufacturer = productManufacturer;
		this.manufactureDate = manufactureDate;
		this.bestBeforeDate = bestBeforeDate;
		this.description = description;
		this.discount = discount;
		this.originalProductquantity =originalProductquantity;
		this.soldProductQuantity = soldProductQuantity;
		this.availableProductQuantity = availableProductQuantity;
		this.manufacturerRebate = manufacturerRebate;
        this.accessories=new HashMap<String,String>();
	}
	
    HashMap<String,String> getAccessories()
	{
		return accessories;
	}
	public void setAccessories( HashMap<String,String> accessories) {
		this.accessories = accessories;
	}
	public Medicine(){
		
	}
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public String getProductName() {
		return productName;
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
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductManufacturer() {
		return productManufacturer;
	}
	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}
	public String getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public String getBestBeforeDate() {
		return bestBeforeDate;
	}
	public void setBestBeforeDate(String bestBeforeDate) {
		this.bestBeforeDate = bestBeforeDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getOriginalProductquantity() {
		return originalProductquantity;
	}
	public void setOriginalProductquantity(int originalProductquantity) {
		this.originalProductquantity = originalProductquantity;
	}
	public int getSoldProductQuantity() {
		return soldProductQuantity;
	}
	public void setSoldProductQuantity(int soldProductQuantity) {
		this.soldProductQuantity = soldProductQuantity;
	}
	public int getAvailableProductQuantity() {
		return availableProductQuantity;
	}
	public void setAvailableProductQuantity(int availableProductQuantity) {
		this.availableProductQuantity = availableProductQuantity;
	}
	public double getManufacturerRebate() {
		return manufacturerRebate;
	}
	public void setManufacturerRebate(double manufacturerRebate) {
		this.manufacturerRebate = manufacturerRebate;
	}
}
