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
    montant NUMERIC(15,2) NOT NULL,      -- montant réel emprunté par le client
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,              -- calculée via nb_mois_remboursement du type de prêt
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

-- Insérer des types de prêt (en Ariary, taux élevés)
INSERT INTO type_pret (libelle, nb_mois_remboursement, interet, montant) VALUES
('Prêt Etudiant', 36, 24.00, 5000000),     -- 5 000 000 Ar max sur 3 ans
('Prêt Immobilier', 240, 18.00, 200000000), -- 200 000 000 Ar max sur 20 ans
('Prêt Court Terme', 12, 30.00, 2000000),  -- 2 000 000 Ar max sur 1 an
('Prêt Long Terme', 60, 20.00, 30000000);  -- 30 000 000 Ar max sur 5 ans


-- -- Exemple d’insertion d’un prêt pour un client
-- INSERT INTO pret (client_id, type_pret_id, taux_interet, montant, date_debut, date_fin, etat)
-- VALUES (
--     101,  -- client_id
--     1,    -- type_pret_id : Pret Etudiant
--     (SELECT interet FROM type_pret WHERE id = 1),
--     8000.00,  -- montant choisi par le client <= plafond du type
--     '2025-10-01',  -- date de début du prêt
--     '2025-10-01'::date + (SELECT nb_mois_remboursement FROM type_pret WHERE id = 1) * INTERVAL '1 month',  -- date de fin calculée
--     'ENCOURS'
-- );
