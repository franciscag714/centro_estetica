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

public class AppointmentData
{
	/**
	 * This method returns all appointments with DateTime after the current DateTime.
	 * Their clients and employees are not complete.
	 * They only have id, lastname and firstname attributes.
	 */
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
			rs = stmt.executeQuery(""
					+ "SELECT app.id, app.date_time, "
					+ "		cli.id, cli.lastname, cli.firstname, "
					+ "		emp.id, emp.lastname, emp.firstname "
					+ "FROM appointments app "
					+ "INNER JOIN employees emp"
					+ "		ON app.employee_id = emp.id "
					+ "LEFT JOIN clients cli"
					+ "		ON app.client_id = cli.id "
					+ "WHERE app.date_time > NOW() "
					+ "ORDER BY app.date_time;");
			
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDateTime(rs.getObject(2, LocalDateTime.class));
				
				Client c = new Client();
				c.setId(rs.getInt(3));
				c.setLastname(rs.getString(4));
				c.setFirstname(rs.getString(5));
				appointment.setClient(c);
				
				Employee e = new Employee();
				e.setId(rs.getInt(6));
				e.setLastname(rs.getString(7));
				e.setFirstname(rs.getString(8));
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
	
	/**
	 * This method returns all appointments with DateTime before or equal the current DateTime.
	 * Their clients and employees are not complete.
	 * They only have id, lastname and firstname attributes.
	 */
	public LinkedList<Appointment> list2()
	{
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Appointment> appointments = new LinkedList<Appointment>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(""
					+ "SELECT app.id, app.date_time, "
					+ "		cli.id, cli.lastname, cli.firstname, "
					+ "		emp.id, emp.lastname, emp.firstname "
					+ "FROM appointments app "
					+ "INNER JOIN employees emp"
					+ "		ON app.employee_id = emp.id "
					+ "LEFT JOIN clients cli"
					+ "		ON app.client_id = cli.id "
					+ "WHERE app.date_time <= NOW() "
					+ "ORDER BY app.date_time;");
			
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDateTime(rs.getObject(2, LocalDateTime.class));
				
				Client c = new Client();
				c.setId(rs.getInt(3));
				c.setLastname(rs.getString(4));
				c.setFirstname(rs.getString(5));
				appointment.setClient(c);
				
				Employee e = new Employee();
				e.setId(rs.getInt(6));
				e.setLastname(rs.getString(7));
				e.setFirstname(rs.getString(8));
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
	
	/**
	 * This method looks up an appointment by id.
	 * Its client and employee are not complete.
	 * They only have id, lastname and firstname attributes.
	 * @param appointment It must have an id.
	 */
	public Appointment searchById(Appointment appointment)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "SELECT app.id, app.date_time, "
					+ "		cli.id, cli.lastname, cli.firstname, "
					+ "		emp.id, emp.lastname, emp.firstname "
					+ "FROM appointments app "
					+ "INNER JOIN employees emp "
					+ "		ON app.employee_id = emp.id "
					+ "LEFT JOIN clients cli "
					+ "		ON app.client_id = cli.id "
					+ "WHERE app.id = ?;");
			
			pstmt.setInt(1, appointment.getId());
			rs = pstmt.executeQuery();
						
			if (rs.next()) {
				Appointment appointReturn = new Appointment();
				appointReturn.setId(rs.getInt(1));
				appointReturn.setDateTime(rs.getObject(2, LocalDateTime.class));
				
				Client c = new Client();
				c.setId(rs.getInt(3));
				c.setLastname(rs.getString(4));
				c.setFirstname(rs.getString(5));
				appointment.setClient(c);
				
				Employee e = new Employee();
				e.setId(rs.getInt(6));
				e.setLastname(rs.getString(7));
				e.setFirstname(rs.getString(8));
				appointment.setEmployee(e);
				
				return appointReturn;
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
			
			if (appointment.getClient().getId() == 0)
				pstmt.setNull(3, java.sql.Types.BIGINT);
			else
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
	
	public Appointment update(Appointment appointment) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("UPDATE appointments SET date_time=?, employee_id=?, client_id=? WHERE id=?");
			pstmt.setObject(1, appointment.getDateTime());
			pstmt.setInt(2, appointment.getEmployee().getId());

			if (appointment.getClient().getId() == 0)
				pstmt.setNull(3, java.sql.Types.BIGINT);
			else
				pstmt.setInt(3, appointment.getClient().getId());
			
			pstmt.setInt(4, appointment.getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated.");
				return null;
			}
			else 
				return appointment;
			
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
	
	public Appointment delete(Appointment appointment) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM appointments WHERE id=?");
			pstmt.setInt(1, appointment.getId());

			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated.");
				return null;
			}
			else 
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
