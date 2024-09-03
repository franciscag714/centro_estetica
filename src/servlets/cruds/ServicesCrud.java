package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ServiceLogic;

import java.io.IOException;
import java.util.LinkedList;

import entities.Employee;
import entities.Service;

public class ServicesCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ServicesCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
			response.sendRedirect("index.html");        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
			ServiceLogic ctrl = new ServiceLogic();
			LinkedList<Service> services = ctrl.list();
			request.setAttribute("servicesList", services);
			request.getRequestDispatcher("WEB-INF/crud/services-crud.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
