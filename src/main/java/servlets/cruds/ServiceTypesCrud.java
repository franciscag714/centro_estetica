package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Alert;
import entities.Client;
import entities.Employee;
import entities.Person;
import entities.ServiceType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ServiceTypeLogic;

public class ServiceTypesCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServiceTypesCrud() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Client) {
			response.sendRedirect("index");
			return;
		}

		// user is Employee
		ServiceTypeLogic ctrl = new ServiceTypeLogic();
		LinkedList<ServiceType> types = ctrl.list(false);
		request.setAttribute("typesList", types);
		request.getRequestDispatcher("WEB-INF/crud/service-types-crud.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Client) {
			response.sendRedirect("index");
			return;
		}

		// user is Employee
		Employee emp = (Employee) user;

		if (emp.getIsAdmin()) {
			ServiceType type = new ServiceType();
			ServiceTypeLogic logic = new ServiceTypeLogic();
			String action = request.getParameter("action");

			try {
				if (action.equals("create")) {
					type.setDescription(request.getParameter("description"));
					type = logic.create(type);

					if (type != null)
						request.setAttribute("alert", new Alert("success", "Se ha creado el nuevo tipo de servicio."));
					else
						request.setAttribute("alert",
								new Alert("error", "No se ha podido crear un nuevo tipo de servicio."));

				} else if (action.equals("update")) {
					type.setId(Integer.parseInt(request.getParameter("id")));
					type.setDescription(request.getParameter("description"));
					type = logic.update(type);

					if (type != null)
						request.setAttribute("alert", new Alert("success", "Se ha modificado el tipo de servicio."));
					else
						request.setAttribute("alert",
								new Alert("error", "No se ha podido modificar el tipo de servicio."));

				} else if (action.equals("delete")) {
					type.setId(Integer.parseInt(request.getParameter("id")));
					type = logic.delete(type);

					if (type != null)
						request.setAttribute("alert", new Alert("success", "Se ha eliminado el tipo de servicio."));
					else
						request.setAttribute("alert",
								new Alert("error", "No se ha podido eliminar el tipo de servicio."));
				}

			} catch (Exception e) {
			}
		}

		this.doGet(request, response);
	}

}
