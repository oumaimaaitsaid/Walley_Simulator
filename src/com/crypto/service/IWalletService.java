package com.crypto.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.crypto.model.Wallet;
import com.crypto.model.Enum.WalletType;

public interface IWalletService {

	/**
	 * Creation d'un wallet
	 */

	Wallet createWallet(WalletType type);

	/**
	 * récupèrer un wallet par son UUID
	 */

	Optional<Wallet> getWalletByUuid(UUID walletUuid);

	/**
	 * récupèrer les listes de wallet
	 */

	List<Wallet> getAllWallets();

	/**
	 * mis à jour le solde
	 */

	Boolean updateBalance(UUID walletUuid, double nowBalance);

	/**
	 * delete un wallet
	 */

	Boolean deleteWallet(UUID walletUuid);
}
