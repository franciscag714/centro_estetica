package logic;

import data.ClientData;
import data.EmployeeData;
import entities.Client;
import entities.Employee;
import entities.Person;

public class LoginLogic {
	private ClientData clientData;
	private EmployeeData employeeData;
	
	public LoginLogic() {
		clientData = new ClientData();
		employeeData = new EmployeeData() ;
	}
	
	public Person validate(Person person) {
		Employee emp = new Employee();
		
		emp.setUser(person.getUser());
		emp.setPassword(person.getPassword());
		emp = employeeData.searchByUserAndPassword(emp);
		
		if (emp != null)
			return emp;
		
		Client cli = new Client();

		cli.setUser(person.getUser());
		cli.setPassword(person.getPassword());
		cli = clientData.searchByUserAndPassword(cli);
		
		if (cli != null)
			return cli;
		
		return null;
	}
}
