package com.crypto.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.crypto.model.Wallet;

public interface IWalletRepository {

    /**
     * Sauvegarde un wallet en base de données.
     * @param wallet l'objet Wallet à sauvegarder
     * @return le wallet sauvegardé avec son ID mis à jour si auto-généré
     */
    Wallet save(Wallet wallet);

    /**
     * Recherche un wallet par son UUID.
     * @param uuid identifiant unique du wallet
     * @return un Optional contenant le wallet si trouvé, sinon Optional.empty()
     */
    Optional<Wallet> findByUuid(UUID uuid);

    /**
     * Recherche un wallet par son adresse crypto.
     * @param address adresse publique du wallet
     * @return un Optional contenant le wallet si trouvé, sinon Optional.empty()
     */
    Optional<Wallet> findByAddress(String address);

    /**
     * Récupère tous les wallets existants.
     * @return une liste de tous les wallets
     */
    List<Wallet> findAll();

    /**
     * Met à jour le solde d’un wallet.
     * @param uuid identifiant unique du wallet
     * @param newBalance nouveau solde à enregistrer
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean updateBalance(UUID uuid, double newBalance);

   
}
