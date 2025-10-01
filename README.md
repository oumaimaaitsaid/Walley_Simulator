# 💰 Wallet Crypto – Java 8 (Console) – PostgreSQL

## 📌 Contexte
Application console en **Java 8** qui simule un **wallet crypto** avec gestion du **mempool** et calcul des **frais (fees)**.  
L’objectif est d’illustrer les concepts de **transactions, priorités (ÉCONOMIQUE / STANDARD / RAPIDE)**, et leur **positionnement dans le mempool**, tout en persistant les données dans **PostgreSQL**.

---

## ✨ Fonctionnalités

- 🔐 **Création de wallet** (BITCOIN ou ETHEREUM) avec adresse générée automatiquement et persistance en base.  
- 💸 **Création de transaction** avec calcul des frais selon le type de crypto et la priorité (**PENDING**).  
- ⏳ **Simulation du mempool** : transactions aléatoires + calcul de la position par ordre décroissant des fees + estimation du temps (`position × 10 min`).  
- 📊 **Comparaison des frais** : tableau console (fees, position, temps estimé) pour les 3 niveaux de priorité.  
- 🗄️ **Consultation DB** : lister les wallets et lister les transactions par wallet.  

---

## 🏗️ Architecture & principes

- **Java 8 (sans Maven)**
- **Couches** :  
  - Présentation (console)  
  - Services  
  - Repositories (in-memory + JDBC service)  
  - Domaine  
  - Utilitaires  
  - Base de données (Singleton)  

- **Principes SOLID & Patterns :**
  - `Singleton` : Database  
  - `Repository Pattern` : interfaces génériques + implémentations in-memory  
  - Polymorphisme/Stratégie via héritage : `Transaction` abstraite, `BitcoinTransaction`, `EthereumTransaction`  
  - Interfaces clés : `Repository<T>`, `Identifiable`  

---

## 📋 Prérequis

- ☕ **JDK 8**  
- 🐘 **PostgreSQL** (base `crypto_wallet`)  
- 📂 **Pilote JDBC PostgreSQL** → `lib/postgresql-<version>.jar`  

---

## 🗄️ Schéma de base de données

- 📄 Fichier : `Wallet_Crypto.sql`  
- **Tables** : `wallets`, `transactions`  
- **Index** : `wallet_id`, `status`, `priority`  


---

## ⚙️ Installation

1. **Cloner/copier le projet**
2. **Créer la base** et exécuter `Wallet_Crypto.sql` dans PostgreSQL (`psql` ou `pgAdmin`).  
3. **Placer le pilote JDBC** dans `lib/`  
   ```bash
   lib/postgresql-42.7.4.jar

## 🏗️ Compilation (Windows, sans Maven)
```bashcmd /c "if not exist out mkdir out && dir /s /b src\*.java > sources.txt && javac -source 1.8 -target 1.8 -encoding UTF-8 -cp lib\postgresql-42.7.4.jar -d out @sources.txt" ```bash

## ▶️ Exécution

PowerShell 

$env:JDBC_URL = "jdbc:postgresql://localhost:5432/crypto_wallet"
$env:JDBC_USER = "postgres"
$env:JDBC_PASSWORD = "password"   # ou "" si pas de mot de passe
$env:JDBC_DRIVER = "org.postgresql.Driver"

java -cp "out;lib\postgresql-42.7.4.jar" com.crypto.app.Main



## 🖥️ Utilisation (Menu console)

1.Créer un wallet
2.Créer une nouvelle transaction
3.Voir les transactions d'un wallet
4.Comparer les niveaux de frais
5.Consulter l'état du mempool
6.Consulter Votre Position en meempol
7.Modifier Votre Balance
0.Quitter


## 📸 Aperçu

<img width="397" height="248" alt="image" src="https://github.com/user-attachments/assets/da00c583-4cef-4588-9c59-ec4a6d4c82cd" />

## Diagramme de Classe
<img width="1022" height="586" alt="image" src="https://github.com/user-attachments/assets/b4c54055-699a-42c0-a2aa-3b84ac18597e" />

## 📝 Notes

Le mempool est simulé en mémoire (non persisté).
👉 Extension possible : somme des transactions CONFIRMED.
Code Java 8 pur, sans Maven, avec séparation claire des responsabilités.

## 🛠️ Dépannage

❌ ClassNotFoundException: org.postgresql.Driver
→ Vérifier que lib/postgresql-<version>.jar est bien présent et que le classpath est cité avec des guillemets.

❌ Aucune donnée visible dans la DB
→ Vérifier vos variables JDBC et la base crypto_wallet.





