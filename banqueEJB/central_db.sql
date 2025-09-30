\c postgres;
DROP DATABASE IF EXISTS central_db;
CREATE DATABASE central_db;
\c central_db;

-- Table clients
CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    telephone VARCHAR(20),
    numero_client INTEGER NOT NULL UNIQUE
);

-- Type de mouvement global
CREATE TABLE type_mouvement (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(20) NOT NULL
);

-- Valeurs initiales pour type_mouvement
INSERT INTO type_mouvement (libelle) VALUES
('ENTREE'),   -- Utilisé pour les dépôts ou crédits sur un compte courant
('SORTIE'),   -- Utilisé pour les retraits ou débits sur un compte courant
('VIREMENT'), -- Utilisé pour les transferts d'argent entre comptes
('VERSEMENT'),-- Utilisé pour les dépôts sur un compte dépôt
('RETRAIT');  -- Utilisé pour les retraits depuis un compte dépôt

INSERT INTO client (nom, prenom, email, telephone, numero_client) VALUES
('Randria', 'Jean', 'jean.randria@email.com', '0341234567', 1001),
('Rakoto', 'Marie', 'marie.rakoto@email.com', '0347654321', 1002);


