package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.mindrot.jbcrypt.BCrypt;

import entities.Employee;


public class EmployeeData {
	public LinkedList<Employee> list(){
		DbConnector db = new DbConnector();
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Employee> employees = new LinkedList<>();
		
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id, firstname, lastname, email, user, is_admin FROM employees");
			
			while (rs.next()) {
				Employee employee = new Employee();
					
				employee.setId(rs.getInt(1));
				employee.setFirstname(rs.getString(2));
				employee.setLastname(rs.getString(3));
				employee.setEmail(rs.getString(4));
				employee.setUser(rs.getString(5));
				employee.setIsAdmin(rs.getBoolean(6));
					
				employees.add(employee);
			}
			
			return employees;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
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
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email, user, is_admin FROM employees WHERE id=?");
			
			pstmt.setInt(1, emp.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Employee employee = new Employee();
				
				employee.setId(rs.getInt(1));
				employee.setFirstname(rs.getString(2));
				employee.setLastname(rs.getString(3));
				employee.setEmail(rs.getString(4));
				employee.setUser(rs.getString(5));
				employee.setIsAdmin(rs.getBoolean(6));
				
				return employee;
			}
			
			return null;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Employee searchByUserAndPassword(Employee emp) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email, user, password, is_admin FROM employees WHERE user = ?");
			pstmt.setString(1, emp.getUser());
			
			rs = pstmt.executeQuery();
			
			if (rs.next() && BCrypt.checkpw(emp.getPassword(), rs.getString(6)))
			{
				Employee employee = new Employee();
				
				employee.setId(rs.getInt(1));
				employee.setFirstname(rs.getString(2));
				employee.setLastname(rs.getString(3));
				employee.setEmail(rs.getString(4));
				employee.setUser(rs.getString(5));
				employee.setIsAdmin(rs.getBoolean(7));
				
				return employee;
			}
			
			return null;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
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
			pstmt = conn.prepareStatement(""
					+ "	INSERT INTO employees(user, password, firstname, lastname, email, is_admin)"
					+ "	VALUES (?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, emp.getUser());
			pstmt.setString(2, BCrypt.hashpw(emp.getPassword(), BCrypt.gensalt()));
			pstmt.setString(3, emp.getFirstname());
			pstmt.setString(4, emp.getLastname());
			pstmt.setString(5, emp.getEmail());
			pstmt.setBoolean(6, emp.isAdmin());
			
			pstmt.executeUpdate();
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
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
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
		
		try {
			StringBuilder query = new StringBuilder("UPDATE employees SET user=?, firstname=?, lastname=?, email=?, is_admin=? ");
			if (emp.getPassword() != "")
				query.append(", password=? ");
			
			query.append("WHERE id = ?");
			
			conn= db.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(1, emp.getUser());
			pstmt.setString(2, emp.getFirstname());
			pstmt.setString(3, emp.getLastname());
			pstmt.setString(4, emp.getEmail());
			pstmt.setBoolean(5, emp.isAdmin());
			
			if (emp.getPassword() != "") {
				pstmt.setString(6, BCrypt.hashpw(emp.getPassword(), BCrypt.gensalt()));
				pstmt.setInt(7, emp.getId());
			}
			else
				pstmt.setInt(6, emp.getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated.");
				return null;
			}
			
			return emp;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				if (pstmt != null)
					pstmt.close();
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
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM employees WHERE id = ?");
			pstmt.setInt(1, emp.getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were deleted.");
				return null;
			}
			
			return emp;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				if (pstmt != null)
					pstmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
