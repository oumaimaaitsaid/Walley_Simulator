package com.crypto.repository;

import java.sql.*;
import java.util.*;
import com.crypto.model.Transaction;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.Status;
import com.crypto.utils.DataBaseConnection;

public class TransactionRepository implements ITransactionRepository {

	private Connection connection;

	public TransactionRepository() throws SQLException {

		this.connection = DataBaseConnection.getConnection();
	}

	/**
	 * Sauvegarder d'une transaction
	 */
	@Override

	public Transaction save(Transaction transaction) {

		String sql = "INSERT INTO transactions "
				+ "(tx_uuid,wallet_id,source_address,destination_address,amount,fees,priority,status,created_at) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, transaction.getTxUuid());
			stmt.setObject(2, transaction.getWalletId());
			stmt.setString(3, transaction.getSourceAddress());
			stmt.setString(4, transaction.getDestinationAddress());
			stmt.setDouble(5, transaction.getAmount());
			stmt.setDouble(6, transaction.getFees());
			stmt.setString(7, transaction.getPriority().name());
			stmt.setString(8, transaction.getStatus().name());
			stmt.setTimestamp(9, Timestamp.valueOf(transaction.getCreatedAt()));

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				return transaction;
			} else {
				throw new SQLException("la transaction non enregistré");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * Recherche par ID
	 */

	public Optional<Transaction> findById(UUID id) {

		String sql = "SELECT * FROM transactions WHERE tx_uuid =?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setObject(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Transaction transaction = mapToTransaction(rs);
				return Optional.of(transaction);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	};


	
	
	


}
