import java.util.*;
import java.util.Map;

public class Doctor {
	private String id;
	private String doctorName;
	/*private String lastName;
	private String speciality;*/
	
	public Doctor(String id,String doctorName)/*, String lastName, String speciality*/
	{
		this.id=id;
		this.doctorName=doctorName;
		/*this.lastName=lastName;
		this.speciality=speciality;*/
	}
	
	public Doctor(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	/*public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality =speciality;
	}*/
}
