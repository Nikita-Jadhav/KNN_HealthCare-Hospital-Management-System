import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

public class PatientAppointment implements Serializable
{
	public String appointmentId;
	public String userName;
	public String doctorName;
	public String doctorVisitingDay;
	public String visitingDate;
	public String fixAppointmentDate;
	public String appointmentStatus;
	
	public PatientAppointment(String appointmentId,String userName,String doctorName,String doctorVisitingDay,String visitingDate,String fixAppointmentDate,String appointmentStatus)
	{
		this.appointmentId = appointmentId;
		this.userName = userName;
		this.doctorName = doctorName;
	 	this.doctorVisitingDay = doctorVisitingDay;
		this.visitingDate = visitingDate;
		this.fixAppointmentDate = fixAppointmentDate;
		this.appointmentStatus = appointmentStatus;
	}
	public PatientAppointment()
	{}
	public String getAppointmentId()
	{
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId)
	{
		this.appointmentId = appointmentId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getDoctorName()
	{
		return doctorName;
	}

	public void setDoctorName(String doctorName)
	{
		this.doctorName = doctorName;
	}

	public String getDoctorVisitingDay()
	{
		return doctorVisitingDay;
	}

	public void setDoctorVisitingDay(String doctorVisitingDay)
	{
		this.doctorVisitingDay = doctorVisitingDay;
	}
	
	public String getVisitingDate()
	{
		return visitingDate;
	}

	public void setVisitingDate(String visitingDate)
	{
		this.visitingDate = visitingDate;
	}
	
	public String getFixAppointmentDate()
	{
		return fixAppointmentDate;
	}

	public void setFixAppointmentDate(String fixAppointmentDate)
	{
		this.fixAppointmentDate = fixAppointmentDate;
	}
	
	public String getAppointmentStatus()
	{
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus)
	{
		this.appointmentStatus = appointmentStatus;
	}
}