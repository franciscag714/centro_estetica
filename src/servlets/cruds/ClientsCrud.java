package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Alert;
import entities.Client;
import entities.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClientLogic;

public class ClientsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ClientsCrud() {
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
		ClientLogic ctrl = new ClientLogic();
		LinkedList<Client> clients = ctrl.list();
		request.setAttribute("clientsList", clients);
		request.getRequestDispatcher("WEB-INF/crud/clients-crud.jsp").forward(request, response);
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
		Client client = new Client();
		ClientLogic logic = new ClientLogic();
		String action = request.getParameter("action");

		try {
			if (action.equals("create")) {
				setData(request, client);
				client = logic.create(client);

				if (client != null)
					request.setAttribute("alert", new Alert("success", "Se ha creado el nuevo cliente."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido crear el nuevo cliente."));

			} else if (action.equals("update")) {
				client.setId(Integer.parseInt(request.getParameter("id")));
				setData(request, client);
				client = logic.update(client);

				if (client != null)
					request.setAttribute("alert", new Alert("success", "Se ha modificado el cliente."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido modificar el cliente."));

			} else if (action.equals("delete")) {
				client.setId(Integer.parseInt(request.getParameter("id")));
				client = logic.delete(client);

				if (client != null)
					request.setAttribute("alert", new Alert("success", "Se ha eliminado el cliente."));
				else
					request.setAttribute("alert", new Alert("error", "No se ha podido eliminar el cliente."));
			}

		} catch (Exception e) {
		}

		this.doGet(request, response);
	}

	private void setData(HttpServletRequest request, Client client) {
		client.setFirstname(request.getParameter("firstname"));
		client.setLastname(request.getParameter("lastname"));
		client.setEmail(request.getParameter("email"));
		client.setUser(request.getParameter("user"));
		client.setPassword(request.getParameter("password"));
	}

}
