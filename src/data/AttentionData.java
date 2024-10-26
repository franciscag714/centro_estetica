package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.Appointment;
import entities.Attention;
import entities.Client;
import entities.Employee;
import entities.Service;

public class AttentionData {
	
	public LinkedList<Attention> list(){
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Attention> attentions = new LinkedList<>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(""
					+ "SELECT att.price"
					+ "		, att.appointment_id, app.date_time "
					+ "		, cli.id, cli.firstname, cli.lastname "
					+ "	    , emp.id, emp.firstname, emp.lastname "
					+ "		, att.service_id, serv.description"
					+ "FROM attentions att "
					+ "INNER JOIN appointments app "
					+ "		ON att.appointment_id = app.id "
					+ "INNER JOIN clients cli "
					+ "		ON app.client_id = cli.id "
					+ "INNER JOIN employees emp "
					+ "		ON app.employee_id = emp.id "
					+ "INNER JOIN services serv "
					+ "		ON  att.service_id = serv.id "
					+ "ORDER BY app.date_time;");
			 
			while (rs.next()) {
				Attention attention = new Attention();
				attention.setPrice(rs.getDouble(1));
				
				Appointment app = new Appointment();
				app.setId(rs.getInt(2));
				app.setDateTime(rs.getObject(3, LocalDateTime.class));
				
				Client c = new Client();
				c.setId(rs.getInt(4));
				c.setFirstname(rs.getString(5));
				c.setLastname(rs.getString(6));
				app.setClient(c);
				
				Employee e = new Employee();
				e.setId(rs.getInt(7));
				e.setFirstname(rs.getString(8));
				e.setLastname(rs.getString(9));
				app.setEmployee(e);
				
				Service s = new Service();
				s.setId(rs.getInt(10));
				s.setDescription(rs.getString(11));
				
				attention.setAppointment(app);
				attention.setService(s);
				attentions.add(attention);
			}
			return attentions;
			
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
	
	public LinkedList<Attention> searchByAppointment(Appointment appointment)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		LinkedList<Attention> attentions = new LinkedList<>();
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "SELECT att.price, att.appointment_id"
					+ "		, att.service_id, serv.description "
					+ "FROM attentions att "
					+ "INNER JOIN services serv "
					+ "		ON  att.service_id = serv.id "
					+ "WHERE att.appointment_id = ?;");
			
			pstmt.setInt(1, appointment.getId());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Attention att = new Attention();
				att.setPrice(rs.getDouble(1));
				
				Appointment app = new Appointment();
				app.setId(rs.getInt(2));
				att.setAppointment(app);
				
				Service s = new Service();
				s.setId(rs.getInt(3));
				s.setDescription(rs.getString(4));
				att.setService(s);
				
				attentions.add(att);
			}
			return attentions;
			
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
		
	public Attention add(Attention attention) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "	INSERT INTO attentions(appointment_id, service_id, price) "
					+ "		SELECT ?, ?, updated_price "
					+ "		FROM services WHERE id=?;");
			
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			pstmt.setInt(3, attention.getService().getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were added.");
				return null;
			}
			
			return attention;
			
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
	
	//at the moment this method is not used
	public Attention update(Attention attention) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("UPDATE attentions SET appointment_id=?, service_id=? WHERE appointment_id=? AND service_id=? ");
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated. ");
				return null;
			}
			
			return attention;
					
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
	
	public Attention delete(Attention attention) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM attentions WHERE appointment_id=? AND service_id=?");
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were deleted. ");
				return null;
			}
			
			return attention;
			
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
