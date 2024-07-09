package entities;

import java.time.LocalDateTime;

public class Appointment {
	private int id;
	private LocalDateTime dateTime;
	private Employee employee;
	private Client client;
	
	public int getId() { return id;	}
	public void setId(int id) { this.id = id; }
	
	public LocalDateTime getDateTime() {	return dateTime; }
	public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
	
	public Employee getEmployee() {	return employee; }
	public void setEmployee(Employee employee) { this.employee = employee; }
	
	public Client getClient() {	return client; }
	public void setClient(Client client) { this.client = client; }
}
