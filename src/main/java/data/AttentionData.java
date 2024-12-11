package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Appointment;
import entities.Attention;
import entities.Employee;
import entities.Service;

public class AttentionData {
	
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
					+ "	SELECT att.price, att.appointment_id"
					+ "		, att.service_id, serv.description"
					+ "	FROM attentions att"
					+ "	INNER JOIN services serv"
					+ "		ON att.service_id = serv.id"
					+ "	WHERE att.appointment_id = ?;");
			
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
		
	public Attention add(Attention attention, Employee employee) {
		String filter = employee != null ? " AND employee_id = ?" : "";
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "	INSERT INTO attentions (appointment_id, service_id, price) "
					+ "		SELECT ?, ?, updated_price "
					+ "		FROM services"
					+ "		WHERE id = ?"
					+ "		AND EXISTS ("
					+ "			SELECT 1"
					+ "			FROM appointments"
					+ "			WHERE id = ?"
					+ "			AND client_id IS NOT NULL"
					+ 			filter
					+ "		);");
			
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			pstmt.setInt(3, attention.getService().getId());
			pstmt.setInt(4, attention.getAppointment().getId());
			
			if (employee != null)
				pstmt.setInt(5, employee.getId());
			
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
	
	public Attention delete(Attention attention, Employee employee) {
		String filter = employee != null ? " AND app.employee_id = ?" : "";
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement(""
					+ "	DELETE att FROM attentions att"
					+ "	INNER JOIN appointments app"
					+ "		ON att.appointment_id = app.id"
					+ "	WHERE att.appointment_id = ?"
					+ "		AND att.service_id = ?"
					+		filter
					+ "	;");
			
			pstmt.setInt(1, attention.getAppointment().getId());
			pstmt.setInt(2, attention.getService().getId());
			
			if (employee != null)
				pstmt.setInt(3, employee.getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were deleted.");
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
