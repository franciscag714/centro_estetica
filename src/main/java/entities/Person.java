package entities;

public class Person {
	private int id;
	private String user;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getFirstname() { return firstname; }
	public void setFirstname(String firstname) { this.firstname = firstname; }
	
	public String getLastname() { return lastname; }
	public void setLastname(String lastname) { this.lastname = lastname; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getFullname()
	{
		if (this.getLastname() == null || this.getLastname().trim().isEmpty())
			return this.getFirstname();
		if (this.getFirstname() == null || this.getFirstname().trim().isEmpty())
			return this.getLastname();
		
		return this.getLastname() + ' ' + this.getFirstname();
	}
	
	public void trimFields() {
		this.user = this.user.trim();
		this.password = this.password.trim();
		this.firstname = this.firstname.trim();
		this.lastname = this.lastname.trim();
		this.email = this.email.trim();
	}
}
