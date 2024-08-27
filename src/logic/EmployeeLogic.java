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
		return employeeData.add(e);
	}
	
	public Employee update(Employee e) {
		return employeeData.update(e);
	}
	
	public Employee delete(Employee e) {
		return employeeData.delete(e);
	}
	
	public Employee findById(int id) {
		return employeeData.findById(id);
	}

}
