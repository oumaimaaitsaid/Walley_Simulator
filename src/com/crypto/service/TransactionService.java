package com.crypto.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.crypto.model.Transaction;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.Status;
import com.crypto.repository.ITransactionRepository;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.WalletRepository;
import com.crypto.utils.IdGenerator;

public class TransactionService implements ITransactionsService {

	private final ITransactionRepository TransactionRepository;
	private final WalletRepository walletRepository;

	public TransactionService() throws SQLException {
		this.TransactionRepository = new TransactionRepository();
		this.walletRepository = new WalletRepository();
	}

	
	


}
