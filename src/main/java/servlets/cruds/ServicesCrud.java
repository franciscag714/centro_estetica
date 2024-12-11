package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Alert;
import entities.Client;
import entities.Employee;
import entities.Person;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Client || !((Employee) user).isAdmin()) {
			response.sendRedirect("index");
			return;
		}

		// user is an administrator Employee
		ServiceLogic serviceLogic = new ServiceLogic();
		LinkedList<Service> services = serviceLogic.list();
		request.setAttribute("servicesList", services);

		ServiceTypeLogic typeLogic = new ServiceTypeLogic();
		LinkedList<ServiceType> types = typeLogic.list(false);
		request.setAttribute("typesList", types);

		request.getRequestDispatcher("WEB-INF/crud/services-crud.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Client || !((Employee) user).isAdmin()) {
			response.sendRedirect("index");
			return;
		}

		// user is an administrator Employee
		Service service = new Service();
		ServiceLogic logic = new ServiceLogic();
		String action = request.getParameter("action");

		try {
			if (action.equals("create")) {
				setData(request, service);
				service = logic.create(service);

				if (service != null)
					request.setAttribute("alert", new Alert("success", "Se ha creado el nuevo servicio."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido crear el nuevo servicio."));

			} else if (action.equals("update")) {
				service.setId(Integer.parseInt(request.getParameter("id")));
				setData(request, service);
				service = logic.update(service);

				if (service != null)
					request.setAttribute("alert", new Alert("success", "Se ha modificado el servicio."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido modificar el servicio."));

			} else if (action.equals("delete")) {
				service.setId(Integer.parseInt(request.getParameter("id")));
				service = logic.delete(service);

				if (service != null)
					request.setAttribute("alert", new Alert("success", "Se ha eliminado el servicio."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido eliminar el servicio."));
			}

		} catch (Exception e) {
		}

		this.doGet(request, response);
	}

	private void setData(HttpServletRequest request, Service service) {
		ServiceType type = new ServiceType();
		type.setId(Integer.parseInt(request.getParameter("type")));
		service.setType(type);

		service.setDescription(request.getParameter("description"));
		service.setUpdatedPrice(Double.parseDouble(request.getParameter("price")));
	}
}
