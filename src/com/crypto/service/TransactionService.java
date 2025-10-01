package com.crypto.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.crypto.model.Transaction;
import com.crypto.model.Wallet;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.Status;
import com.crypto.repository.ITransactionRepository;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.WalletRepository;
import com.crypto.utils.IdGenerator;

import java.util.logging.Logger;

public class TransactionService implements ITransactionsService {

	private final ITransactionRepository TransactionRepository;
	private final WalletRepository walletRepository;
	private static final Logger logger = Logger.getLogger(TransactionService.class.getName());

	public TransactionService() throws SQLException {
		this.TransactionRepository = new TransactionRepository();
		this.walletRepository = new WalletRepository();
	}

	@Override
	public Transaction createTransaction(UUID waletId, String destinationAddress, double amount, double fees,
			Priority priority) {
		Wallet sourceWallet = walletRepository.findByUuid(waletId)
				.orElseThrow(() -> new IllegalArgumentException("Wallet source introuvable !"));
		if (amount <= 0) {
			logger.warning("le montant doit être positif");
			throw new IllegalArgumentException("Le montant doit être positif !");
		}
		if (destinationAddress == null || destinationAddress.isEmpty()) {
			logger.warning("Adress de detination invalide.");
			throw new IllegalArgumentException("Adresse de destination invalide !");
		}
		
		double total =amount+fees;
		if(sourceWallet.getBalance() < total) {
			logger.warning("Solde insuffisant. " + sourceWallet.getBalance() +"total" +total );
			throw new IllegalArgumentException("Solde insuffisant pour cette transaction!");
			
		}
		
		 double newBalance = sourceWallet.getBalance() - total;
		    sourceWallet.setBalance(newBalance);
		    walletRepository.updateBalance(sourceWallet.getWalletUuid(), newBalance);
		    logger.info("Balance du wallet mise à jour : " + newBalance);

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

	@Override
	public Optional<Transaction> getTransactionById(UUID txUuid) {
		return TransactionRepository.findById(txUuid);
	}

	@Override
	public List<Transaction> getTransactionsByWallet(UUID walletId) {
		return TransactionRepository.findByWallet(walletId);
	}

	@Override
	public List<Transaction> getAllPendingTransactions() {
		return TransactionRepository.findAllPending();
	}

	@Override
	public boolean updateTransactionStatus(UUID uuid, Status newStatus) {
		return TransactionRepository.updateStatus(uuid, newStatus);
	}

	@Override
	public boolean deleteTransaction(UUID txUuid) {
		return TransactionRepository.delete(txUuid);
	}

}
