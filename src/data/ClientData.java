package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Client;

public class ClientData {
	public LinkedList<Client> list(){
		DbConnector db = new DbConnector();
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Client> clients = new LinkedList<Client>();
		
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id, firstname, lastname, email FROM clients");
			
			while (rs.next()) {
				Client client = new Client();
				
				client.setId(rs.getInt(1));
				client.setFirstname(rs.getString(2));
				client.setLastname(rs.getString(3));
				client.setEmail(rs.getString(4));
				
				clients.add(client);
			}
			
			return clients;
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

	public Client searchById(Client cli){
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email FROM clients WHERE id=?");
			
			pstmt.setInt(1, cli.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Client client = new Client();
				
				client.setId(rs.getInt(1));
				client.setFirstname(rs.getString(2));
				client.setLastname(rs.getString(3));
				client.setEmail(rs.getString(4));
				
				return client;
			}
			return null;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Client searchByUser(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email FROM clients WHERE user=? AND password=?");
			
			pstmt.setString(1, cli.getUser());
			pstmt.setString(2, cli.getPassword());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Client client = new Client();
				
				client.setId(rs.getInt(1));
				client.setFirstname(rs.getString(2));
				client.setLastname(rs.getString(3));
				client.setEmail(rs.getString(4));
				
				return client;
			}
			return null;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Client add(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO clients(id, user, password, firstname, lastname, email) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, cli.getId());
			pstmt.setString(2, cli.getUser());
			pstmt.setString(3, cli.getPassword());
			pstmt.setString(4, cli.getFirstname());
			pstmt.setString(5, cli.getLastname());
			pstmt.setString(6, cli.getEmail());
			
			rs = pstmt.executeQuery();
			rs = pstmt.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
				cli.setId(rs.getInt(1));
				return cli;
			}
			return null;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Client update(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Client client = searchById(cli);
		
		if (client == null) {
			return null;
		}
		try {
			conn= db.getConnection();
			pstmt = conn.prepareStatement("UPDATE clients SET id=?, user=?, password=?, firstname=?, lastname=?, email=? WHERE id=?");
			pstmt.setInt(1, cli.getId());
			pstmt.setString(2, cli.getUser());
			pstmt.setString(3, cli.getPassword());
			pstmt.setString(4, cli.getFirstname());
			pstmt.setString(5, cli.getLastname());
			pstmt.setString(6, cli.getEmail());
			
			pstmt.executeUpdate();
			return cli;
		}catch (SQLException e) {
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
	
	public Client delete(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		
		Client client = searchById(cli);
		if (client == null) {
			return null;
		}
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM clients WHERE id=?");
			pstmt.setInt(1, client.getId());
			pstmt.executeUpdate();
			return client;
			
		}catch (SQLException e) {
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
