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
    telephone VARCHAR(20)
);

CREATE TABLE type_mouvement (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(30) NOT NULL,
    description VARCHAR(100)
);

INSERT INTO type_mouvement (id, libelle, description) VALUES
(1, 'DEPOT', 'Dépôt d''argent sur un compte'),
(2, 'RETRAIT', 'Retrait d''argent d''un compte'),
(3, 'VIREMENT_SORTANT', 'Envoi vers un autre compte'),
(4, 'VIREMENT_ENTRANT', 'Réception depuis un autre compte'),
(5, 'INTERET', 'Application d''intérêts sur dépôt'),
(6, 'FRAIS', 'Prélèvement de frais bancaires');

INSERT INTO client (nom, prenom, email, telephone) VALUES
('Randria', 'Jean', 'jean.randria@email.com', '0341234567'),
('Rakoto', 'Marie', 'marie.rakoto@email.com', '0347654321');


