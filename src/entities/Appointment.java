package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointment {
	private int id;
	private LocalDateTime dateTime;
	private Employee employee;
	private Client client;
	private Boolean isModifiable = true; //dependerá de los permisos del usuario
	
	public int getId() { return id;	}
	public void setId(int id) { this.id = id; }
	
	@JsonIgnore
	public LocalDateTime getDateTime() {	return dateTime; }
	public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
	
	public Employee getEmployee() {	return employee; }
	public void setEmployee(Employee employee) { this.employee = employee; }
	
	public Client getClient() {	return client; }
	public void setClient(Client client) { this.client = client; }
	
	@JsonIgnore
	public Boolean isModifiable() { return isModifiable; }
	
	@JsonIgnore
	public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return this.getDateTime().format(formatter);
	}
}
