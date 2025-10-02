package com.crypto.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

	private static String url;
	private static String user;
	private static String password;

	private static Connection connection;

	static {
		try (InputStream input = DataBaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {

			Properties prop = new Properties();
			if (input == null) {
				throw new RuntimeException("Fichier config.properties introuvable");
			}
			prop.load(input);
			url = prop.getProperty("db.url");
			user = prop.getProperty("db.user");
			password = prop.getProperty("db.password");
			

		} catch (Exception e) {
			  e.printStackTrace();
		        throw new RuntimeException("Erreur lors du chargement de la configuration DB");
		}
		
	}

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
