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
	 * souvgardé un wallet en base de données
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

	@Override
	public Optional<Wallet> findByUuid(UUID uuid) {
		String sql = "SELECT * FROM wallets WHERE wallet_uuid = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, uuid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Wallet wallet = mapResultSetToWallet(rs);
				return Optional.of(wallet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	

	

}
