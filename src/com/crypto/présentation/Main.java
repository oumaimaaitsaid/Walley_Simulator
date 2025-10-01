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

		
		

			

			default:
				logger.log( Level.WARNING,"Choix invalide saisi : {0} " + choice);
				System.out.println("Choix invalide !");
			}
		}

		scanner.close();
	}
}
