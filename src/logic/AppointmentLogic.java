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
	
	public LinkedList<Appointment> list2(){
		return appointmentData.list2();
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
	
	public Appointment searchById(Appointment a) {
		return appointmentData.searchById(a);
	}
}
