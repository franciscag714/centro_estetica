package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.Appointment;
import entities.Client;
import entities.Employee;
import entities.Service;
import entities.ServiceType;

public class AppointmentData
{
	public LinkedList<Appointment> list()
	{
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Appointment> appointments = new LinkedList<Appointment>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT appointments.id, appointments.datetime, CONCAT(clients.lastname, ' ', clients.firstname) as client, "
					+ "CONCAT(employee.lastname, ' ', employee.firstname) as employee FROM appointments INNER JOIN clients ON appointments.client_id = clients.id "
					+ "INNER JOIN employees ON appointments.employee_id = employees.id");
			
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDateTime(rs.getObject(2, LocalDateTime.class));
				
				Client c = new Client();
				c.setFullname(rs.getString(3));
				appointment.setClient(c);
				
				Employee e = new Employee();
				e.setFullname(rs.getString(4));
				appointment.setEmployee(e);
				
				appointments.add(appointment);
			}
			return appointments;
			
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
	
	public Appointment searchById(Appointment appointParam)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("SELECT appointments.id, appointments.datetime, CONCAT(clients.lastname, ' ', clients.firstname) as client, "
					+ "CONCAT(employee.lastname, ' ', employee.firstname) as employee FROM appointments INNER JOIN clients ON appointments.client_id = clients.id "
					+ "INNER JOIN employees ON appointments.employee_id = employees.id WHERE appointments.id=?");
			pstmt.setInt(1, appointParam.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDateTime(rs.getObject(2, LocalDateTime.class));
				
				Client c = new Client();
				c.setFullname(rs.getString(3));
				appointment.setClient(c);
				
				Employee e = new Employee();
				e.setFullname(rs.getString(4));
				appointment.setEmployee(e);
				
				return appointment;
			}
			return null;
			
		} catch (SQLException e) {
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

	public Appointment add(Appointment appointment) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("INSERT INTO appointments(date_time, employee_id, client_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setObject(1, appointment.getDateTime());
			pstmt.setInt(2, appointment.getEmployee().getId());
			pstmt.setInt(3, appointment.getClient().getId());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if (rs != null && rs.next())
			{
				appointment.setId(rs.getInt(1));
				return appointment;
			}
			
			return null;
		} catch (SQLException e) {
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
	
	public Appointment update(Appointment appointParam) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Appointment appointment = searchById(appointParam);
		
		if (appointment == null)
			return null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("UPDATE appointments SET datetime=?, employee_id=?, client_id=? WHERE id=?");
			pstmt.setObject(1, appointment.getDateTime());
			pstmt.setInt(2, appointment.getEmployee().getId());
			pstmt.setInt(3, appointment.getClient().getId());
			
			pstmt.executeUpdate();
			return appointParam;
			
		} catch (SQLException e) {
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
	
	public Appointment delete(Appointment appointParam) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		Appointment appointment = searchById(appointParam);
		if (appointment == null)
			return null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM appointments WHERE id=?");
			pstmt.setInt(1, appointment.getId());
			pstmt.executeUpdate();
			return appointment;
			
		} catch (SQLException e) {
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
