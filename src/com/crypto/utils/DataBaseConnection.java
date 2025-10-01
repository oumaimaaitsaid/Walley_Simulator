package com.crypto.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	private static String url = ("jdbc:postgresql://localhost:5433/crypto_wallet");
	private static String user = "postgres";
	private static String password = "admin";

	private static Connection connection;
	private DataBaseConnection() {
        throw new UnsupportedOperationException("instanciation interdite");
    }
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {

			connection = DriverManager.getConnection(url, user, password);

		}
		return connection;
	}

}
