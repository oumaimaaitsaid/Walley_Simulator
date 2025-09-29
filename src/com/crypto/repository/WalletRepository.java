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

	/**
	 * souvegardé un wallet en base de données
	 */
	@Override
	public Wallet save(Wallet wallet) {
		String sql = "INSERT INTO wallets (wallet_uuid, type, address, balance, created_at) VALUES (?,?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, wallet.getWalletUuid());
			stmt.setString(2, wallet.getType().name());
			stmt.setString(3, wallet.getAddress());
			stmt.setDouble(4, wallet.getBalance());
			stmt.setTimestamp(5, Timestamp.valueOf(wallet.getCreatedAt()));

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				return wallet;
			} else {
				throw new SQLException("échec de enregistré du wallet");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	

	

}
