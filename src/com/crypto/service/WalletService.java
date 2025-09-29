package com.crypto.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.crypto.model.Wallet;
import com.crypto.model.Enum.WalletType;
import com.crypto.repository.IWalletRepository;
import com.crypto.repository.WalletRepository;
import com.crypto.utils.AddressGenerator;
import com.crypto.utils.IdGenerator;



public class WalletService  implements IWalletService {

	private final IWalletRepository walletRepository;

	public WalletService() throws SQLException {

		this.walletRepository = new WalletRepository();
	}

	@Override
	public Wallet createWallet(WalletType type) {

		Wallet wallet = new Wallet();
		wallet.setWalletUuid(IdGenerator.generateUUID());
		wallet.setType(type);
		wallet.setAddress(AddressGenerator.generateAddress(type));
		wallet.setBalance(0.0);
		wallet.setCreatedAt(java.time.LocalDateTime.now());

		return walletRepository.save(wallet);
	}

	@Override
	public Optional<Wallet> getWalletByUuid(UUID walletUuid) {

		return walletRepository.findByUuid(walletUuid);
	}

	@Override
	public List<Wallet> getAllWallets() {
		return walletRepository.findAll();
	}

	@Override
	public Boolean updateBalance(UUID walletUuid, double newBalance) {
		if (newBalance < 0) {
			System.err.println("votre solde est négatif");
			return false;
		}
		return walletRepository.updateBalance(walletUuid, newBalance);
	}

	@Override
	public Boolean deleteWallet(UUID walletUuid) {
		return walletRepository.delete(walletUuid);

	}

}
