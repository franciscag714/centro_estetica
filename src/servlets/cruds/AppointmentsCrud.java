package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import logic.ClientLogic;
import logic.EmployeeLogic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.Appointment;
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
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class) 
        {
        	AppointmentLogic appointmentLogic = new AppointmentLogic();
        	LinkedList<Appointment> appointments = appointmentLogic.list();
        	request.setAttribute("appointmentsList", appointments);
        	
        	ClientLogic clientLogic = new ClientLogic();
			LinkedList<Client> clients = clientLogic.list();
			request.setAttribute("clientsList", clients);
			
			EmployeeLogic employeeLogic = new EmployeeLogic();
			LinkedList<Employee> employees = employeeLogic.list();
			request.setAttribute("employeesList", employees);
			
			request.getRequestDispatcher("WEB-INF/crud/appointments-crud.jsp").forward(request, response);
        }
        else 
        	response.sendRedirect("index");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
		{
			response.sendRedirect("index");
			return;
		}
		if (request.getSession().getAttribute("user").getClass() == Employee.class)
		{
			Appointment appointment = new Appointment();
			AppointmentLogic logic = new AppointmentLogic();
			String action = request.getParameter("action");
			
			try {
				if (action.equals("create"))
				{
					if (setData(request, response, appointment))
						logic.create(appointment);			
				} 
				else if (action.equals("update"))
				{
					appointment.setId(Integer.parseInt(request.getParameter("id")));
					if (setData(request, response, appointment))
						logic.update(appointment);	
				} 
				else if (action.equals("delete"))
				{
					appointment.setId(Integer.parseInt(request.getParameter("id")));
					logic.delete(appointment);	
				}
			} catch (Exception e) { }
		}
		response.sendRedirect("turnos");
	}
	
	private boolean setData(HttpServletRequest request, HttpServletResponse response, Appointment appointment) throws IOException
	{
		LocalDateTime dt = LocalDateTime.parse(request.getParameter("date_time"));
		
		if (dt.isBefore(LocalDateTime.now()))
			return false;
		else
			appointment.setDateTime(dt);
		
		Employee e = new Employee();
		e.setId(Integer.parseInt(request.getParameter("employee")));
		appointment.setEmployee(e);
		
		Client c = new Client();
		if (request.getParameter("client") != null)
			c.setId(Integer.parseInt(request.getParameter("client")));

		appointment.setClient(c);
		return true;
	}
}
