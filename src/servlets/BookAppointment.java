package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import entities.Appointment;
import entities.AppointmentFilter;
import entities.Client;

public class BookAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookAppointment() {
        super();
    }

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
	        	
	        	/*ClientLogic clientCtrl = new ClientLogic();
				LinkedList<Client> clients = clientCtrl.list();
				request.setAttribute("clientsList", clients);
				
				EmployeeLogic employeeCtrl = new EmployeeLogic();
				LinkedList<Employee> employees = employeeCtrl.list();
				request.setAttribute("employeesList", employees);
				*/
        	}
        	catch (Exception e) {
        		System.out.println(e.toString());
        	}
			
        	request.getRequestDispatcher("WEB-INF/book-appointment.jsp").forward(request, response);
        }
        else
        	response.sendRedirect("index");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
