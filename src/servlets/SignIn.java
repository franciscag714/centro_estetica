package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.LoginCli;
import logic.LoginEmp;

import java.io.IOException;

import entities.Client;
import entities.Employee;

public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignIn() {
        super();       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Employee emp = new Employee();
		LoginEmp ctrlE = new LoginEmp();
		Client cli = new Client();
		LoginCli ctrlC = new LoginCli();
		
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
		emp.setUser(user);
		emp.setPassword(password);
		
		emp = ctrlE.validate(emp);
		if (emp != null) {
			request.getSession().setAttribute("usuario", emp);
			request.getRequestDispatcher("WEB-INF/UserManagement.jsp").forward(request, response);
		} else {
			cli.setUser(user);
			cli.setPassword(password);
			cli = ctrlC.validate(cli);
			
			if (cli != null) {
				request.getSession().setAttribute("usuario", cli);
				request.getRequestDispatcher("WEB-INF/UserManagement.jsp").forward(request, response);
			} else {
				request.setAttribute("userNotFound", true);
				this.doGet(request, response);
			}
		}
	}
}
