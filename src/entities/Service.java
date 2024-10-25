package entities;

import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Service {
	private int id;
	private String description;
	private double updatedPrice;
	private ServiceType type;
	
	public int getId() { return id; }
	public void setId(int id) {	this.id = id; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	@JsonIgnore
	public double getUpdatedPrice() { return updatedPrice; }
	public void setUpdatedPrice(double updatedPrice) { this.updatedPrice = updatedPrice; }
	
	@JsonIgnore
	public ServiceType getType() { return type; }
	public void setType(ServiceType type) { this.type = type; }
	
	@JsonIgnore
	public String getFormatedPrice() {
		return NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(this.getUpdatedPrice());
	}
}
