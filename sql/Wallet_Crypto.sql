CREATE DATABASE crypto_wallet;


CREATE TABLE wallets (
    id SERIAL PRIMARY KEY,
    wallet_uuid UUID NOT NULL UNIQUE,
    address VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('BITCOIN','ETHEREUM')),
    balance NUMERIC(18,8) NOT NULL DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    tx_uuid UUID NOT NULL UNIQUE,
    source_address VARCHAR(100) NOT NULL,
    destination_address VARCHAR(100) NOT NULL,
    amount NUMERIC(18,8) NOT NULL CHECK (amount > 0),
    fees NUMERIC(18,8) NOT NULL CHECK (fees >= 0),
    priority VARCHAR(20) NOT NULL CHECK (priority IN ('ECONOMIQUE','STANDARD','RAPIDE')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING','CONFIRMED','FAILED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    wallet_id UUID NOT NULL,
    
    CONSTRAINT fk_wallet FOREIGN KEY(wallet_id) REFERENCES wallets(wallet_uuid) ON DELETE CASCADE
);
-- Exemple de wallets
INSERT INTO wallets (wallet_uuid, address, type, balance)
VALUES
 (gen_random_uuid(), 'BTC-ABC12345', 'BITCOIN', 5000.0),
 (gen_random_uuid(), 'ETH-XYZ67890', 'ETHEREUM', 3000.0);

-- Exemple de transactions
INSERT INTO transactions (tx_uuid, source_address, destination_address, amount, fees, priority, status, wallet_id)
VALUES
 (gen_random_uuid(), 'BTC-ABC12345', 'BTC-DEST-111', 100.0, 1.0, 'STANDARD', 'PENDING', (SELECT wallet_uuid FROM wallets LIMIT 1));
