package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Employee;

public class EmployeeData {
	public LinkedList<Employee> list(){
		DbConnector db = new DbConnector();
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Employee> employees = new LinkedList<Employee>();
		
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id, firstname, lastname, email, is_admin FROM employees");
			
			while (rs.next()) {
				Employee employee = new Employee();
					
				employee.setId(rs.getInt(1));
				employee.setFirstname(rs.getString(2));
				employee.setLastname(rs.getString(3));
				employee.setEmail(rs.getString(4));
				employee.setIsAdmin(rs.getBoolean(5));
					
				employees.add(employee);
			}
				
			return employees;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Employee searchById(Employee emp) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email, is_admin FROM employees WHERE id=?");
			
			pstmt.setInt(1, emp.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Employee employee = new Employee();
				
				employee.setId(rs.getInt(1));
				employee.setFirstname(rs.getString(2));
				employee.setLastname(rs.getString(3));
				employee.setEmail(rs.getString(4));
				employee.setIsAdmin(rs.getBoolean(5));
				
				return employee;
			}
			return null;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Employee searchByUser(Employee emp) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email, is_admin FROM employees WHERE user=? AND password=?");
			
			pstmt.setString(1, emp.getUser());
			pstmt.setString(2, emp.getPassword());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Employee employee = new Employee();
				
				employee.setId(rs.getInt(1));
				employee.setFirstname(rs.getString(2));
				employee.setLastname(rs.getString(3));
				employee.setEmail(rs.getString(4));
				employee.setIsAdmin(rs.getBoolean(5));
				
				return employee;
			}
			return null;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Employee add(Employee emp) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO employees(id, user, password, firstname, lastname, email, is_admin) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, emp.getId());
			pstmt.setString(2, emp.getUser());
			pstmt.setString(3, emp.getPassword());
			pstmt.setString(4, emp.getFirstname());
			pstmt.setString(5, emp.getLastname());
			pstmt.setString(6, emp.getEmail());
			pstmt.setBoolean(7, emp.getIsAdmin());
			
			rs = pstmt.executeQuery();
			rs = pstmt.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
				emp.setId(rs.getInt(1));
				return emp;
			}
			return null;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Employee update(Employee emp) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Employee employee = searchById(emp);
		
		if (employee == null) {
			return null;
		}
		try {
			conn= db.getConnection();
			pstmt = conn.prepareStatement("UPDATE employees SET id=?, user=?, password=?, firstname=?, lastname=?, email=?, is_admin=? WHERE id=?");
			pstmt.setInt(1, emp.getId());
			pstmt.setString(2, emp.getUser());
			pstmt.setString(3, emp.getPassword());
			pstmt.setString(4, emp.getFirstname());
			pstmt.setString(5, emp.getLastname());
			pstmt.setString(6, emp.getEmail());
			pstmt.setBoolean(7, emp.getIsAdmin());
			
			pstmt.executeUpdate();
			return emp;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Employee delete(Employee emp) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		
		Employee employee = searchById(emp);
		if (employee == null) {
			return null;
		}
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM employees WHERE id=?");
			pstmt.setInt(1, employee.getId());
			pstmt.executeUpdate();
			return employee;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
