package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import entities.Alert;
import entities.Appointment;
import entities.AppointmentFilter;
import entities.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;

public class BookAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookAppointment() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//NEVER IN PRODUCTION
		request.getSession().setAttribute("user", new Client()); //never in production
		
		
		
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Client.class)
        {
        	AppointmentLogic appointmentLogic = new AppointmentLogic();
        	
        	try {
        		AppointmentFilter filter = new AppointmentFilter();
        		
        		if (request.getParameter("date") != null && !request.getParameter("date").isEmpty())
        			filter.setDate(LocalDate.parse(request.getParameter("date")));
        		
        		if (request.getParameter("start-time") != null && !request.getParameter("start-time").isEmpty())
        			filter.setStartTime(LocalTime.parse(request.getParameter("start-time")));
        		
        		if (request.getParameter("end-time") != null && !request.getParameter("end-time").isEmpty())
        			filter.setEndTime(LocalTime.parse(request.getParameter("end-time")));

        			        	
	        	LinkedList<Appointment> appointments = appointmentLogic.listAvailable(filter);
	        	request.setAttribute("availableAppointments", appointments);
        	}
        	catch (Exception e) {
        		System.out.println(e.toString());
        	}
			
        	request.getRequestDispatcher("WEB-INF/client/book-appointment.jsp").forward(request, response);
        }
        else
        	response.sendRedirect("index");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//NEVER IN PRODUCTION
		Client c = new Client();
		c.setId(4);
		request.getSession().setAttribute("user", c); //never in production
		
		
		
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Client.class)
        {
        	AppointmentLogic appointmentLogic = new AppointmentLogic();
        	Appointment appointment = new Appointment();
        	Alert alert = new Alert("error", "No se pudo reservar el turno.");
        	
        	try {
        		appointment.setId(Integer.parseInt(request.getParameter("appointment-id")));
        		appointment.setClient((Client) request.getSession().getAttribute("user"));
        		
        			        	
	        	appointment = appointmentLogic.book(appointment);
	        	if (appointment != null)
	        		alert = new Alert("success", "Turno reservado con éxito.");	//se pueden mostrar detalles y enviar mail confimración
        	}
        	catch (Exception e) {
        		System.out.println(e.toString());
        	}
			
        	request.setAttribute("alert", alert);
        	doGet(request, response);
        }
        else
        	response.sendRedirect("index");
	}

}
