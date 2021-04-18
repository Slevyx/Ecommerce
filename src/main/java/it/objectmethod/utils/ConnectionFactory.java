package it.objectmethod.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private static final String URL = "jdbc:mysql://localhost:33060/ecommerce";
	private static final String USERNAME = "omdev";
	private static final String PASSWORD = "omdev";
	
	public static Connection getConnection() {
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
