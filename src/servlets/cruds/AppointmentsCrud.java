package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import logic.ClientLogic;
import logic.EmployeeLogic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import data.ClientData;
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
        	AppointmentLogic appointmentCtrl = new AppointmentLogic();
        	LinkedList<Appointment> appointments = appointmentCtrl.list();
        	request.setAttribute("appointmentsList", appointments);
        	
        	ClientLogic clientCtrl = new ClientLogic();
			LinkedList<Client> clients = clientCtrl.list();
			request.setAttribute("clientsList", clients);
			
			EmployeeLogic employeeCtrl = new EmployeeLogic();
			LinkedList<Employee> employees = employeeCtrl.list();
			request.setAttribute("employeesList", employees);
			
			request.getRequestDispatcher("WEB-INF/appointments-crud.jsp").forward(request, response);
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
			
			if (action.equals("create"))
			{
				try {
					appointment.setDateTime(LocalDateTime.parse(request.getParameter("date_time")));
					
					Client c = new Client();
					c.setId(Integer.parseInt(request.getParameter("client")));
					ClientLogic clientLogic = new ClientLogic();
					c = clientLogic.searchById(c);
					appointment.setClient(c);
					
					Employee e = new Employee();
					e.setId(Integer.parseInt(request.getParameter("employee")));
					EmployeeLogic employeeLogic = new EmployeeLogic();
					e = employeeLogic.searchById(e);
					appointment.setEmployee(e);
					
					logic.create(appointment);
				} catch (Exception e) { }
			} 
			else if (action.equals("update"))
			{
				try {
					appointment.setId(Integer.parseInt(request.getParameter("id")));
					appointment.setDateTime(LocalDateTime.parse(request.getParameter("date_time")));
					
					Client c = new Client();
					c.setId(Integer.parseInt(request.getParameter("client")));
					ClientLogic clientLogic = new ClientLogic();
					c = clientLogic.searchById(c);
					appointment.setClient(c);
					
					Employee e = new Employee();
					e.setId(Integer.parseInt(request.getParameter("employee")));
					EmployeeLogic employeeLogic = new EmployeeLogic();
					e = employeeLogic.searchById(e);
					appointment.setEmployee(e);
					
					logic.update(appointment);
				} catch (Exception e) { 
					System.out.println(e.toString());
				}
			} 
			else if (action.equals("delete"))
			{
				try {
					appointment.setId(Integer.parseInt(request.getParameter("id")));
					logic.delete(appointment);
				} catch (Exception e) { }
			}
		}
		response.sendRedirect("turnos");
	}
}
