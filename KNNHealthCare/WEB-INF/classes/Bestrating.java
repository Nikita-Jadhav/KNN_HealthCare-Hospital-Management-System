import java.io.*;
public class Bestrating
{
String doctorname ;
String rating;


public  Bestrating(String doctorname,String rating)
{
	
	this.doctorname = doctorname ;
    this.rating = rating;
}


public String getDoctorname(){
 return doctorname;
}

public String getRating () {
 return rating;
}
}