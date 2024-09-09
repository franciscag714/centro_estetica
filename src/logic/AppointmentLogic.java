package logic;

import java.util.LinkedList;

import data.AppointmentData;
import entities.Appointment;

public class AppointmentLogic {
	private AppointmentData appointmentData;
	
	public AppointmentLogic() {
		appointmentData = new AppointmentData();
	}
	
	public LinkedList<Appointment> list(){
		return appointmentData.list();
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
}
