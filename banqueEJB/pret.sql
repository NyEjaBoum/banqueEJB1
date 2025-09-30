\c postgres;
DROP DATABASE IF EXISTS pret_db;
CREATE DATABASE pret_db;
\c pret_db;

-- Type de prêt
CREATE TABLE type_pret (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    nb_mois_remboursement INTEGER NOT NULL,
    interet NUMERIC(5,2) NOT NULL,  -- taux spécifique au type de prêt
    montant NUMERIC(15,2) NOT NULL   -- montant maximal pour ce type
);

-- Prêts des clients
CREATE TABLE pret (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,              -- référence à central_db.client.id
    type_pret_id INT NOT NULL REFERENCES type_pret(id),  -- référence au type de prêt
    taux_interet NUMERIC(5,2) NOT NULL,  -- peut être copié depuis type_pret.interet
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    etat VARCHAR(20) NOT NULL CHECK (etat IN ('ENCOURS','REMBOURSE'))
);

-- Remboursements
CREATE TABLE remboursement (
    id SERIAL PRIMARY KEY,
    pret_id INT NOT NULL REFERENCES pret(id),
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    interet_payes NUMERIC(12,2) DEFAULT 0,
    capital_rembourse NUMERIC(12,2) DEFAULT 0,
    date_remboursement DATE NOT NULL DEFAULT CURRENT_DATE
);


-- Insérer des types de prêt
INSERT INTO type_pret (libelle, nb_mois_remboursement, interet, montant) VALUES
('Pret Etudiant', 36, 3.00, 10000.00),
('Pret Immobilier', 240, 4.50, 500000.00),
('Pret Court Terme', 12, 5.00, 5000.00),
('Pret Long Terme', 60, 4.00, 50000.00);
