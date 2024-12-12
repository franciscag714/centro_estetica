package servlets.cruds;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.Alert;
import entities.Appointment;
import entities.Client;
import entities.Employee;
import entities.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import logic.ClientLogic;
import logic.EmployeeLogic;

public class AppointmentsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AppointmentsCrud() {
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
		AppointmentLogic appointmentLogic = new AppointmentLogic();
		LinkedList<Appointment> appointments = appointmentLogic.list();
		request.setAttribute("appointmentsList", appointments);

		ClientLogic clientLogic = new ClientLogic();
		LinkedList<Client> clients = clientLogic.list();
		request.setAttribute("clientsList", clients);

		EmployeeLogic employeeLogic = new EmployeeLogic();
		LinkedList<Employee> employees = employeeLogic.list();
		request.setAttribute("employeesList", employees);

		request.getRequestDispatcher("WEB-INF/crud/appointments-crud.jsp").forward(request, response);
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
		Employee employee = (Employee) user;
		Appointment appointment = new Appointment();
		AppointmentLogic logic = new AppointmentLogic();
		String action = request.getParameter("action");

		try {
			if (action.equals("create")) {
				setData(request, appointment, employee);
				appointment = logic.create(appointment);

				if (appointment != null)
					request.setAttribute("alert", new Alert("success", "Se ha creado el nuevo turno."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido crear el nuevo turno."));

			} else if (action.equals("update")) {
				appointment.setId(Integer.parseInt(request.getParameter("id")));
				setData(request, appointment, employee);
				appointment = logic.update(appointment, employee);

				if (appointment != null)
					request.setAttribute("alert", new Alert("success", "Se ha modificado el turno."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido modificar el turno."));

			} else if (action.equals("delete")) {
				appointment.setId(Integer.parseInt(request.getParameter("id")));
				appointment = logic.delete(appointment, employee);

				if (appointment != null)
					request.setAttribute("alert", new Alert("success", "Se ha eliminado el turno."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido eliminar el turno."));
			}

		} catch (Exception e) {
		}

		this.doGet(request, response);
	}

	private void setData(HttpServletRequest request, Appointment appointment, Employee employee) {
		appointment.setDateTime(LocalDateTime.parse(request.getParameter("date-time")));

		if (employee.isAdmin()) {
			Employee e = new Employee();
			e.setId(Integer.parseInt(request.getParameter("employee")));
			appointment.setEmployee(e);	
		} else
			appointment.setEmployee(employee);

		Client c = new Client();
		if (request.getParameter("client") != null)
			c.setId(Integer.parseInt(request.getParameter("client")));

		appointment.setClient(c);
	}
}
