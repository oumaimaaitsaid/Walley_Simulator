package com.crypto.présentation;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.crypto.model.Transaction;
import com.crypto.model.Wallet;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.WalletType;
import com.crypto.service.MempoolService;
import com.crypto.service.TransactionService;
import com.crypto.service.WalletService;

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		WalletService walletService = null;
		TransactionService transactionService = null;
		MempoolService mempoolService;
		UUID lastUserTxUuid = null;

		try {
			walletService = new WalletService();
			transactionService = new TransactionService();
			mempoolService = new MempoolService(transactionService);

			logger.info("initialisation avec succée");
		} catch (SQLException e) {
			logger.severe(" erreur de connexion à la base de données : " + e.getMessage());
			return;
		}

		boolean running = true;

		while (running) {
			logger.info("\n=== CRYPTO WALLET SIMULATOR ===");
			System.out.println("1. Créer un wallet");
			System.out.println("2. Créer une transaction");
			System.out.println("3. Voir les transactions d'un wallet");
			System.out.println("4. Comparer les niveaux de frais");
			System.out.println("5. Consulter l'état du mempool");
			System.out.println("6. Consulter Votre Position en meempol");
			System.out.println("7. Modifier Votre Balance");
			System.out.println("0. Quitter");
			System.out.print("Choix : ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {

			case 1:
				System.out.print("Type de wallet (1:BITCOIN/2:ETHEREUM) : ");
				int tp = scanner.nextInt();
				scanner.nextLine();

				if (tp != 1 && tp != 2) {
					logger.log(Level.WARNING,"Saisie invalide pour le type de wallet {0} : " , tp);
					
					System.out.println("Choix invalide ! Merci d’entrer 1 pour BITCOIN ou 2 pour ETHEREUM.");
					break;
				}

				WalletType type = (tp == 1) ? WalletType.BITCOIN : WalletType.ETHEREUM;
				Wallet wallet = walletService.createWallet(type);
				logger.info("Wallet créé : " + wallet.getWalletUuid());
				System.out.println("Adresse: " + wallet.getAddress() + " | UUID: " + wallet.getWalletUuid());
				break;

			case 2:
				System.out.print("UUID du wallet source : ");
				UUID walletId = UUID.fromString(scanner.nextLine());
				System.out.print("Adresse destination : ");
				String dest = scanner.nextLine();
				System.out.print("Montant : ");
				double amount = scanner.nextDouble();
				System.out.print("Frais : ");
				double fees = scanner.nextDouble();
				scanner.nextLine();

				System.out.print("Priorité (1:ECONOMIQUE/2:STANDARD/3:RAPIDE) : ");
				int p = scanner.nextInt();
				scanner.nextLine();

				Priority priority = (p == 1) ? Priority.ECONOMIQUE : (p == 2) ? Priority.STANDARD : Priority.RAPIDE;
				try {
					Transaction tx = transactionService.createTransaction(walletId, dest, amount, fees, priority);
					mempoolService.addTransaction(tx);

					lastUserTxUuid = tx.getTxUuid();
					logger.info("Transaction créée et ajoutée au mempool : " + tx.getTxUuid());
				} catch (IllegalArgumentException e) {
					logger.warning("echec de création la transaction" + e.getMessage());

				}

				break;

			case 3:
				System.out.print("UUID du wallet : ");
				UUID wId = UUID.fromString(scanner.nextLine());
				List<Transaction> txList = transactionService.getTransactionsByWallet(wId);
				logger.log(Level.INFO,"Consultation des transactions du wallet {0} : " , wId);
				for (Transaction t : txList) {
					System.out.println(t.getTxUuid() + " | Montant: " + t.getAmount() + " | Status: " + t.getStatus());
				}

				break;

			case 4:
				System.out.print("Montant à comparer : ");
				double amt = scanner.nextDouble();
				scanner.nextLine();
				List<String> results = mempoolService.compareFeeLevels(amt);
				logger.log(Level.INFO ,"Comparaison des frais effectuée pour montant = {0} " ,amt);
				System.out.printf("%-12s | %-10s | %-10s | %-10s%n", "Priorité", "Frais", "Position", "Temps estimé");
				System.out.println("-----------------------------------------------");
				for (String line : results) {
					System.out.println(line);
				}
				System.out.println("-----------------------------------------------\n");
				break;

			case 5:
				
				mempoolService.generateRandomTrans(15);
				mempoolService.displayMempoolState(lastUserTxUuid).forEach(System.out::println);
				logger.info("Mempool affiché avec transactions aléatoires");

				break;
			case 6:
				if (lastUserTxUuid == null) {
					logger.warning("Demande de position sans transaction utilisateur");
				} else {
					int position = mempoolService.getTransactionPosition(lastUserTxUuid);
					int total = mempoolService.getPendingTrans().size();
					int time = mempoolService.estimateConfirmationTime(lastUserTxUuid);

					if (position == -1) {
						logger.warning("Transaction utilisateur non trouvée dans mempool");
					} else {

						System.out.println("\n=== POSITION DANS LE MEMPOOL ===");
						System.out.println("Votre transaction est en position " + position + " sur " + total);
						System.out.println("Temps estimé avant confirmation : " + time + " minutes");
					}
				}
				break;
			case 7:
				System.out.print("UUID du wallet : ");
				UUID ID = UUID.fromString(scanner.nextLine());
				System.out.print("Entrer le nouveau balance : ");
				double newBalance = scanner.nextDouble();
				scanner.nextLine();
				walletService.updateBalance(ID, newBalance);

				break;

			case 0:
				running = false;
				logger.info("Arrêt de l'application demandé par l'utilisateur");
				System.out.println("Au revoir !");
				break;


			default:
				logger.log( Level.WARNING,"Choix invalide saisi : {0} " + choice);
				System.out.println("Choix invalide !");
			}
		}

		scanner.close();
	}
}
