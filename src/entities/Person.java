package entities;

public abstract class Person {
	private int id;
	private String user;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private String fullname;
	
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
	
	public String getFullname() { return fullname; }
	public void setFullname(String fullname) { this.fullname = fullname; }
	
	@Override
	public String toString() {
		if (fullname == null)
			return fullname;
		return lastname + ' ' + firstname;
	}
}
