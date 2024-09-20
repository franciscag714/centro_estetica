package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AppointmentLogic;
import logic.AttentionLogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;

import entities.Appointment;
import entities.Attention;
import entities.Employee;

public class AttentionsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AttentionsCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class) 
        {
        	AppointmentLogic appointmentCtrl = new AppointmentLogic();
        	LinkedList<Appointment> appointments = appointmentCtrl.list2();
        	request.setAttribute("appointmentsList", appointments);
        	
        	//lo ultimo que trate pero no funciono
        	if (request.getParameter("appointmentId:") != null) {
        		Appointment app = new Appointment();
        		app.setId(Integer.parseInt(request.getParameter("appointmentId:")));
        		
	        	Attention att = new Attention();        	
	        	AttentionLogic attentionCtrl = new AttentionLogic();
	        	att.setAppointment(app);
	        	LinkedList<Attention> attentions = attentionCtrl.searchByAppointment(app);
	        	request.setAttribute("attentionsList", attentions);
        	}
        	
        	request.getRequestDispatcher("WEB-INF/crud/attentions-crud.jsp").forward(request, response);
        	
        }
        else {
        	response.sendRedirect("index");
        }
	}

	
}
