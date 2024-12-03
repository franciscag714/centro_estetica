package servlets;

import java.io.IOException;
import java.util.LinkedList;

import entities.ServiceType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ServiceTypeLogic;

public class OurServices extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OurServices() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServiceTypeLogic logic = new ServiceTypeLogic();
		LinkedList<ServiceType> serviceTypes = logic.list(true);
		request.setAttribute("serviceTypesList", serviceTypes);
		request.getRequestDispatcher("WEB-INF/our-services.jsp").forward(request, response);
	}
}
