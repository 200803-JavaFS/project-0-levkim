package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectUtil {
	
	public static Connection getConnection() throws SQLException {
		
		// for compatibility for other frameworks, you need to register a Driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// never ever do this. don't hardcode your creds into your code.
		// but it's fine to do for this project.
		String url = "jdbc:postgresql://demosdb.cnhivgivm0u1.us-east-1.rds.amazonaws.com:5432/project0";
		String username = "postgres";
		String password = "password";
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
	public static void main(String[] args) {
		try (Connection conn = ConnectUtil.getConnection()) {
			System.out.println("connection successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
