package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.mindrot.jbcrypt.BCrypt;

import entities.Client;

public class ClientData {
	public LinkedList<Client> list(){
		DbConnector db = new DbConnector();
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Client> clients = new LinkedList<>();
		
		try {
			conn = db.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id, firstname, lastname, email, user FROM clients ORDER BY lastname");
			
			while (rs.next()) {
				Client client = new Client();
				
				client.setId(rs.getInt(1));
				client.setFirstname(rs.getString(2));
				client.setLastname(rs.getString(3));
				client.setEmail(rs.getString(4));
				client.setUser(rs.getString(5));
				
				clients.add(client);
			}
			return clients;
			
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

	public Client searchById(Client cli){
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email, user FROM clients WHERE id=?");
			
			pstmt.setInt(1, cli.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Client client = new Client();
				
				client.setId(rs.getInt(1));
				client.setFirstname(rs.getString(2));
				client.setLastname(rs.getString(3));
				client.setEmail(rs.getString(4));
				client.setUser(rs.getString(5));
				
				return client;
			}
			
			return null;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
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
	
	public Client searchByUserAndPassword(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("SELECT id, firstname, lastname, email, user, password FROM clients WHERE user = ?");
			pstmt.setString(1, cli.getUser());
			
			rs = pstmt.executeQuery();
			
			if (rs.next() && BCrypt.checkpw(cli.getPassword(), rs.getString(6)))
			{
				Client client = new Client();
				
				client.setId(rs.getInt(1));
				client.setFirstname(rs.getString(2));
				client.setLastname(rs.getString(3));
				client.setEmail(rs.getString(4));
				client.setUser(rs.getString(5));
				
				return client;
			}
			
			return null;
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
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
	
	public Client add(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO clients(user, password, firstname, lastname, email) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, cli.getUser());
			pstmt.setString(2, BCrypt.hashpw(cli.getPassword(), BCrypt.gensalt()));
			pstmt.setString(3, cli.getFirstname());
			pstmt.setString(4, cli.getLastname());
			pstmt.setString(5, cli.getEmail());
			
			pstmt.executeUpdate();
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

	public Client update(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder query = new StringBuilder("UPDATE clients SET user=?, firstname=?, lastname=?, email=? ");
			if (cli.getPassword() != "")
				query.append(", password=? ");
			
			query.append("WHERE id = ?");
			
			conn = db.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(1, cli.getUser());
			pstmt.setString(2, cli.getFirstname());
			pstmt.setString(3, cli.getLastname());
			pstmt.setString(4, cli.getEmail());
			
			if (cli.getPassword() != "") {
				pstmt.setString(5, BCrypt.hashpw(cli.getPassword(), BCrypt.gensalt()));
				pstmt.setInt(6, cli.getId());
			}
			else
				pstmt.setInt(5, cli.getId());
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were updated.");
				return null;
			}
			
			return cli;
			
		}catch (SQLException e) {
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
	
	public Client delete(Client cli) {
		DbConnector db = new DbConnector();
		Connection conn;
		PreparedStatement pstmt = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM clients WHERE id=?");
			pstmt.setInt(1, cli.getId());
			pstmt.executeUpdate();
			
			if (pstmt.executeUpdate() == 0)
			{
				System.out.println("No rows were deleted.");
				return null;
			}
			
			return cli;
			
		}catch (SQLException e) {
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
