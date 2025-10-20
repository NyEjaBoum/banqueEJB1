\c postgres;
DROP DATABASE IF EXISTS compte_courant_db;
CREATE DATABASE compte_courant_db;
\c compte_courant_db;

-- Tables existantes
CREATE TABLE compte_courant (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    date_maj DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE mouvement_courant (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL REFERENCES compte_courant(id),
    type_mouvement_id INT NOT NULL,
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_mouvement DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Nouvelles tables pour l'authentification
CREATE TABLE direction (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    niveau INT NOT NULL
);

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    direction_id INT NOT NULL REFERENCES direction(id),
    role INT NOT NULL,
    date_creation DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE action_role (
    id SERIAL PRIMARY KEY,
    nom_table VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    role INT NOT NULL
);

-- Insertion des données
INSERT INTO direction (libelle, niveau) VALUES
('Direction Générale Comptes', 10),
('Direction Comptes Courants', 8),
('Service Gestion Comptes', 5),
('Service Mouvements', 5),
('Équipe Saisie', 3),
('Consultants Comptes', 1);

INSERT INTO utilisateur (email, mot_de_passe, direction_id, role) VALUES
('admin.comptes@banque.com', 'test', 1, 4),
('manager.comptes@banque.com', 'test', 2, 3),
('employe.comptes@banque.com', 'test', 3, 2),
('consultant.comptes@banque.com', 'test', 6, 1);

INSERT INTO action_role (nom_table, action, role) VALUES
-- ADMIN (4)
('compte_courant', 'CREATE', 4),
('compte_courant', 'READ', 4),
('compte_courant', 'UPDATE', 4),
('compte_courant', 'DELETE', 4),
('mouvement_courant', 'CREATE', 4),
('mouvement_courant', 'READ', 4),
('mouvement_courant', 'UPDATE', 4),
('mouvement_courant', 'DELETE', 4),
-- MANAGER (3)
('compte_courant', 'CREATE', 3),
('compte_courant', 'READ', 3),
('compte_courant', 'UPDATE', 3),
('mouvement_courant', 'CREATE', 3),
('mouvement_courant', 'READ', 3),
('mouvement_courant', 'UPDATE', 3),
-- EMPLOYE (2)
('compte_courant', 'CREATE', 2),
('compte_courant', 'READ', 2),
('compte_courant', 'UPDATE', 2),
('mouvement_courant', 'CREATE', 2),
('mouvement_courant', 'READ', 2),
-- CONSULTANT (1)
('compte_courant', 'READ', 1),
('mouvement_courant', 'READ', 1);