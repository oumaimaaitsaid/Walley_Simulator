package Validator;




import java.util.logging.Logger;

import com.crypto.model.Transaction;

import java.util.logging.Level;

public class TransactionValidator {

    private static final Logger LOGGER = Logger.getLogger(TransactionValidator.class.getName());

    public static void validate(Transaction tx) {
        if (tx.getAmount() <= 0) {
            LOGGER.log(Level.WARNING, "Montant invalide : {0}", tx.getAmount());
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        if (tx.getDestinationAddress() == null || tx.getDestinationAddress().length() < 10) {
            LOGGER.log(Level.WARNING, "Adresse de destination invalide : {0}", tx.getDestinationAddress());
            throw new IllegalArgumentException("Adresse de destination invalide.");
        }

        if (tx.getPriority() == null) {
            LOGGER.log(Level.WARNING, "Niveau de priorité manquant.");
            throw new IllegalArgumentException("Le niveau de priorité est obligatoire.");
        }
    }
}
