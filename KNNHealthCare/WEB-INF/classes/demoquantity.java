import javax.servlet.ServletException;
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


@WebServlet("/demoquantity")

public class demoquantity extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String msg="";
		try
		{	
			String quantityUpdated = request.getParameter("quantity");
			String productName=request.getParameter("productName");
			if(quantityUpdated!=null || productName!=null)
			{
				response.setContentType("text/html");
				Quantity db=new Quantity();
				msg=db.storeData(quantityUpdated,productName);
				response.getWriter().write(generateQuantity(quantityUpdated,productName));
			}
		}
		catch(Exception e)
		{}
	}
	public String generateQuantity(String quantityUpdated,String productName)
	{
		StringBuffer sb = null;
		if(quantityUpdated==null || productName==null)
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
}