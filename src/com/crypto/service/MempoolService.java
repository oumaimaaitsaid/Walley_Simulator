package com.crypto.service;

import java.util.*;
import java.util.stream.Collectors;

import com.crypto.model.Transaction;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.Status;
import com.crypto.utils.IdGenerator;

public class MempoolService {

	private final TransactionService transactionService;
	private final List<Transaction> pendingTrans;

	public MempoolService(TransactionService transactionService) {
		this.transactionService = transactionService;
		this.pendingTrans = new ArrayList<>();
	}

	/**
	 * génerer les transactions aleatoire pour simuler le pool
	 **/

	public void generateRandomTrans(int count) {
		for (int i = 0; i < count; i++) {
			Transaction tr = new Transaction();

			tr.setTxUuid(IdGenerator.generateUUID());
			tr.setWalletId(IdGenerator.generateUUID());
			tr.setDestinationAddress(generateEthereumAddress());
			tr.setAmount(Math.random() * 1000);
			tr.setFees(Math.random() * 100);
			tr.setPriority(Priority.values()[new Random().nextInt(Priority.values().length)]);
			tr.setStatus(Status.PENDING);
			tr.setCreatedAt(java.time.LocalDateTime.now());

			pendingTrans.add(tr);

		}
	}

	private String generateEthereumAddress() {
		String base = UUID.randomUUID().toString().replace("-", "");

		while (base.length() < 40) {
			base += "0";
		}
		return "0x" + base.substring(0, 40);
	}

	/**
	 * retourne toutes les transactions en attente
	 */

	public List<Transaction> getPendingTrans() {
		return pendingTrans.stream().filter(tr -> tr.getStatus() == Status.PENDING).collect(Collectors.toList());
	}

	/**
	 * ajouter une nouvelle transaction dans le mempool
	 */

	public void addTransaction(Transaction tr) {
		pendingTrans.add(tr);
	}

	/**
	 * Supprimer un e transaction du mempool
	 */

	public void removeTransactio(UUID txUuid) {
		pendingTrans.removeIf(tr -> tr.getTxUuid().equals(txUuid));
	}

	

	

	
}
