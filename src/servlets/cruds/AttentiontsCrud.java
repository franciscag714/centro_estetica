package servlets.cruds;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AttentiontsCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AttentiontsCrud() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
	}

}
