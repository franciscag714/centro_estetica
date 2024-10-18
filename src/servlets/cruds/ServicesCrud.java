package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Employee;
import entities.Service;
import entities.ServiceType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ServiceLogic;
import logic.ServiceTypeLogic;

public class ServicesCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ServicesCrud() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
			response.sendRedirect("index");
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
			ServiceLogic serviceLogic = new ServiceLogic();
			LinkedList<Service> services = serviceLogic.list();
			request.setAttribute("servicesList", services);
			
			ServiceTypeLogic typeLogic = new ServiceTypeLogic();
			LinkedList<ServiceType> types = typeLogic.list(false);
			request.setAttribute("typesList", types);
			
			request.getRequestDispatcher("WEB-INF/crud/services-crud.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
		{
			response.sendRedirect("index");
			return;
		}
		if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
			Service service = new Service();
			ServiceLogic logic = new ServiceLogic();
			String action = request.getParameter("action");
			
			try {
				if (action.equals("create"))
				{
					setData(request, service);
					logic.create(service);
				}
				else if (action.equals("update"))
				{
					service.setId(Integer.parseInt(request.getParameter("id")));
					setData(request, service);
					logic.update(service);
				}
				else if (action.equals("delete"))
				{
					service.setId(Integer.parseInt(request.getParameter("id")));
					logic.delete(service);
				}
			} catch (Exception e) { }
		}
		response.sendRedirect("abmc-servicios");
	}

	private void setData(HttpServletRequest request, Service service)
	{
		ServiceType type = new ServiceType();
		type.setId(Integer.parseInt(request.getParameter("type")));
		service.setType(type);
		
		service.setDescription(request.getParameter("description"));
		service.setUpdatedPrice(Double.parseDouble(request.getParameter("price")));
	}
}
