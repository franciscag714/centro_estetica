package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Questions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Questions() {
        super();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//enviar email y mostrar mensaje
		request.getRequestDispatcher("WEB-INF/our-location.jsp").forward(request, response);
	}

}
