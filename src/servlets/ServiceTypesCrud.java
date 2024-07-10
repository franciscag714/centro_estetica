package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ServiceTypeLogic;

import java.io.IOException;
import java.util.LinkedList;

import entities.Employee;
import entities.ServiceType;

public class ServiceTypesCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServiceTypesCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index.html");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
        	ServiceTypeLogic ctrl = new ServiceTypeLogic();
			LinkedList<ServiceType> types = ctrl.list();
			request.setAttribute("typesList", types);
			request.getRequestDispatcher("WEB-INF/service-types-crud.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
			Employee emp = (Employee) request.getSession().getAttribute("user");
			if (emp.getIsAdmin())
			{
				ServiceType type = new ServiceType();
				ServiceTypeLogic logic = new ServiceTypeLogic();
				String action = request.getParameter("action");
				
				if (action.equals("create"))
				{	
					type.setDescription(request.getParameter("description"));
					logic.create(type);
				}
				else if (action.equals("update"))
				{
					try {
						type.setId(Integer.parseInt(request.getParameter("id")));
						type.setDescription(request.getParameter("description"));
						logic.update(type);
					}
					catch (Exception e) { }
					
				}
				else if (action.equals("delete"))
				{
					try {
						type.setId(Integer.parseInt(request.getParameter("id")));
						logic.delete(type);
					}
					catch (Exception e) { }
				}
			}
		}
		doGet(request, response);
	}

}
