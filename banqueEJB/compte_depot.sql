\c postgres;
DROP DATABASE IF EXISTS compte_depot_db;
CREATE DATABASE compte_depot_db;
\c compte_depot_db;

CREATE TABLE compte_depot (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL, -- référence à central_db.client.id
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    taux_interet NUMERIC(5,2) NULL,
    plafond_retrait NUMERIC(12,2),
    date_dernier_interet DATE NOT NULL DEFAULT CURRENT_DATE,
    actif BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE mouvement_depot (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL,
    type_mouvement_id INT NOT NULL, -- référence à central_db.type_mouvement.id
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_mouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
