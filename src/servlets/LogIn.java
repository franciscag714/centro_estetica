package servlets;

import java.io.IOException;

import entities.Employee;
import entities.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.LoginLogic;

public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogIn() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Person p = (Person) request.getSession().getAttribute("user");
		if (p == null)
			request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
		else if (p.getClass() == Employee.class)
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
		else
			request.getRequestDispatcher("WEB-INF/book-appointment.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Person p = new Person();
		p.setUser(request.getParameter("user"));
		p.setPassword(request.getParameter("password"));
		
		LoginLogic loginLogic = new LoginLogic();
		p = loginLogic.validate(p);
		
		if (p == null) {
			request.setAttribute("userNotFound", true);
			this.doGet(request, response);
		}
		else {
			request.getSession().setAttribute("user", p);
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
		}
	}
}
