package entities;

public class Attention {
	private Appointment appointment;
	private Service service;
	private double price;
	
	public Appointment getAppointment() { return appointment; }
	public void setAppointment(Appointment appointment) { this.appointment = appointment; }
	
	public Service getService() { return service; }
	public void setService(Service service) { this.service = service; }
	
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
}
