package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Alert;
import entities.Appointment;
import entities.Attention;
import entities.Client;
import entities.Person;
import entities.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import logic.AttentionLogic;
import logic.ServiceLogic;

public class AttentionsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AttentionsCrud() {
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
		ServiceLogic serviceLogic = new ServiceLogic();

		LinkedList<Appointment> appointments = appointmentLogic.listPast();
		request.setAttribute("appointmentsList", appointments);

		LinkedList<Service> services = serviceLogic.list();
		request.setAttribute("servicesList", services);

		request.getRequestDispatcher("WEB-INF/crud/attentions-crud.jsp").forward(request, response);
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
		Attention attention = new Attention();
		AttentionLogic logic = new AttentionLogic();

		Appointment app = new Appointment();
		Service service = new Service();

		String action = request.getParameter("action");

		try {
			if (action.equals("create")) {
				app.setId(Integer.parseInt(request.getParameter("appointment")));
				service.setId(Integer.parseInt(request.getParameter("service")));

				attention.setAppointment(app);
				attention.setService(service);

				attention = logic.create(attention);
				if (attention != null)
					request.setAttribute("alert", new Alert("success", "Se ha registrado la atención realizada."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido registrar la atención."));

			} else if (action.equals("delete")) {
				app.setId(Integer.parseInt(request.getParameter("appointment")));
				service.setId(Integer.parseInt(request.getParameter("service")));

				attention.setAppointment(app);
				attention.setService(service);

				attention = logic.delete(attention);
				if (attention != null)
					request.setAttribute("alert", new Alert("success", "Se ha eliminado la atención."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido eliminar la atención."));
			}
		} catch (Exception e) {
		}

		// TODO: seleccionar turno automáticamente
		this.doGet(request, response);
	}
}
