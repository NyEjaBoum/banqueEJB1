-- =========================================
-- SCRIPT DE REINITIALISATION DES DONNEES
-- Sans suppression des bases de données
-- =========================================

-- =========================================
-- CENTRAL_DB - Nettoyage et réinitialisation
-- =========================================
\c central_db;

-- Nettoyage des données (garde la structure)
DELETE FROM client WHERE id > 0;
DELETE FROM type_mouvement WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE client_id_seq RESTART WITH 1;
ALTER SEQUENCE type_mouvement_id_seq RESTART WITH 1;

-- Réinsertion des types de mouvement globaux
INSERT INTO type_mouvement (id, libelle, description) VALUES
(1, 'DEPOT', 'Dépôt d''argent sur un compte'),
(2, 'RETRAIT', 'Retrait d''argent d''un compte'),
(3, 'VIREMENT_SORTANT', 'Envoi vers un autre compte'),
(4, 'VIREMENT_ENTRANT', 'Réception depuis un autre compte'),
(5, 'INTERET', 'Application d''intérêts sur dépôt'),
(6, 'FRAIS', 'Prélèvement de frais bancaires');

-- Réinsertion des clients de test
INSERT INTO client (nom, prenom, email, telephone) VALUES
('Randria', 'Jean', 'jean.randria@email.com', '0341234567'),
('Rakoto', 'Marie', 'marie.rakoto@email.com', '0347654321'),
('Andry', 'Paul', 'paul.andry@email.com', '0349876543'),
('Rabe', 'Sophie', 'sophie.rabe@email.com', '0348765432'),
('Rasolofo', 'Michel', 'michel.rasolofo@email.com', '0345678901');

-- =========================================
-- COMPTE_COURANT_DB - Nettoyage et réinitialisation
-- =========================================
\c compte_courant_db;

-- Nettoyage des données (ordre important : mouvements puis comptes)
DELETE FROM mouvement_courant WHERE id > 0;
DELETE FROM compte_courant WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE mouvement_courant_id_seq RESTART WITH 1;
ALTER SEQUENCE compte_courant_id_seq RESTART WITH 1;

-- Insertion de comptes de test
INSERT INTO compte_courant (client_id, solde, date_maj) VALUES
(1, 10000.00, CURRENT_DATE),
(2, 5000.00, CURRENT_DATE),
(3, 15000.00, CURRENT_DATE);

-- Insertion de mouvements de test
INSERT INTO mouvement_courant (compte_id, type_mouvement_id, montant, date_mouvement) VALUES
(1, 1, 2000.00, CURRENT_TIMESTAMP - INTERVAL '5 days'),  -- DEPOT
(1, 2, 500.00, CURRENT_TIMESTAMP - INTERVAL '3 days'),   -- RETRAIT
(2, 1, 3000.00, CURRENT_TIMESTAMP - INTERVAL '2 days'),  -- DEPOT
(3, 1, 5000.00, CURRENT_TIMESTAMP - INTERVAL '1 day');   -- DEPOT

-- =========================================
-- COMPTE_DEPOT_DB - Nettoyage et réinitialisation
-- =========================================
\c compte_depot_db;

-- Nettoyage des données (ordre important)
DELETE FROM mouvement_depot WHERE id > 0;
DELETE FROM compte_depot WHERE id > 0;
DELETE FROM parametre WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE mouvement_depot_id_seq RESTART WITH 1;
ALTER SEQUENCE compte_depot_id_seq RESTART WITH 1;
ALTER SEQUENCE parametre_id_seq RESTART WITH 1;

-- Réinsertion des paramètres globaux
INSERT INTO parametre (plafond_retrait_global, taux_interet_depot) VALUES
(1000000.00, 2.5);

-- Insertion de comptes dépôt de test
INSERT INTO compte_depot (client_id, solde, taux_interet, plafond_retrait, date_dernier_interet, actif) VALUES
(1, 50000.00, 2.5, NULL, CURRENT_DATE, true),
(2, 25000.00, 3.0, 500000.00, CURRENT_DATE, true),
(3, 0.00, NULL, NULL, CURRENT_DATE, false);

-- Insertion de mouvements dépôt de test
INSERT INTO mouvement_depot (compte_id, type_mouvement_id, montant, date_mouvement) VALUES
(1, 1, 50000.00, CURRENT_TIMESTAMP - INTERVAL '10 days'), -- DEPOT
(1, 2, 5000.00, CURRENT_TIMESTAMP - INTERVAL '5 days'),   -- RETRAIT
(2, 1, 25000.00, CURRENT_TIMESTAMP - INTERVAL '7 days'),  -- DEPOT
(2, 1, 10000.00, CURRENT_TIMESTAMP - INTERVAL '2 days');  -- DEPOT

-- =========================================
-- PRET_DB - Nettoyage et réinitialisation
-- =========================================
\c pret_db;

-- Nettoyage des données (ordre important)
DELETE FROM remboursement WHERE id > 0;
DELETE FROM pret WHERE id > 0;
DELETE FROM type_pret WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE remboursement_id_seq RESTART WITH 1;
ALTER SEQUENCE pret_id_seq RESTART WITH 1;
ALTER SEQUENCE type_pret_id_seq RESTART WITH 1;

-- Réinsertion des types de prêt
INSERT INTO type_pret (libelle, nb_mois_remboursement, interet, montant) VALUES
('Prêt Étudiant', 36, 24.00, 5000000.00),      -- 5M Ar max sur 3 ans
('Prêt Immobilier', 240, 12.00, 500000000.00), -- 500M Ar max sur 20 ans
('Prêt Auto', 60, 18.00, 50000000.00),         -- 50M Ar max sur 5 ans
('Prêt Personnel', 24, 30.00, 10000000.00),    -- 10M Ar max sur 2 ans
('Prêt Professionnel', 120, 15.00, 200000000.00); -- 200M Ar max sur 10 ans

-- Insertion de prêts de test
INSERT INTO pret (client_id, type_pret_id, taux_interet, montant, date_debut, date_fin, etat) VALUES
(1, 1, 24.00, 3000000.00, '2024-01-01', '2027-01-01', 'ENCOURS'),
(2, 3, 18.00, 25000000.00, '2024-06-01', '2029-06-01', 'ENCOURS'),
(3, 2, 12.00, 150000000.00, '2023-12-01', '2043-12-01', 'ENCOURS');

-- Insertion de remboursements de test
INSERT INTO remboursement (pret_id, montant, interet_payes, capital_rembourse, date_remboursement) VALUES
(1, 150000.00, 60000.00, 90000.00, '2024-02-01'),
(1, 150000.00, 58000.00, 92000.00, '2024-03-01'),
(2, 800000.00, 375000.00, 425000.00, '2024-07-01'),
(3, 2500000.00, 1500000.00, 1000000.00, '2024-01-01');

-- =========================================
-- VERIFICATION ET RESUME
-- =========================================

-- Affichage du résumé des données réinitialisées
\c central_db;
\echo '=== CENTRAL_DB ==='
SELECT 'Clients' as table_name, COUNT(*) as nb_records FROM client
UNION ALL
SELECT 'Types mouvement', COUNT(*) FROM type_mouvement;

\c compte_courant_db;
\echo '=== COMPTE_COURANT_DB ==='
SELECT 'Comptes courants' as table_name, COUNT(*) as nb_records FROM compte_courant
UNION ALL
SELECT 'Mouvements courants', COUNT(*) FROM mouvement_courant;

\c compte_depot_db;
\echo '=== COMPTE_DEPOT_DB ==='
SELECT 'Comptes dépôt' as table_name, COUNT(*) as nb_records FROM compte_depot
UNION ALL
SELECT 'Mouvements dépôt', COUNT(*) FROM mouvement_depot
UNION ALL
SELECT 'Paramètres', COUNT(*) FROM parametre;

\c pret_db;
\echo '=== PRET_DB ==='
SELECT 'Types prêt' as table_name, COUNT(*) as nb_records FROM type_pret
UNION ALL
SELECT 'Prêts', COUNT(*) FROM pret
UNION ALL
SELECT 'Remboursements', COUNT(*) FROM remboursement;

\echo '=== REINITIALISATION TERMINEE ==='
\echo 'Toutes les données ont été réinitialisées avec succès !';