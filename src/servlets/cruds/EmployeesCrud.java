package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Alert;
import entities.Client;
import entities.Employee;
import entities.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.EmployeeLogic;

public class EmployeesCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeesCrud() {
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
		EmployeeLogic ctrl = new EmployeeLogic();
		LinkedList<Employee> employees = ctrl.list();
		request.setAttribute("employeesList", employees);
		request.getRequestDispatcher("WEB-INF/crud/employees-crud.jsp").forward(request, response);
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
		Employee employee = new Employee();
		EmployeeLogic logic = new EmployeeLogic();
		String action = request.getParameter("action");

		try {
			if (action.equals("create")) {
				setData(request, employee);
				employee = logic.create(employee);

				if (employee != null)
					request.setAttribute("alert", new Alert("success", "Se ha creado el nuevo empleado."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido crear el nuevo empleado."));

			} else if (action.equals("update")) {
				employee.setId(Integer.parseInt(request.getParameter("id")));
				setData(request, employee);
				employee = logic.update(employee);

				if (employee != null)
					request.setAttribute("alert", new Alert("success", "Se ha modificado el empleado."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido modificar el empleado."));

			} else if (action.equals("delete")) {
				employee.setId(Integer.parseInt(request.getParameter("id")));
				employee = logic.delete(employee);

				if (employee != null)
					request.setAttribute("alert", new Alert("success", "Se ha eliminado el empleado."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido eliminar el empleado."));
			}

		} catch (Exception e) {
		}

		this.doGet(request, response);
	}

	private void setData(HttpServletRequest request, Employee employee) {
		employee.setFirstname(request.getParameter("firstname"));
		employee.setLastname(request.getParameter("lastname"));
		employee.setUser(request.getParameter("user"));
		employee.setEmail(request.getParameter("email"));

		boolean isAdmin = false;
		if (request.getParameter("is_admin").equals("administrador"))
			isAdmin = true;

		employee.setIsAdmin(isAdmin);
		employee.setPassword(request.getParameter("password"));
	}
}
