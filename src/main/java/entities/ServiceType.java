package entities;

import java.util.LinkedList;

public class ServiceType {
	private int id;
	private String description;
	private LinkedList<Service> services;
	
	public int getId() { return id; }
	public void setId(int id) {	this.id = id; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public LinkedList<Service> getServices() { return services; }
	public void setServices(LinkedList<Service> services) {	this.services = services; }
	
	public void trimFields() {
		this.description = this.description.trim();
	}
}
