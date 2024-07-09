package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Service;
import entities.ServiceType;

public class ServiceData
{
	public LinkedList<Service> list()
	{
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Service> services = new LinkedList<Service>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT services.id, services.description, services.updated_price, services_types.id, services_types.description "
					+ "FROM services INNER JOIN service_types ON services.service_type_id = service_types.id");
			
			while (rs.next()) {
				Service service = new Service();
				service.setId(rs.getInt(1));
				service.setDescription(rs.getString(2));
				service.setUpdatedPrice(rs.getDouble(3));
				
				ServiceType servType = new ServiceType();
				servType.setId(rs.getInt(4));
				servType.setDescription(rs.getString(5));
				service.setType(servType);
				
				services.add(service);
			}
			return services;
			
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
	
	public Service searchById(Service servParam)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("SELECT services.id, services.description, services.updated_price, services_types.id, services_types.description "
					+ "FROM services INNER JOIN service_types ON services.service_type_id = service_types.id WHERE id=?");
			pstmt.setInt(1, servParam.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Service service = new Service();
				service.setId(rs.getInt(1));
				service.setDescription(rs.getString(2));
				service.setUpdatedPrice(rs.getDouble(3));
				
				ServiceType servType = new ServiceType();
				servType.setId(rs.getInt(4));
				servType.setDescription(rs.getString(5));
				service.setType(servType);
				
				return service;
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

	public Service add(Service service) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("INSERT INTO services(description, updated_price, service_type_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, service.getDescription());
			pstmt.setDouble(2, service.getUpdatedPrice());
			pstmt.setInt(3, service.getType().getId());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if (rs != null && rs.next())
			{
				service.setId(rs.getInt(1));
				return service;
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
	
	public Service update(Service servParam) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Service service = searchById(servParam);
		
		if (service == null)
			return null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("UPDATE services SET description=?, updated_price=?, service_type_id=? WHERE id=?");
			pstmt.setString(1, service.getDescription());
			pstmt.setDouble(2, service.getUpdatedPrice());
			pstmt.setInt(3, service.getType().getId());
			pstmt.setInt(4, service.getId());
			
			pstmt.executeUpdate();
			return servParam;
			
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
	
	public Service delete(Service servParam) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		Service service = searchById(servParam);
		if (service == null)
			return null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM services WHERE id=?");
			pstmt.setInt(1, service.getId());
			pstmt.executeUpdate();
			return service;
			
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