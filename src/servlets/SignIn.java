package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.LoginCli;
import logic.LoginEmp;

import java.io.IOException;

import com.mysql.cj.Session;

import entities.Client;
import entities.Employee;
import entities.Person;

public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignIn() {
        super();       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Person user = (Person) request.getSession().getAttribute("user");
		if (user == null)
			request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
		else if (user.getClass() == Employee.class)
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
		else
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response); //definir a dónde debe ser redirigido
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
			request.getSession().setAttribute("user", emp);
			request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
		} else {
			cli.setUser(user);
			cli.setPassword(password);
			cli = ctrlC.validate(cli);
			
			if (cli != null) {
				request.getSession().setAttribute("user", cli);
				request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
			} else {
				request.setAttribute("userNotFound", true);
				this.doGet(request, response);
			}
		}
	}
}
