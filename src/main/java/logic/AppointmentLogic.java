package logic;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import data.AppointmentData;
import entities.Appointment;
import entities.AppointmentFilter;
import utils.EmailSender;

public class AppointmentLogic {
	private AppointmentData appointmentData;
	
	public AppointmentLogic() {
		appointmentData = new AppointmentData();
	}
	
	public LinkedList<Appointment> list() {
		return appointmentData.list();
	}
	
	public LinkedList<Appointment> listPast(){
		return appointmentData.listPast();
	}
	
	public LinkedList<Appointment> listAvailable(AppointmentFilter filter) {
		return appointmentData.listAvailable(filter);
	}
	
	public Appointment book(Appointment a, String imagePath) {
		final Appointment appointment = appointmentData.book(a);
		
		if (appointment == null)
			return null;
		
		CompletableFuture.runAsync(() -> {
			try {
				String to = appointment.getClient().getEmail();
				String subject = "Turno reservado - Centro de Estética";
				
				EmailSender emailSender = new EmailSender(to, subject);
				
				String htmlContent = "<h2>Estimado/a " + a.getClient().getFullname() +  ",</h2>"
		                + "<p>Este es un correo de confirmación para informarte que tu turno ha sido reservado correctamente.</p>"
		                + "<h3>Detalles de tu cita:</h3>"
		                + "<ul>"
		                + "<li><strong>Fecha y hora del turno:</strong> " + a.getDateTime() + "</li>"
		                + "<li><strong>Profesional a cargo:</strong> " + a.getEmployee().getFullname() + "</li>"
		                + "</ul>"
		                + "<p>Si tienes alguna pregunta o necesitas realizar algún cambio en tu cita, no dudes en ponerte en contacto con nosotros.</p>"
		                + "<p>Esperamos verte pronto.</p>"
		                + "<br>"
		                + "<p><strong>Saludos cordiales,</strong></p>"
		                + "<p>Centro de Estética<br>"
		                + "Tel: 0341 272-4611<br>"
		                + "<img src='cid:logo' style='width:75px; height:75px; top-border:10px;'>";
		
				String text = "Estimado/a " + a.getClient().getFullname() + ",\n\n"
						+ "Este es un correo de confirmación para informarte que tu turno ha sido reservado correctamente.\n\n"
						+ "Detalles de tu cita:\n"
						+ "- Fecha y hora del turno: " + a.getDateTime() + "\n"
						+ "- Profesional a cargo: " + a.getEmployee().getFullname() + "\n\n"
						+ "Si tienes alguna pregunta o necesitas realizar algún cambio en tu cita, no dudes en ponerte en contacto con nosotros.\n\n"
						+ "Esperamos verte pronto.\n\n"
						+ "Saludos cordiales,\n"
						+ "Centro de Estética\n"
						+ "Tel: 0341 272-4611\n";
				
				emailSender.send(htmlContent, text, imagePath);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}).exceptionally(ex -> {
            System.out.println("Failed to send email: " + ex.getMessage());
            return null;
        });

		return a;
	}
	
	public Appointment create(Appointment a) {

		/*if (dt.isBefore(LocalDateTime.now()))
			return false;
		else
			appointment.setDateTime(dt);
		*/
		return appointmentData.add(a);
	}
	
	public Appointment update(Appointment a) {

		/*if (dt.isBefore(LocalDateTime.now()))
			return false;
		else
			appointment.setDateTime(dt);
		*/
		return appointmentData.update(a);
	}
	
	public Appointment delete(Appointment a) {
		return appointmentData.delete(a);
	}
	
	public Appointment searchById(Appointment a) {
		return appointmentData.searchById(a);
	}
}
