package logic;

import java.util.LinkedList;

import data.EmployeeData;
import entities.Employee;

public class LoginEmp {
	private EmployeeData ed;
	
	public LoginEmp() {
		ed = new EmployeeData() ;
	}
	
	public Employee validate(Employee emp) {
		/* para hacer más seguro el manejo de passwords este sería un lugar adecuado para generar un hash de la password utilizando un cifrado
		 asimétrico como sha256 y utilizar el hash en lugar de la password en plano  */
		return ed.searchByUser(emp);
	}

	public LinkedList<Employee> list(){
		return ed.list();
	}
}
