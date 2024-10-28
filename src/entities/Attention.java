package entities;

import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Attention {
	private Appointment appointment;
	private Service service;
	private double price;
	
	public Appointment getAppointment() { return appointment; }
	public void setAppointment(Appointment appointment) { this.appointment = appointment; }
	
	public Service getService() { return service; }
	public void setService(Service service) { this.service = service; }
	
	@JsonIgnore
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	
	public String getFormatedPrice() {
		Locale locale = Locale.forLanguageTag("es-AR");
		return NumberFormat.getCurrencyInstance(locale).format(this.getPrice());
	}
}
