<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*;import java.util.*;"%>
<!DOCTYPE HTML">
<div id="container">
	<h2 style="color:#337ab7;margin-left:870px;font-size:35px;font-family:Arial, Helvetica, sans-serif;">Welcome to KNN HealthCare</h2>
	<br><br>
	<img src="images/carousel/doctors.png" alt="Los Angeles" style="width:60%;margin-left:228px">
	
	<br><br><br>
	<div id="bookappointmentHeader">
		<br>
		<h3 style="color:#4c4c4c;margin-left:650px;margin-top:-3px;font-size:30px;font-family:Arial, Helvetica, sans-serif;">Book an Appointment</h3>
	</div>
	<div id="appointment">
		<form name ='BookAppointmentForm' action='BookAppointment' method='post'>
		<table id="complete-table" class="gridtable"
						style="position: absolute;width: 300px;visibilty:hidden;"></table>
		<select class="ddlSpecialty" name="ddlSpecialty" id="ddlSpecialty">
			<option selected="selected" value="-1">Search by Speciality</option>
			<%
			try
			{				
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/knnhealthcare","root","root");
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("select distinct(speciality) from doctor_details;");
				while(rs.next())
				{
					%>
					<option><%=rs.getString("speciality")%></option>
					<%
				}								
			}
			catch(Exception e){}
			%>		
		</select>
		<!--<input type="submit" value="Go" onchange="return abc();"/> -->
		<select name="ddlDoctor" id="ddlDoctor" class="ddlDoctor">
			<option selected="selected" value="-1">Select Doctor</option>
		</select>
		<table id="completetable" class="gridtable"
						style="position: absolute;width: 300px;visibilty:hidden;"></table>
		<select name="ddlDay" id="ddlDay">
			<option value="0">Select Day</option>
		</select>
		<input type='date' name='appointmentDate' style='margin-left: 60px;border-radius:5px;font-size:20px;height:40px;width: 20%;' required>
		<h4 style='margin-left:1191px;color:red;'>Note: Please select future date for selected day.</h4>
		<input type="submit" name="btnBookAppointment" value="BOOK NOW" id="btnBookAppointment"/>
		</form>
	</div>
</div>