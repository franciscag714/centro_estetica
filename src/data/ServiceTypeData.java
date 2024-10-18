package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.ServiceType;

public class ServiceTypeData
{
	public LinkedList<ServiceType> list(Boolean populateServices)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<ServiceType> servTypes = new LinkedList<>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT id, description FROM service_types ORDER BY description;");
			
			while (rs.next()) {
				ServiceType servType = new ServiceType();
				servType.setId(rs.getInt("id"));
				servType.setDescription(rs.getString("description"));
				
				if (populateServices) {
					servType.setServices(serviceData.searchByType(servType));
				}
				
				servTypes.add(servType);
			}
			return servTypes;
			
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
	
	public ServiceType searchById(ServiceType stParam)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServiceType servType;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("SELECT id, description FROM service_types WHERE id=?");
			pstmt.setInt(1, stParam.getId());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				servType = new ServiceType();
				servType.setId(rs.getInt("id"));
				servType.setDescription(rs.getString("description"));
				return servType;
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

	public ServiceType add(ServiceType servType) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("INSERT INTO service_types(description) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, servType.getDescription());
						
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if (rs != null && rs.next())
			{
				servType.setId(rs.getInt(1));
				return servType;
			}
			
			return null;
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated.");
				return null;
			}
			return servType;

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
	
	public ServiceType update(ServiceType servType) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("UPDATE service_types SET description=? WHERE id = ?");
			pstmt.setString(1, servType.getDescription());
			pstmt.setInt(2, servType.getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated.");
				return null;
			}
			
			return servType;
			
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
	
	public ServiceType delete(ServiceType servType) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM service_types WHERE id = ?");
			pstmt.setInt(1, servType.getId());
			pstmt.executeUpdate();
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were deleted.");
				return null;
			}
			
			return servType;
			
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
