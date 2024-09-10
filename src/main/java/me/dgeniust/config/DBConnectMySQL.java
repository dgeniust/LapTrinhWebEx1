package me.dgeniust.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectMySQL {
	private static Connection con = null;
	private static String USERNAME = "root";
	private static String PASSWORD = "diemquynh2207";
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/ltweb";

	public static Connection getDatabaseConnection() throws ClassNotFoundException {
		try {
			Class.forName(DRIVER);
			return setConnection(DriverManager.getConnection(URL, USERNAME, PASSWORD));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection() {
		return con;
	}

	public static Connection setConnection(Connection conn) {
		con = conn;
		return getConnection();
	}

	public static void main(String[] args) {
		try {
			new DBConnectMySQL();
			System.out.print(DBConnectMySQL.getDatabaseConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}