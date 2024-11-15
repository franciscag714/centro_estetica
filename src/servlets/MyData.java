package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClientLogic;

import java.io.IOException;

import entities.Client;

public class MyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
        if (request.getSession().getAttribute("user") == null)
			response.sendRedirect("index");
        
		else if (request.getSession().getAttribute("user").getClass() == Client.class) 
		{
			Client session = (Client) request.getSession().getAttribute("user");
			ClientLogic clientCtrl = new ClientLogic();
			Client sessionC = clientCtrl.searchById(session);
			request.setAttribute("client", sessionC);
			request.getRequestDispatcher("WEB-INF/client/my-data.jsp").forward(request, response);
		}else
			response.sendRedirect("index");
    }
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("index");
            return;
        }

        if (request.getSession().getAttribute("user").getClass() == Client.class)
		{
	        Client client = new Client();
	        ClientLogic logic = new ClientLogic();
	        String action = request.getParameter("action");
	
	        if (action.equals("update")) {
	            try {
	                client.setId(Integer.parseInt(request.getParameter("id")));
	                client.setFirstname(request.getParameter("firstname"));
	                client.setLastname(request.getParameter("lastname"));
	                client.setEmail(request.getParameter("email"));
	                client.setUser(request.getParameter("user"));
                    client.setPassword(request.getParameter("password"));
	                
	                logic.update(client);
	                request.getSession().setAttribute("user", client);
	                response.sendRedirect("mis-datos");
	
	            }catch (Exception e) { }
	
		    } else 
		        response.sendRedirect("mis-datos");
		    
		}
	}

}
