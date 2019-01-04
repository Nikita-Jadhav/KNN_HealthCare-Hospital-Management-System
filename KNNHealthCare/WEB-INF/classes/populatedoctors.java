/*import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.text.*;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.io.*;


@WebServlet("/populatedoctors")

public class populatedoctors extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String msg="";
		try
		{	
			String speciality = request.getParameter("speciality");
			if(speciality!=null)
			{
				response.setContentType("text/html");
				response.getWriter().write(Doctors(speciality));
			}
		}
		catch(Exception e)
		{}
	}
	public String Doctors(String speciality)
	{
		StringBuffer sb = null;
		if(speciality==null)
		{
			sb = new StringBuffer("{\"quantity\":{");
			sb.append("\"name\":\"nothing found!\",");
			sb.append("}}");
		}
		else
		{
			sb = new StringBuffer("{\"quantity\":{");
			sb.append("\"name\":\""+quantityUpdated+"\",");
			sb.append("\"pname\":\""+productName+"\"");
			sb.append("}}");
		}
		
		return sb.toString();
	}
}*/
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;


@WebServlet("/populatedoctors")

public class populatedoctors extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();

	    }
		
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
	{
		try
		{	
			String speciality = request.getParameter("speciality");
			StringBuffer sb = new StringBuffer();
			boolean namesAdded = false;
			if(speciality==null)	
			{
				context.getRequestDispatcher("/Error").forward(request, response);
			}
			else
			{
				AJAXPopulateDoctorsUtility a=new AJAXPopulateDoctorsUtility();
				sb=a.readdata(speciality);
				if(sb!=null || !sb.equals(""))
				{
					namesAdded=true;
				}
				if (namesAdded) 
				{
					response.setContentType("text/xml");
					response.getWriter().write("<doctors>"+sb.toString()+"</doctors>");
				} 
				else 
				{
					//nothing to show
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
				/*HashMap<String,Doctor> data=AJAXPopulateDoctorsUtility.getData(speciality);     	
				if (speciality != null) 
				{
					request.setAttribute("data",data.get(speciality));	
					RequestDispatcher rd=context.getRequestDispatcher("/DoctorData");
					rd.forward(request,response);
				}*/
			}
		}
		catch(Exception e)
		{}
    }
}
