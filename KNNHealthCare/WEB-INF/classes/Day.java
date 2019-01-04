import java.util.*;
import java.util.Map;

public class Day {
	private int id;
	private String visitingDay;
	
	public Day(int id,String visitingDay)
	{
		this.id=id;
		this.visitingDay=visitingDay;
	}
	
	public Day()
	{
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVisitingDay() {
		return visitingDay;
	}
	public void setVisitingDay(String visitingDay) {
		this.visitingDay = visitingDay;
	}
}
