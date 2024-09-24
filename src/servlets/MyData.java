
package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClientLogic;

import java.io.IOException;

import entities.Client;
import entities.Person;

public class MyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//NEVER IN PRODUCTION request.getSession().setAttribute("user", new Client());//never in production
		
		Client sessionClient = (Client) request.getSession().getAttribute("user"); 
		
		if (request.getSession().getAttribute("user") == null)
			response.sendRedirect("index");
        
		else if (request.getSession().getAttribute("user").getClass() == Client.class) 
		{
			
			ClientLogic clientCtrl = new ClientLogic();
			Client cli = clientCtrl.searchByUser(sessionClient);
			request.setAttribute("client", cli);
			request.getRequestDispatcher("WEB-INF/my-data.jsp").forward(request, response);
		}
		else
			response.sendRedirect("index");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getSession().getAttribute("user") == null)
			response.sendRedirect("index");
		
		else if (request.getSession().getAttribute("user").getClass() == Client.class) 
		{
			Client client = new Client();
			ClientLogic logic = new ClientLogic();
			String action = request.getParameter("action");
			
			//por las dudas, por ahi no es necesario
			if (action.equals("update")) 
			{
				try {
					client.setId(Integer.parseInt(request.getParameter("id")));
					client.setFirstname(request.getParameter("firstname"));
					client.setLastname(request.getParameter("lastname"));
					client.setEmail(request.getParameter("email"));
					client.setUser(request.getParameter("user"));
					client.setPassword(request.getParameter("password"));
					
					logic.update(client);
					
					System.out.println("Action: " + request.getParameter("action"));

				}
				catch (Exception e) { }
			}
			
		}
		
		response.sendRedirect("mis-datos");
	}

}
