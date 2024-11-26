package servlets;

import java.io.IOException;

import entities.Alert;
import entities.Client;
import entities.Employee;
import entities.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClientLogic;

public class MyData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyData() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Employee)
			response.sendRedirect("index");
		else // user is Client
			request.getRequestDispatcher("WEB-INF/client/my-data.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer.parseInt("adfadf");
		Person user = (Person) request.getSession().getAttribute("user");

		if (user == null || user instanceof Employee) {
			response.sendRedirect("index");
			return;
		}

		// user is Client
		Client client = new Client();
		ClientLogic logic = new ClientLogic();

		try {
			client.setId(user.getId());
			client.setFirstname(request.getParameter("firstname"));
			client.setLastname(request.getParameter("lastname"));
			client.setEmail(request.getParameter("email"));
			client.setUser(request.getParameter("user"));
			client.setPassword(request.getParameter("password"));
			client = logic.update(client);

			if (client != null) {
				request.setAttribute("alert", new Alert("success", "Sus datos han sido modificados."));
				request.getSession().setAttribute("user", client);
			} else
				request.setAttribute("alert", new Alert("error", "No se han podido modificar sus datos."));

		} catch (Exception e) {
		}

		this.doGet(request, response);
	}
}
