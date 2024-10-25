package entities;

public class Alert {
	String icon;
	String message;
	
	public Alert(String icon, String message) {
		this.icon = icon;
		this.message = message;
	}
	
	public String getIcon() { return icon; }
	public String getMessage() { return message; }
}
