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
import entities.ServiceType;

public class AttentionData {
	
	public LinkedList<Attention> list(){
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Attention> attentions = new LinkedList<Attention>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery(""
					+ "SELECT att.appointment_id, att.service_id "
					+ "		, app.date_time "
					+ "		, cli.id, cli.firstname, cli.lastname "
					+ "	    , emp.id, emp.firstname, emp.lastname "
					+ "	    , serv.description , serv.updated_price, serv.service_type_id "
					+ "     , st.description "
					+ "FROM attentions att "
					+ "INNER JOIN appointments app "
					+ "		ON att.appointment_id = app.id "
					+ "INNER JOIN clients cli "
					+ "		ON app.client_id = cli.id "
					+ "INNER JOIN employees emp "
					+ "		ON app.employee_id = emp.id "
					+ "INNER JOIN services serv "
					+ "		ON  att.service_id = serv.id "
					+ "INNER JOIN service_types st "
					+ "		ON serv.service_type_id = st.id "
					+ "WHERE app.date_time <= NOW() "
					+ "ORDER BY app.date_time ");
			 
			while (rs.next()) {
				Attention attention = new Attention();
				
				Appointment app = new Appointment();
				app.setId(rs.getInt(1));
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
				
				attention.setAppointment(app);
				
				Service s = new Service();
				s.setId(rs.getInt(2));
				s.setDescription(rs.getString(10));
				s.setUpdatedPrice(rs.getDouble(11));
				
				ServiceType st = new ServiceType();
				st.setId(rs.getInt(12));
				st.setDescription(rs.getString(13));
				s.setType(st);
				
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
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 
	
	//mmm no se
	public LinkedList<Attention> searchByAppointment(Appointment appointment)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		LinkedList<Attention> attentions = new LinkedList<Attention>();
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "SELECT att.appointment_id, att.service_id "
					+ "		, app.date_time "
					+ "		, cli.id, cli.firstname, cli.lastname "
					+ "	    , emp.id, emp.firstname, emp.lastname "
					+ "	    , serv.description , serv.updated_price, serv.service_type_id "
					+ "     , st.description "
					+ "FROM attentions att "
					+ "INNER JOIN appointments app "
					+ "		ON att.appointment_id = app.id "
					+ "INNER JOIN clients cli "
					+ "		ON app.client_id = cli.id "
					+ "INNER JOIN employees emp "
					+ "		ON app.employee_id = emp.id "
					+ "INNER JOIN services serv "
					+ "		ON  att.service_id = serv.id "
					+ "INNER JOIN service_types st "
					+ "		ON serv.service_type_id = st.id "
					+ "WHERE app.id = ?; ");
			
			pstmt.setInt(1, appointment.getId());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Attention attentionReturn = new Attention();
				
				Appointment app = new Appointment();
				app.setId(rs.getInt(1));
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
				
				attentionReturn.setAppointment(app);
				
				Service s = new Service();
				s.setId(rs.getInt(2));
				s.setDescription(rs.getString(10));
				s.setUpdatedPrice(rs.getDouble(11));
				
				ServiceType st = new ServiceType();
				st.setId(rs.getInt(12));
				st.setDescription(rs.getString(13));
				s.setType(st);
				
				attentionReturn.setService(s);
				
				attentions.add(attentionReturn);
			}
			return attentions;
			
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
	
	//add, update y delete no se si estan bien, me confuncde que no tenga el atributo id :'(
	
	//no se si esta bien
	public Attention add(Attention attention) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("INSERT INTO attentions(appointment_id, service_id) VALUES (?, ?)");
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			
			pstmt.executeUpdate();
			
			return attention;
			
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
	
	//no se si esta bien
	public Attention update(Attention attention) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
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
			else
				return attention;
			
					
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
	
	//no se si esta bien
	public Attention delete(Attention attention) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM attentions WHERE appointment_id=? AND service_id=?");
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updates. ");
				return null;
			}
			else
				return attention;
			
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
	
}
