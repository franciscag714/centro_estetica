package data;

import java.sql.*;
import java.util.LinkedList;
import entities.ServiceType;

public class ServiceTypeData
{
	public LinkedList<ServiceType> list()
	{
		DbConnector db = new DbConnector();
		Connection cn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<ServiceType> servTypes = new LinkedList<ServiceType>();
		
		try {
			cn = db.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM service_types");
			
			while (rs.next()) {
				ServiceType servType = new ServiceType();
				servType.setId(rs.getInt("id"));
				servType.setDescription(rs.getString("description"));
				servTypes.add(servType);
			}
			return servTypes;
			
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
	
	public ServiceType searchById(ServiceType stParam)
	{
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServiceType servType;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("SELECT * FROM service_types WHERE id=?");
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
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
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
	
	public ServiceType update(ServiceType stParam) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServiceType servType = searchById(stParam);
		if (servType == null)
			return null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("UPDATE service_types SET description=? WHERE id=?");
			pstmt.setString(1, stParam.getDescription());
			pstmt.setInt(2, stParam.getId());
			pstmt.executeUpdate();
			return stParam;
			
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
	
	public ServiceType delete(ServiceType stParam) {
		DbConnector db = new DbConnector();
		Connection cn;
		PreparedStatement pstmt = null;
		ServiceType servType = searchById(stParam);
		if (servType == null)
			return null;
		
		try {
			cn = db.getConnection();
			pstmt = cn.prepareStatement("DELETE FROM service_types WHERE id=?");
			pstmt.setInt(1, stParam.getId());
			pstmt.executeUpdate();
			return servType;
			
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
