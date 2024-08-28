package servlets.cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.EmployeeLogic;

import java.io.IOException;
import java.util.LinkedList;

import entities.Employee;

public class EmployeesCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeesCrud() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class) 
        {
        	EmployeeLogic ctrl = new EmployeeLogic();
			LinkedList<Employee> employees = ctrl.list();
			request.setAttribute("employeesList", employees);
			request.getRequestDispatcher("WEB-INF/employees-crud.jsp").forward(request, response);
        }
        else 
        	response.sendRedirect("index");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
        	response.sendRedirect("index");
        
        else if (request.getSession().getAttribute("user").getClass() == Employee.class) 
        {
        	Employee employee = new Employee();
        	EmployeeLogic logic = new EmployeeLogic();
        	String action = request.getParameter("action");
        	
        	if(action.equals("create"))
        	{
        		try {
		        	employee.setFirstname(request.getParameter("firstname"));
		        	employee.setLastname(request.getParameter("lastname"));
		        	employee.setUser(request.getParameter("user"));
		        	employee.setEmail(request.getParameter("email"));
		        	boolean tipo = false;
		        	if (request.getParameter("is_admin").equals("administrador")) 
		        		tipo = true;
		        	else if (request.getParameter("is_admin").equals("empleado"))
		        		tipo = false;
		        	employee.setIsAdmin(tipo);
	        		employee.setPassword(request.getParameter("password"));
	        		logic.create(employee);
        		}
        		catch (Exception e) { }
        	}
        	else if (action.equals("update"))
        	{
        		try {
        			employee.setId(Integer.parseInt(request.getParameter("id")));
        			employee.setFirstname(request.getParameter("firstname"));
		        	employee.setLastname(request.getParameter("lastname"));
		        	employee.setUser(request.getParameter("user"));
		        	employee.setEmail(request.getParameter("email"));
		        	boolean tipo = false;
		        	if (request.getParameter("is_admin").equals("administrador")) 
		        		tipo = true;
		        	else if (request.getParameter("is_admin").equals("empleado"))
		        		tipo = false;
		        	employee.setIsAdmin(tipo);
	        		employee.setPassword(request.getParameter("password"));
	        		logic.update(employee);
        		}
        		catch (Exception e) { }
        	}
        	else if (action.equals("delete"))
        	{
        		try {
        			employee.setId(Integer.parseInt(request.getParameter("id")));
        			logic.delete(employee);
        		}
        		catch (Exception e) { }
        	}
        }
       	response.sendRedirect("empleados");
	}
}
