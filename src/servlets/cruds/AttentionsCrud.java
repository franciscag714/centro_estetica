package servlets.cruds;

import java.io.IOException;
import java.util.LinkedList;

import entities.Appointment;
import entities.Attention;
import entities.Employee;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
        {
        	AppointmentLogic appointmentLogic = new AppointmentLogic();
        	ServiceLogic serviceLogic = new ServiceLogic();
        	
        	LinkedList<Appointment> appointments = appointmentLogic.listPast();
        	request.setAttribute("appointmentsList", appointments);
        	
        	LinkedList<Service> services = serviceLogic.list();
        	request.setAttribute("servicesList", services);
        	
        	request.getRequestDispatcher("WEB-INF/crud/attentions-crud.jsp").forward(request, response);
        } else
			response.sendRedirect("index");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class)
        {
        	Attention attention = new Attention();
        	AttentionLogic logic = new AttentionLogic();
        	
        	Appointment app = new Appointment();
        	Service service = new Service();
        	
        	String action = request.getParameter("action");
			
			try {
				if (action.equals("create"))
				{
					app.setId(Integer.parseInt(request.getParameter("appointment")));
					service.setId(Integer.parseInt(request.getParameter("service")));
					
					attention.setAppointment(app);
					attention.setService(service);
					
					logic.create(attention);
				}
				else if (action.equals("delete"))
				{
					app.setId(Integer.parseInt(request.getParameter("appointment")));
					service.setId(Integer.parseInt(request.getParameter("service")));
					
					attention.setAppointment(app);
					attention.setService(service);
					
					logic.delete(attention);
				}
			} catch (Exception e) { }
        	
        	doGet(request, response);
        } else
			response.sendRedirect("index");
	}
}
