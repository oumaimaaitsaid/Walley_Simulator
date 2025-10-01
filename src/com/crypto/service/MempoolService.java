package com.crypto.service;

import java.util.*;
import java.util.stream.Collectors;

import com.crypto.model.Transaction;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.Status;
import com.crypto.utils.IdGenerator;

public class MempoolService {

	private final List<Transaction> pendingTrans;
	private static final Random RANDOM = new Random();

	public MempoolService(TransactionService transactionService) {

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
			tr.setPriority(Priority.values()[RANDOM.nextInt(Priority.values().length)]);
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

	/**
	 * calculer la position d un transaction dans la mempool
	 * 
	 * @param txUuid
	 * @return
	 */
	public int getTransactionPosition(UUID txUuid) {

		List<Transaction> sorted = pendingTrans.stream().filter(tr -> tr.getStatus() == Status.PENDING)
				.sorted((a, b) -> Double.compare(b.getFees(), a.getFees())).collect(Collectors.toList());

		for (int i = 0; i < sorted.size(); i++) {
			if (sorted.get(i).getTxUuid().equals(txUuid)) {
				return i + 1;
			}
		}

		return -1;

	}

	/**
	 * Estimer le temps de confirmation en minute
	 */

	public int estimateConfirmationTime(UUID txUuid) {

		int position = getTransactionPosition(txUuid);
		if (position == -1)
			return -1;
		return position * 10;
	}

	/**
	 * Comparer les 3 niveaux de frais pour une transaction
	 */

	public List<String> compareFeeLevels(double amount) {
		List<String> lines = new ArrayList<>();

		for (Priority priority : Priority.values()) {

			double fee = calculateFee(amount, priority);

			Transaction tmp = new Transaction();
			tmp.setTxUuid(IdGenerator.generateUUID());
			tmp.setAmount(amount);
			tmp.setFees(fee);
			tmp.setPriority(priority);
			tmp.setStatus(Status.PENDING);

			pendingTrans.add(tmp);

			int position = getTransactionPosition(tmp.getTxUuid());
			int estimatedTime = position * 10;

			String line = String.format("%-12s | %-10.2f |%-10d | %-10d min", priority, fee, position, estimatedTime);

			lines.add(line);

			pendingTrans.removeIf(tr -> tr.getTxUuid().equals(tmp.getTxUuid()));

		}

		return lines;
	}

	/**
	 * calculer les fees selon la priorité
	 */

	private double calculateFee(double amount, Priority priority) {

		switch (priority) {
		case ECONOMIQUE:
			return amount * 0.01;
		case STANDARD:
			return amount * 0.02;
		case RAPIDE:
			return amount * 0.03;
		default:
			return 0;
		}
	}

	/**
	 * Retourne l'état actuel du mempool sous forme de liste de chaînes de
	 * caractères pour que le main puisse l'afficher
	 */
	public List<String> displayMempoolState(UUID userTxUuid) {
		List<Transaction> sorted = pendingTrans.stream().filter(tr -> tr.getStatus() == Status.PENDING)
				.sorted(Comparator.comparingDouble(Transaction::getFees).reversed()).collect(Collectors.toList());

		List<String> lines = new ArrayList<>();
		lines.add("=== ETAT DU MEMPOOL ===");
		lines.add("Transactions en attentes :" + sorted.size());
		lines.add("┌────────────────────────────────────────────┬─────────┐");
		lines.add(String.format("│ %-40s │ %-7s │", "Transaction (autres utilisateurs)", "Frais"));
		lines.add("├────────────────────────────────────────────┼─────────┤");

		for (Transaction tr : sorted) {

			String label = tr.getTxUuid().equals(userTxUuid)
					? ">>>> Votre tx :" + tr.getDestinationAddress().substring(0, 10) + "...."
					: tr.getDestinationAddress().substring(0, 10) + "... (anonyme)";
			lines.add(String.format("│ %-40s │ %6.2f$ │", label, tr.getFees()));
		}

		lines.add("└────────────────────────────────────────────┴─────────┘");
		return lines;
	}

	public void clearMempool() {
		pendingTrans.clear();
	}

}
