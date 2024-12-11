package entities;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointment {
	private int id;
	private LocalDateTime dateTime;
	private Employee employee;
	private Client client;
	private double totalIncome;
	
	public int getId() { return id;	}
	public void setId(int id) { this.id = id; }
	
	@JsonIgnore
	public LocalDateTime getDateTime() { return dateTime; }
	public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
	
	public Employee getEmployee() {	return employee; }
	public void setEmployee(Employee employee) { this.employee = employee; }
	
	public Client getClient() {	return client; }
	public void setClient(Client client) { this.client = client; }
	
	@JsonIgnore
	public String getTotalIncome() {
		if (this.totalIncome == 0) return "";
		
		Locale locale = Locale.forLanguageTag("es-AR");
		return NumberFormat.getCurrencyInstance(locale).format(this.totalIncome);
	}
	public void setTotalIncome(double totalIncome) { this.totalIncome = totalIncome; }
	
	@JsonIgnore
	public Boolean isModifiable(Employee emp) {
		if (emp.isAdmin() || emp.getId() == this.getEmployee().getId())
			return true;
		
		return false;
	}
	
	@JsonIgnore
	public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return this.getDateTime().format(formatter);
	}
}
