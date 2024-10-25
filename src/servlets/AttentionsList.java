package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Appointment;
import entities.Attention;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AttentionLogic;

public class AttentionsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AttentionsList() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        AttentionLogic logic = new AttentionLogic();
                
        response.setContentType("application/json");

        //poner try para protegerse de petición sin parámetro o de que el mismo no sea integer
        Appointment app = new Appointment();
        app.setId(Integer.parseInt(request.getPathInfo().substring(1)));
                
        LinkedList<Attention> attentions = logic.searchByAppointment(app);
        String jsonResponse = objectMapper.writeValueAsString(attentions);
        
        out.print(jsonResponse);
        out.flush();
	}
}
