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



  

  

 
   
}
