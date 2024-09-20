package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import entities.Client;

public class MyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyData() {
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
			request.getRequestDispatcher("WEB-INF/my-data.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
