package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Client;
import entities.Employee;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
        	ClientLogic ctrl = new ClientLogic();
			LinkedList<Client> clients = ctrl.list();
			request.setAttribute("clientsList", clients);
			request.getRequestDispatcher("WEB-INF/crud/clients-crud.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null) {
        	response.sendRedirect("index");
        	return;
		}
		
		if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
			Client client = new Client();
			ClientLogic logic = new ClientLogic();
			String action = request.getParameter("action");
			
			if (action.equals("create"))
				try {
					client.setFirstname(request.getParameter("firstname"));
					client.setLastname(request.getParameter("lastname"));
					client.setEmail(request.getParameter("email"));
					client.setUser(request.getParameter("user"));
					client.setPassword(request.getParameter("password"));
					logic.create(client);
				}
				catch (Exception e) { }
			else if (action.equals("update"))
				try {
					client.setId(Integer.parseInt(request.getParameter("id")));
					client.setFirstname(request.getParameter("firstname"));
					client.setLastname(request.getParameter("lastname"));
					client.setEmail(request.getParameter("email"));
					client.setUser(request.getParameter("user"));
					client.setPassword(request.getParameter("password"));
					logic.update(client);
				}
				catch (Exception e) { }
			else if (action.equals("delete"))
				try {
					client.setId(Integer.parseInt(request.getParameter("id")));
					logic.delete(client);
				}
				catch (Exception e) { }
		}
		response.sendRedirect("clientes");
	}

}
