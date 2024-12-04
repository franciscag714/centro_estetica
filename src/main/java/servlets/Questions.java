package servlets;

import java.io.IOException;

import entities.Alert;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EmailSender;

public class Questions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Questions() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String to = request.getParameter("email");
		String issue = request.getParameter("issue");
		String description = request.getParameter("description");

		if (to.isEmpty() || issue.isEmpty() || description.isEmpty()) {
			request.getRequestDispatcher("WEB-INF/our-location.jsp").forward(request, response);
			return;
		}

		String subject = "Hemos recibido su pregunta!";
		EmailSender emailSender = new EmailSender(to, subject);
		
		String htmlContent = String.format("<p>Su consulta sobre <b>%s</b> fue recibida.</p>" + "<p>Descripción: %s</p>"
				+ "<p>Le responderemos en breve. Saludos!</p>"
				+ "<div style=\"color: #FF5733; font-size: 16px; font-weight: bold;\">Centro de Estética</div>"
				+ "<img src='cid:logo' style='width:75px; height:75px; top-border:10px;'>", issue, description);

		String text = String.format("Hemos recibido su consulta sobre \"%s\"\n" + "Descripción: %s\n\n"
				+ "Le responderemos en breve. Saludos!", issue, description);

		String imagePath = getServletContext().getRealPath("/resources/CE.jpg");
		
		Boolean sent = emailSender.send(htmlContent, text, imagePath);

		if (sent)
			request.setAttribute("alert",
					new Alert("success", "Su mensaje ha sido enviado. Recibirá una respuesta en breve."));
		else
			request.setAttribute("alert", new Alert("error", "Su mensaje no pudo ser enviado."));

		request.getRequestDispatcher("WEB-INF/our-location.jsp").forward(request, response);
	}

}
