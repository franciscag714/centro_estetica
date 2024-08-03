package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AttentiontsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AttentiontsCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
	}

}
