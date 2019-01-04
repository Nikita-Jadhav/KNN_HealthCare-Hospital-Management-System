import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DoctorSpeciality")

public class DoctorSpeciality extends HttpServlet{
	private String Id;
	private String name;
	private String speciality;
	private String doctorImage;
	private String gender;
	private String languages;
	private String emailId;
	private String contactNumber_1;
	private String contactNumber_2;
	private String qualification;
	private String doctorDescription;
	private String yearsInPractice;
	private double visitingPrice;
	HashMap<String,String> visitingDetails;
	
	//HashMap<String,String> accessories;
	public DoctorSpeciality(String name, String speciality, String doctorImage, String gender, String languages, String emailId, String contactNumber_1, String contactNumber_2, String qualification, String doctorDescription, String yearsInPractice, double visitingPrice){
		//this.Id=Id;
		this.name=name;
		this.speciality = speciality;
		this.doctorImage=doctorImage;
		this.gender = gender;
		this.languages = languages;
		this.emailId = emailId;
		this.contactNumber_1 = contactNumber_1;
		this.contactNumber_2 = contactNumber_2;
		this.qualification =qualification;
		this.doctorDescription = doctorDescription;
		this.yearsInPractice = yearsInPractice;
		this.visitingPrice = visitingPrice;
		this.visitingDetails=new HashMap<String,String>();
        //this.accessories=new HashMap<String,String>();
	}
	
    /*HashMap<String,String> getAccessories() {
		return accessories;
		}*/
	public DoctorSpeciality(){
		
	}
	HashMap<String,String> getVisitingDetails() {
		return visitingDetails;
	}
	public void setVisitingDetails( HashMap<String,String> visitingDetails) {
		this.visitingDetails = visitingDetails;
	}
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getDoctorImage() {
		return doctorImage;
	}
	public void setDoctorImage(String doctorImage) {
		this.doctorImage = doctorImage;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNumber_1() {
		return contactNumber_1;
	}
	public void setContactNumber_1(String contactNumber_1) {
		this.contactNumber_1 = contactNumber_1;
	}
	/*public void setAccessories( HashMap<String,String> accessories) {
		this.accessories = accessories;
	}*/	
	public String getContactNumber_2() {
		return contactNumber_2;
	}
	public void setContactNumber_2(String contactNumber_2) {
		this.contactNumber_2 = contactNumber_2;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getDoctorDescription() {
		return doctorDescription;
	}
	public void setDoctorDescription(String doctorDescription) {
		this.doctorDescription = doctorDescription;
	}
	public String getYearsInPractice() {
		return yearsInPractice;
	}
	public void setYearsInPractice(String yearsInPractice) {
		this.yearsInPractice = yearsInPractice;
	}
	public double getVisitingPrice() {
		return visitingPrice;
	}
	public void setVisitingPrice(double visitingPrice) {
		this.visitingPrice = visitingPrice;
	}
}
