package logic;

import java.util.LinkedList;

import data.EmployeeData;
import entities.Employee;

public class EmployeeLogic {
	private EmployeeData employeeData;

	public EmployeeLogic() {
		employeeData = new EmployeeData();
	}

	public LinkedList<Employee> list() {
		return employeeData.list();
	}

	public Employee create(Employee e) {
		if (e.getFirstname().isBlank() || e.getLastname().isBlank() || e.getEmail().isBlank() || e.getUser().isBlank()
				|| e.getPassword().trim().length() < 4)
			return null;

		return employeeData.add(e);
	}

	public Employee update(Employee e) {
		if (e.getFirstname().isBlank() || e.getLastname().isBlank() || e.getEmail().isBlank() || e.getUser().isBlank())
			return null;

		String password = e.getPassword().trim();

		if (!password.isEmpty() && password.length() < 4)
			return null;

		return employeeData.update(e);
	}

	public Employee delete(Employee e) {
		return employeeData.delete(e);
	}

	public Employee searchById(Employee e) {
		return employeeData.searchById(e);
	}
}
