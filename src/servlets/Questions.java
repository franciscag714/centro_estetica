package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Questions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Questions() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//enviar email y mostrar mensaje
		request.getRequestDispatcher("WEB-INF/our-location.jsp").forward(request, response);
	}

}
