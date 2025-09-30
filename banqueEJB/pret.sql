\c postgres;
DROP DATABASE IF EXISTS pret_db;
CREATE DATABASE pret_db;
\c pret_db;

CREATE TABLE pret (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL, -- référence à central_db.client.id
    type_pret_id INT NOT NULL, -- référence à central_db.type_pret.id
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    taux_interet NUMERIC(5,2) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    etat VARCHAR(20) NOT NULL CHECK (etat IN ('ENCOURS','REMBOURSE'))
);

CREATE TABLE remboursement (
    id SERIAL PRIMARY KEY,
    pret_id INT NOT NULL, -- référence à pret.id
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_remboursement DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE parametre (
    id SERIAL PRIMARY KEY,
    taux_interet_pret NUMERIC(5,2) NOT NULL
);