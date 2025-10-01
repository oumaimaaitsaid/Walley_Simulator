package test.java.com.crypto.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crypto.model.Enum.Priority;
import com.crypto.model.Transaction;
import com.crypto.model.Wallet;
import com.crypto.model.Enum.WalletType;
import com.crypto.service.TransactionService;
import com.crypto.service.WalletService;

public class TransactionServiceTest {

	private TransactionService transactionService;
	private WalletService walletService;
	private Wallet testWallet;

	@BeforeEach
	void setUp() throws SQLException {
		walletService = new WalletService();
		transactionService = new TransactionService();

		testWallet = walletService.createWallet(WalletType.BITCOIN);
	}

	@Test
	void testCreateTransactionPositiveAmount() {
		Transaction tx = transactionService.createTransaction(testWallet.getWalletUuid(), "BTC-DEST-1234", 50.0,
				Priority.STANDARD);

		assertNotNull(tx);
		assertEquals(50.0, tx.getAmount(), "Le montant de la transaction doit être 100");
		assertEquals(Priority.STANDARD, tx.getPriority());
	}

	@Test
	void testCreateTransactionNegativeAmount() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			transactionService.createTransaction(testWallet.getWalletUuid(), "BTC-DEST-1234", -50.0,
					Priority.ECONOMIQUE);
		});

		assertTrue(exception.getMessage().contains("montant doit être positif"));
	}
}
