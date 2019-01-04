import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

public class OrderPayment implements Serializable
{
	public int orderId;
	public String userName;
	public String orderName;
	public double orderOriginalPrice;
	public double orderDiscount;
	public double orderManufecturarRebate;
	public double orderPayablePrice;
	public String userAddress;
	public String creditCardNo;
	public String creditCardName;
	public String expiry;
	public String cvv;
	public String orderDate;
	public String deliveryDate;
	public String orderStatus;
	public int quantity;
	
	public OrderPayment(int orderId,String userName,String orderName,double orderOriginalPrice,double orderDiscount,double orderManufecturarRebate,double orderPayablePrice,String userAddress,String creditCardNo,String creditCardName,String expiry,String cvv,String orderDate,String deliveryDate,String orderStatus)
	{
		this.orderId=orderId;
		this.userName=userName;
		this.orderName=orderName;
	 	this.orderOriginalPrice=orderOriginalPrice;
		this.orderDiscount=orderDiscount;
		this.orderManufecturarRebate=orderManufecturarRebate;
		this.orderPayablePrice=orderPayablePrice;
		this.userAddress=userAddress;
	 	this.creditCardNo=creditCardNo;
		this.creditCardName=creditCardName;
		this.expiry=expiry;
		this.cvv=cvv;
		this.orderDate=orderDate;
		this.deliveryDate=deliveryDate;
		this.orderStatus=orderStatus;
	}
	public OrderPayment()
	{}
	public String getUserAddress()
	{
		return userAddress;
	}

	public void setUserAddress(String userAddress)
	{
		this.userAddress = userAddress;
	}
	
	public String getCreditCardName()
	{
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName)
	{
		this.creditCardName = creditCardName;
	}

	public String getCreditCardNo()
	{
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo)
	{
		this.creditCardNo = creditCardNo;
	}
	
	public String getExpiry()
	{
		return expiry;
	}

	public void setExpiry(String expiry)
	{
		this.expiry = expiry;
	}
	
	public String getCVV()
	{
		return cvv;
	}

	public void setCVV(String cvv)
	{
		this.cvv = cvv;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public double getOrderOriginalPrice() {
		return orderOriginalPrice;
	}

	public void setOrderOriginalPrice(double orderOriginalPrice) {
		this.orderOriginalPrice = orderOriginalPrice;
	}
	public double getOrderDiscount() {
		return orderDiscount;
	}

	public void setOrderDiscount(double orderDiscount) {
		this.orderDiscount = orderDiscount;
	}
	public double getOrderManufecturarRebate() {
		return orderManufecturarRebate;
	}

	public void setOrderManufecturarRebate(double orderManufecturarRebate) {
		this.orderManufecturarRebate = orderManufecturarRebate;
	}
	public double getOrderPayablePrice() {
		return orderPayablePrice;
	}

	public void setOrderPayablePrice(double orderPayablePrice) {
		this.orderPayablePrice = orderPayablePrice;
	}
	
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
	this.orderDate = orderDate;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
	this.deliveryDate = deliveryDate;
	}
}
