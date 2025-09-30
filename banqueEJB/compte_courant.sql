\c postgres;
DROP DATABASE IF EXISTS compte_courant_db;
CREATE DATABASE compte_courant_db;
\c compte_courant_db;

CREATE TABLE compte_courant (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL, -- référence à central_db.client.id
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    date_maj DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE mouvement_courant (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL,
    type_mouvement_id INT NOT NULL, -- référence à central_db.type_mouvement.id
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_mouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
