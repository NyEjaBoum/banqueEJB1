\c postgres;
DROP DATABASE IF EXISTS compte_courant_db;
CREATE DATABASE compte_courant_db;
\c compte_courant_db;

-- Table des comptes courants
CREATE TABLE compte_courant (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    solde NUMERIC(12,2) NOT NULL DEFAULT 0,
    date_maj DATE NOT NULL DEFAULT CURRENT_DATE
);

-- CREATE TABLE historique_mouvement_courant (
--     id SERIAL PRIMARY KEY,
--     compte_id INT NOT NULL,
--     type_mouvement_id INT NOT NULL,
--     montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
--     date_mouvement DATE NOT NULL DEFAULT CURRENT_DATE,
--     statut INT NOT NULL CHECK(statut IN (0, 1, 2)) DEFAULT 0, -- 0: EN_ATTENTE, 1: VALIDE, 2: REFUSE
--     utilisateur_id INT NOT NULL REFERENCES utilisateur(id)
-- );

-- Table des mouvements avec statut de validation
CREATE TABLE mouvement_courant (
    id SERIAL PRIMARY KEY,
    compte_id INT NOT NULL REFERENCES compte_courant(id),
    type_mouvement_id INT NOT NULL,
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_mouvement DATE NOT NULL DEFAULT CURRENT_DATE,
    statut INT NOT NULL CHECK(statut IN (0, 1, 2)) DEFAULT 0 -- 0: EN_ATTENTE, 1: VALIDE, 2: REFUSE
);

-- Table des directions métiers
CREATE TABLE direction (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    niveau INT NOT NULL
);

-- Table des utilisateurs
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    direction_id INT NOT NULL REFERENCES direction(id),
    role INT NOT NULL,
    date_creation DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Table des actions autorisées par rôle
CREATE TABLE action_role (
    id SERIAL PRIMARY KEY,
    nom_table VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    role INT NOT NULL
);

-- Directions
INSERT INTO direction (libelle, niveau) VALUES
('Direction Générale Comptes', 10),
('Direction Comptes Courants', 8),
('Direction Dépôts', 8),
('Service Gestion Comptes', 5),
('Service Mouvements', 5),
('Équipe Saisie', 3),
('Consultants Comptes', 1);

-- Utilisateurs de test
INSERT INTO utilisateur (email, mot_de_passe, direction_id, role) VALUES
('admin.comptes@banque.com', 'test', 1, 4),      -- ADMIN
('manager.comptes@banque.com', 'test', 2, 3),    -- MANAGER
('employe.comptes@banque.com', 'test', 4, 2),    -- EMPLOYE
('consultant.comptes@banque.com', 'test', 7, 1); -- CONSULTANT

-- Permissions par rôle
INSERT INTO action_role (nom_table, action, role) VALUES
-- ADMIN (4) : tous droits + validation
('compte_courant', 'CREATE', 4),
('compte_courant', 'READ', 4),
('compte_courant', 'UPDATE', 4),
('compte_courant', 'DELETE', 4),
('compte_courant', 'VALIDATE', 4),
('mouvement_courant', 'CREATE', 4),
('mouvement_courant', 'READ', 4),
('mouvement_courant', 'UPDATE', 4),
('mouvement_courant', 'DELETE', 4),
('mouvement_courant', 'VALIDATE', 4),

-- MANAGER (3) : gestion complète sauf suppression et validation
('compte_courant', 'CREATE', 3),
('compte_courant', 'READ', 3),
('compte_courant', 'UPDATE', 3),
('mouvement_courant', 'CREATE', 3),
('mouvement_courant', 'READ', 3),
('mouvement_courant', 'UPDATE', 3),

-- EMPLOYE (2) : création et modification, pas de mouvement
('compte_courant', 'CREATE', 2),
('compte_courant', 'READ', 2),
('compte_courant', 'UPDATE', 2),

-- CONSULTANT (1) : lecture seule
('compte_courant', 'READ', 1),
('mouvement_courant', 'READ', 1);

-- Règle métier :
-- - Seuls les rôles > 2 peuvent faire des mouvements (CREATE sur mouvement_courant)
-- - Seul le rôle 4 (ADMIN) peut valider (action VALIDATE)
-- - Chaque mouvement doit être validé (statut EN_ATTENTE,