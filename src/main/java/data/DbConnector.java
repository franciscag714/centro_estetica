package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector
{
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String host = "127.0.0.1";
	private String port = "3306";
	private String user = "centro";
	private String password = "estetica2024";
	private String db = "centro_estetica";
	
	private int conectados = 0;
	private static DbConnector instancia;
	private Connection conn = null;
	
	public DbConnector() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static DbConnector getInstancia() {
		if (instancia == null)
			instancia = new DbConnector();
		return instancia;
	}
	
	public Connection getConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				conn= DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
				conectados = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conectados++;
		return conn;
	}
	
	public void releaseConnection() {
		conectados--;
		try {
			if (conectados <= 0)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
