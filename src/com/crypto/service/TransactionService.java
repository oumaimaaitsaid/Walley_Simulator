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

	@Override
	public Transaction createTransaction(UUID waletId, String destinationAddress, double amount, double fees,
			Priority priority) {
		if (amount <= 0) {
			throw new IllegalArgumentException("le montant doit ètre positif");
		}
		if (destinationAddress == null || destinationAddress.isEmpty()) {
			throw new IllegalArgumentException("Adress de detination invalide.");
		}
		wallet sourceWallet = walletRepository.findByUuid(waletId)
				.orElseThrow(() -> new IllegalArgumentException("Wallet source introuvable !"));

		Transaction tr = new Transaction();
		tr.setTxUuid(IdGenerator.generateUUID());
		tr.setWalletId(waletId);
		tr.setSourceAddress(sourceWallet.getAddress());
		tr.setDestinationAddress(destinationAddress);
		tr.setAmount(amount);
		tr.setFees(fees);
		tr.setPriority(priority);
		tr.setStatus(Status.PENDING);
		tr.setCreatedAt(java.time.LocalDateTime.now());

		return TransactionRepository.save(tr);
	}

	
	


}
