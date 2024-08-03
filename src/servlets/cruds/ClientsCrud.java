package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClientLogic;
import logic.ServiceTypeLogic;

import java.io.IOException;
import java.util.LinkedList;

import entities.Client;
import entities.Employee;
import entities.ServiceType;

public class ClientsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ClientsCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
        	ClientLogic ctrl = new ClientLogic();
			LinkedList<Client> clients = ctrl.list();
			request.setAttribute("clientsList", clients);
			request.getRequestDispatcher("WEB-INF/clients-crud.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Próximamente");
	}

}
