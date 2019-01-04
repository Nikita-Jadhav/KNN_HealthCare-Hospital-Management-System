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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import java.util.*;
import java.io.*;


@WebServlet("/autocompleteMedicine")

public class AutoCompleteMedicineServlet extends HttpServlet
{
    private ServletContext context;
    String searchMedicineId=null;	

    @Override
    public void init(ServletConfig config) throws ServletException
	{
        this.context = config.getServletContext();
	}

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
	{
		try
		{	
			String action = request.getParameter("action");
			searchMedicineId = request.getParameter("searchMedicineId");
			StringBuffer sb = new StringBuffer();
			if (searchMedicineId != null && action.equals("complete")) {
				searchMedicineId = searchMedicineId.trim().toLowerCase();
			} 
			if(searchMedicineId==null)	
			{
				context.getRequestDispatcher("/Error").forward(request, response);
			}
			boolean namesAdded = false;
			if (action.equals("complete")) 
			{
				if (!searchMedicineId.equals("")) 
				{
					AjaxUtility a=new AjaxUtility();
					sb=a.readMedicineData(searchMedicineId);
					if(sb!=null || !sb.equals(""))
					{
						namesAdded=true;
					}
					if (namesAdded) 
					{
						response.setContentType("text/xml");
						response.getWriter().write("<medicines>" + sb.toString() + "</medicines>");
					} 
					else 
					{
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
				}
			}
			if (action.equals("lookup"))
			{
				
				HashMap<String,Medicine> data=AjaxUtility.getMedicineData();     	
				if ((searchMedicineId != null) && data.containsKey(searchMedicineId.trim())) 
				{
					request.setAttribute("data",data.get(searchMedicineId.trim()));	
					RequestDispatcher rd=context.getRequestDispatcher("/MedicineData");
					rd.forward(request,response);
				}
			}
		}
		catch(Exception e)
		{
		}
    }
}
