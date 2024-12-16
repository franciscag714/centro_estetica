package logic;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import data.AppointmentData;
import entities.Appointment;
import entities.AppointmentFilter;
import entities.Client;
import entities.Employee;
import utils.EmailSender;

public class AppointmentLogic {
	private AppointmentData appointmentData;
	
	public AppointmentLogic() {
		appointmentData = new AppointmentData();
	}
	
	public LinkedList<Appointment> list() {
		return appointmentData.list();
	}
	
	public LinkedList<Appointment> listPast(Employee e){
		if (e.isAdmin())
			return appointmentData.listPast(null);
		else
			return appointmentData.listPast(e);
	}
	
	public Map<String, LinkedList<Appointment>> listAvailable(AppointmentFilter filter) {
		LinkedList<Appointment> appointments = appointmentData.listAvailable(filter);
        
        Map<String, LinkedList<Appointment>> groupedByDate = appointments.stream()
                .collect(Collectors.groupingBy(
                    appointment -> appointment.getFormattedDate(),
                    LinkedHashMap::new,
                    Collectors.toCollection(LinkedList::new)
                ));
        
        return groupedByDate;
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
		                + "<li><strong>Fecha y hora del turno:</strong> " + a.getFullFormattedDateTime() + "</li>"
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
						+ "- Fecha y hora del turno: " + a.getFullFormattedDateTime() + "\n"
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
	
	public Appointment unbook(Appointment a, String imagePath) {
		final Appointment appointment = appointmentData.unbook(a);
		
		if (appointment == null)
			return null;
		
		CompletableFuture.runAsync(() -> {
			try {
				String to = appointment.getClient().getEmail();
				String subject = "Turno cancelado - Centro de Estética";
				
				EmailSender emailSender = new EmailSender(to, subject);
				
				String htmlContent = "<h2>Estimado/a " + a.getClient().getFullname() +  ",</h2>"
		                + "<p>Tu turno para el " + a.getFullFormattedDateTime() + " ha sido cancelado con éxito.</p>"
		                + "<p>Si tienes alguna pregunta, no dudes en ponerte en contacto con nosotros.</p>"
		                + "<p>Esperamos verte pronto.</p>"
		                + "<br>"
		                + "<p><strong>Saludos cordiales,</strong></p>"
		                + "<p>Centro de Estética<br>"
		                + "Tel: 0341 272-4611<br>"
		                + "<img src='cid:logo' style='width:75px; height:75px; top-border:10px;'>";
		
				String text = "Estimado/a " + a.getClient().getFullname() + ",\n\n"
						+ "Tu turno para el " + a.getFullFormattedDateTime() + " ha sido cancelado con éxito.\n"
						+ "Si tienes alguna pregunta, no dudes en ponerte en contacto con nosotros.\n\n"
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
		if (a.getDateTime().isBefore(LocalDateTime.now()))
			return null;
		
		return appointmentData.add(a);
	}
	
	public Appointment update(Appointment a, Employee e) {
		if (a.getDateTime().isBefore(LocalDateTime.now()))
			return null;
		
		if (e.isAdmin())
			return appointmentData.update(a, null);
		else
			return appointmentData.update(a, e);
	}
	
	public Appointment delete(Appointment a, Employee e) {
		if (e.isAdmin())
			return appointmentData.delete(a, null);
		else
			return appointmentData.delete(a, e);
	}

	public LinkedList<Appointment> searchByClient(Client c) {
		return appointmentData.searchByClient(c);
	}

	public Appointment calculateTotalIncome(Appointment a) {
		return appointmentData.calculateTotalIncome(a);
	}
}
