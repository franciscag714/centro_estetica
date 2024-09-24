package servlets;

import java.io.IOException;
import java.util.LinkedList;

import entities.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ServiceLogic;

public class OurServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public OurServices() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ServiceLogic ctrl = new ServiceLogic();
		LinkedList<Service> services = ctrl.list();
		request.setAttribute("servicesList", services);
		request.getRequestDispatcher("WEB-INF/our-services.jsp").forward(request, response);
	}
}
