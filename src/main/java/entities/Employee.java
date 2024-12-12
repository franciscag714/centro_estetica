package entities;

public class Employee extends Person {
	private Boolean isAdmin;

	public Boolean isAdmin() { return isAdmin; }
	public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
}
