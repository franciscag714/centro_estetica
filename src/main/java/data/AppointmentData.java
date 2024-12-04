package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.Appointment;
import entities.AppointmentFilter;
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
		
		LinkedList<Appointment> appointments = new LinkedList<>();
		
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
	
	public LinkedList<Appointment> listAvailable(AppointmentFilter appointFilter)
	{
		StringBuilder filter = new StringBuilder();
		
		if (appointFilter.getDate() != null)
			filter.append("AND DATE(date_time) = ? ");
		
		if (appointFilter.getStartTime() != null)
			filter.append("AND TIME(date_time) >= ? ");
		
		if (appointFilter.getEndTime() != null)
			filter.append("AND TIME(date_time) <= ? ");	//analizar si puedo evitar usar función en el where ya que no aprovecha índices...
		
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LinkedList<Appointment> appointments = new LinkedList<>();
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "SELECT app.id, app.date_time "
					+ "FROM appointments app "
					+ "WHERE app.client_id IS NULL "
					+ "AND app.date_time > NOW() "
					+ filter
					+ "ORDER BY app.date_time;");
			
			int i = 1;
			if (appointFilter.getDate() != null)
				pstmt.setDate(i++, Date.valueOf(appointFilter.getDate()));
			
			if (appointFilter.getStartTime() != null)
				pstmt.setTime(i++, Time.valueOf(appointFilter.getStartTime()));
			
			if (appointFilter.getEndTime() != null)
				pstmt.setTime(i++, Time.valueOf(appointFilter.getEndTime()));
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDateTime(rs.getObject(2, LocalDateTime.class));
				appointments.add(appointment);
			}
			return appointments;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
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
	
	/**
	 * This method books an appointment. To book, the client_id in the database must be null.
	 * Returns the booked appointment with the client's first name, last name and email, and the employee's first name and last name.
	 * If the appointment was not booked, returns null.
	 * @param appointment It must have id and clientId.
	 */
	public Appointment book(Appointment appointment)
	{
		DbConnector db = new DbConnector();
		Connection cn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			cn.setAutoCommit(false);
			
			pstmt = cn.prepareStatement(""
					+ "	UPDATE appointments"
					+ "	SET client_id = ?"
					+ "	WHERE id = ?"
					+ "		AND client_id IS NULL"
					+ "		AND date_time > NOW()"
					+ "	ORDER BY date_time;");
			
			pstmt.setInt(1, appointment.getClient().getId());
			pstmt.setInt(2, appointment.getId());
			
			if (pstmt.executeUpdate() == 0) {
				cn.rollback();
				return null;
			}
			
			pstmt = cn.prepareStatement(""
					+ "	SELECT cli.firstname, cli.lastname, cli.email"
					+ "		, emp.firstname, emp.lastname"
					+ "	FROM appointments app"
					+ "	INNER JOIN clients cli"
					+ "		ON cli.id = app.client_id"
					+ "	INNER JOIN employees emp"
					+ "		ON emp.id = app.employee_id"
					+ "	WHERE app.id = ?;");
			
			pstmt.setInt(1, appointment.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Client c = new Client();
				c.setFirstname(rs.getString(1));
				c.setLastname(rs.getString(2));
				c.setEmail(rs.getString(3));
				appointment.setClient(c);
				
				Employee e = new Employee();
				e.setFirstname(rs.getString(4));
				e.setLastname(rs.getString(5));
				appointment.setEmployee(e);
				
				cn.commit();
				return appointment;
			}
			
			cn.rollback();
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
                cn.rollback();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
			
			return null;
		}
		finally {
			try {
				if (cn != null)
					cn.setAutoCommit(true);
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
	
	/**
	 * This method returns all appointments with DateTime less than or equal to the current DateTime plus 1 hour where Client is not null.
	 * Their clients and employees are not complete.
	 * They only have id, lastname and firstname attributes.
	 */
	public LinkedList<Appointment> listPast()
	{
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Appointment> appointments = new LinkedList<>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(""
					+ "	SELECT app.id, app.date_time"
					+ "		, cli.id, cli.lastname, cli.firstname"
					+ "		, emp.id, emp.lastname, emp.firstname"
					+ "		, SUM(att.price)"
					+ "	FROM appointments app"
					+ "	INNER JOIN employees emp"
					+ "		ON app.employee_id = emp.id"
					+ "	INNER JOIN clients cli"
					+ "		ON app.client_id = cli.id"
					+ "	LEFT JOIN attentions att"
					+ "		ON att.appointment_id = app.id"
					+ "	WHERE app.date_time <= ADDDATE(NOW(), INTERVAL 1 HOUR)"
					+ "	GROUP BY app.id, cli.id, emp.id"
					+ "	ORDER BY app.date_time DESC;");
			
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
				
				appointment.setTotalIncome(rs.getDouble(9));
				appointments.add(appointment);
			}
			return appointments;
			
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
	
	public Appointment update(Appointment appointment) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
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
			
			return appointment;
			
		} catch (SQLException e) {
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
				System.out.println("No rows were deleted.");
				return null;
			}
			
			return appointment;
			
		} catch (SQLException e) {
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
