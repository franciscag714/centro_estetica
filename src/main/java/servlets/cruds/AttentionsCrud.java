package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Alert;
import entities.Appointment;
import entities.Attention;
import entities.Client;
import entities.Employee;
import entities.Person;
import entities.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import logic.AttentionLogic;
import logic.ServiceLogic;
import utils.QrGenerator;

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

		LinkedList<Appointment> appointments = appointmentLogic.listPast((Employee) user);
		request.setAttribute("appointmentsList", appointments);

		LinkedList<Service> services = serviceLogic.list();
		request.setAttribute("servicesList", services);

		if (request.getAttribute("selectedId") == null)
			request.setAttribute("selectedId", 0);
		else {
			Appointment app = new Appointment();
			app.setId((int) request.getAttribute("selectedId"));
			AttentionLogic attentionLogic = new AttentionLogic();
			
			LinkedList<Attention> attentions = attentionLogic.searchByAppointment(app);
			request.setAttribute("attentionsList", attentions);
		}
		
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
			app.setId(Integer.parseInt(request.getParameter("appointment")));
			service.setId(Integer.parseInt(request.getParameter("service")));

			attention.setAppointment(app);
			attention.setService(service);
			
			if (action.equals("create")) {
				attention = logic.create(attention, (Employee) user);
				if (attention != null)
					request.setAttribute("alert", new Alert("success", "Se ha registrado la atención realizada."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido registrar la atención."));

			} else if (action.equals("delete")) {
				attention = logic.delete(attention, (Employee) user);
				if (attention != null)
					request.setAttribute("alert", new Alert("success", "Se ha eliminado la atención."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido eliminar la atención."));
			}
			
			request.setAttribute("selectedId", app.getId());
			
		} catch (Exception e) {
		}

		this.doGet(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Client) {
			response.sendRedirect("index");
			return;
		}

		// user is Employee
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = request.getReader().readLine()) != null)
			stringBuilder.append(line);

		String requestBody = stringBuilder.toString();
		ObjectMapper objectMapper = new ObjectMapper();
		Appointment appointment = objectMapper.readValue(requestBody, Appointment.class);

		AppointmentLogic logic = new AppointmentLogic();
		appointment = logic.calculateTotalIncome(appointment);

		if (appointment != null) {
			QrGenerator qr = new QrGenerator();
			Boolean generated = qr.generate(appointment);

			if (generated)
				response.setStatus(HttpServletResponse.SC_OK);
			else
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
