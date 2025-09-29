package com.crypto.repository;


import java.sql.*;
import java.util.*;
import com.crypto.model.*;
import com.crypto.model.Enum.WalletType;
import com.crypto.utils.DataBaseConnection;

public class WalletRepository implements IWalletRepository {

	private Connection connection;

	// Constructeur : récupère la connexion JDBC
	public WalletRepository() throws SQLException {
		this.connection = DataBaseConnection.getConnection();
	}

	
	
	

	

}
