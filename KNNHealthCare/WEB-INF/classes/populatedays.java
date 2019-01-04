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


@WebServlet("/populatedays")

public class populatedays extends HttpServlet
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
			String doctor = request.getParameter("doctor");
			StringBuffer sb = new StringBuffer();
			boolean namesAdded = false;
			if(doctor==null)	
			{
				context.getRequestDispatcher("/Error").forward(request, response);
			}
			else
			{
				AJAXPopulateDaysUtility a=new AJAXPopulateDaysUtility();
				sb=a.readdata(doctor);
				if(sb!=null || !sb.equals(""))
				{
					namesAdded=true;
				}
				if (namesAdded) 
				{
					response.setContentType("text/xml");
					response.getWriter().write("<days>"+sb.toString()+"</days>");
				} 
				else 
				{
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
			}
		}
		catch(Exception e)
		{}
    }
}
