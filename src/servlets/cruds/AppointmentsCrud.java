package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClientLogic;

import java.io.IOException;
import java.util.LinkedList;

import entities.Client;
import entities.Employee;

public class AppointmentsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AppointmentsCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
	}

}
