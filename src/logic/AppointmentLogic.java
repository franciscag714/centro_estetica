package logic;

import java.util.LinkedList;

import data.AppointmentData;
import entities.Appointment;
import entities.AppointmentFilter;

public class AppointmentLogic {
	private AppointmentData appointmentData;
	
	public AppointmentLogic() {
		appointmentData = new AppointmentData();
	}
	
	public LinkedList<Appointment> list() {
		return appointmentData.list();
	}
	
	public LinkedList<Appointment> listAvailable(AppointmentFilter filter) {
		return appointmentData.listAvailable(filter);
	}
	
	public Appointment book(Appointment a) {
		return appointmentData.book(a);
	}
	
	public Appointment create(Appointment a) {
		return appointmentData.add(a);
	}
	
	public Appointment update(Appointment a) {
		return appointmentData.update(a);
	}
	
	public Appointment delete(Appointment a) {
		return appointmentData.delete(a);
	}
}
