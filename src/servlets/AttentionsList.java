package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Appointment;
import entities.Attention;
import entities.Client;
import entities.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AttentionLogic;

public class AttentionsList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AttentionsList() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Client) {
			response.sendRedirect("../index");
			return;
		}

		// user is Employee
		ObjectMapper objectMapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		AttentionLogic logic = new AttentionLogic();

		response.setContentType("application/json");

		Appointment app = new Appointment();
		try {
			app.setId(Integer.parseInt(request.getPathInfo().substring(1)));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		LinkedList<Attention> attentions = logic.searchByAppointment(app);
		String jsonResponse = objectMapper.writeValueAsString(attentions);
		out.print(jsonResponse);
		out.flush();
	}
}
