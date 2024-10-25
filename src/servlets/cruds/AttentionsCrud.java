package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Appointment;
import entities.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;

public class AttentionsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AttentionsCrud() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
        {
        	AppointmentLogic appointmentLogic = new AppointmentLogic();
        	LinkedList<Appointment> appointments = appointmentLogic.listPast();
        	request.setAttribute("appointmentsList", appointments);
        	
        	request.getRequestDispatcher("WEB-INF/crud/attentions-crud.jsp").forward(request, response);
        } else
			response.sendRedirect("index");
	}
}
