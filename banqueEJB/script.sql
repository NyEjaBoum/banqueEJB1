-- =========================================
-- BASE BANCAIRE COMPLETE
-- =========================================
\c postgres;
DROP DATABASE IF EXISTS banque_db;
CREATE DATABASE banque_db;
\c banque_db;

-- =========================================
-- Table clients
-- =========================================
CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    telephone VARCHAR(20),
    numero_client INTEGER NOT NULL UNIQUE
);

-- =========================================
-- Table types de mouvement (ex: dépôt, retrait, virement)
-- =========================================
CREATE TABLE type_mouvement (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(20) NOT NULL
);

-- =========================================
-- Table types de prêt
-- =========================================
CREATE TABLE type_pret (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    nb_mois_remboursement INTEGER NOT NULL,
    interet NUMERIC(5,2) NOT NULL,
    montant NUMERIC(15,2) NOT NULL
);

-- =========================================
-- Paramètres globaux
-- =========================================
CREATE TABLE parametre (
    id SERIAL PRIMARY KEY,
    plafond_retrait_global NUMERIC(12,2) NOT NULL,
    taux_interet_depot NUMERIC(5,2) NOT NULL
);

-- =========================================
-- COMPTE COURANT
-- =========================================
CREATE TABLE compte_courant (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES client(id),
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    date_maj DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE mouvement_courant (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL REFERENCES compte_courant(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    type VARCHAR(20) NOT NULL CHECK (type IN ('ENTREE','SORTIE')),
    date_mouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- COMPTE DEPOT
-- =========================================
CREATE TABLE compte_depot (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES client(id),
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    taux_interet NUMERIC(5,2) NULL,
    plafond_retrait NUMERIC(12,2), -- NULL = utiliser plafond global
    date_dernier_interet DATE NOT NULL DEFAULT CURRENT_DATE
);

ALTER TABLE compte_depot 
ADD COLUMN actif BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE mouvement_depot (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL REFERENCES compte_depot(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    type VARCHAR(20) NOT NULL CHECK (type IN ('VERSEMENT','RETRAIT')),
    date_mouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- PRETS
-- =========================================
CREATE TABLE pret (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES client(id),
    type_pret_id INT NOT NULL REFERENCES type_pret(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    taux_interet NUMERIC(5,2) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    etat VARCHAR(20) NOT NULL CHECK (etat IN ('ENCOURS','REMBOURSE'))
);

CREATE TABLE remboursement (
    id SERIAL PRIMARY KEY,
    pret_id INT NOT NULL REFERENCES pret(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_remboursement DATE NOT NULL DEFAULT CURRENT_DATE
);

-- =========================================
-- Données initiales exemple
-- =========================================
INSERT INTO client (nom, prenom, email, telephone, numero_client) VALUES
('Randria', 'Jean', 'jean.randria@email.com', '0341234567', 1001),
('Rakoto', 'Marie', 'marie.rakoto@email.com', '0347654321', 1002);

INSERT INTO parametre (plafond_retrait_global, taux_interet_depot) VALUES
(1000000.00, 2.0);

INSERT INTO type_mouvement (libelle) VALUES
('DEPOT'), ('RETRAIT'), ('VIREMENT'), ('ENTREE'), ('SORTIE');

INSERT INTO type_pret (libelle, nb_mois_remboursement, interet, montant) VALUES
('Prêt Conso', 12, 5.0, 500000.00),
('Prêt Immo', 240, 3.5, 50000000.00);

-- Comptes courants et dépôts pour tests
INSERT INTO compte_courant (client_id, solde) VALUES (1, 10000.00), (2, 5000.00);
INSERT INTO compte_depot (client_id, solde, taux_interet) VALUES (1, 20000.00, 2.0), (2, 10000.00, 2.0);

-- Mouvements courants et dépôts pour tests
INSERT INTO mouvement_courant (compte_id, montant, type) VALUES
(1, 2000.00, 'ENTREE'), (1, 500.00, 'SORTIE'), (2, 1000.00, 'ENTREE');

INSERT INTO mouvement_depot (compte_id, montant, type) VALUES
(1, 5000.00, 'VERSEMENT'), (1, 1000.00, 'RETRAIT'), (2, 3000.00, 'VERSEMENT');

INSERT INTO client (nom, prenom, email, telephone, numero_client)
VALUES ('Boss', 'John', 'john.boss@example.com', '0341234567', 1005);

ALTER TABLE parametre 
ADD COLUMN taux_interet_pret NUMERIC(5,2) NOT NULL DEFAULT 24.0;
