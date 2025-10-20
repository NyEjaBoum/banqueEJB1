-- =========================================
-- SCRIPT DE REINITIALISATION DES DONNEES
-- Efface et remet les données initiales dans les 4 bases
-- =========================================

-- =========================================
-- CENTRAL_DB - Nettoyage et réinitialisation
-- =========================================
\c central_db;
\echo 'Réinitialisation de CENTRAL_DB...'

-- Nettoyage des données (garde la structure)
DELETE FROM client WHERE id > 0;
DELETE FROM type_mouvement WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE client_id_seq RESTART WITH 1;
ALTER SEQUENCE type_mouvement_id_seq RESTART WITH 1;

-- Réinsertion des types de mouvement
INSERT INTO type_mouvement (id, libelle, description) VALUES
(1, 'DEPOT', 'Dépôt d''argent sur un compte'),
(2, 'RETRAIT', 'Retrait d''argent d''un compte'),
(3, 'VIREMENT_SORTANT', 'Envoi vers un autre compte'),
(4, 'VIREMENT_ENTRANT', 'Réception depuis un autre compte'),
(5, 'INTERET', 'Application d''intérêts sur dépôt'),
(6, 'FRAIS', 'Prélèvement de frais bancaires');

-- Réinsertion des clients initiaux
INSERT INTO client (nom, prenom, email, telephone) VALUES
('Randria', 'Jean', 'jean.randria@email.com', '0341234567'),
('Rakoto', 'Marie', 'marie.rakoto@email.com', '0347654321');

\echo 'CENTRAL_DB réinitialisé ✅'

-- =========================================
-- COMPTE_COURANT_DB - Nettoyage et réinitialisation
-- =========================================
\c compte_courant_db;
\echo 'Réinitialisation de COMPTE_COURANT_DB...'

-- Nettoyage des données (ordre important : mouvements puis comptes)
DELETE FROM mouvement_courant WHERE id > 0;
DELETE FROM compte_courant WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE mouvement_courant_id_seq RESTART WITH 1;
ALTER SEQUENCE compte_courant_id_seq RESTART WITH 1;

-- Pas de données initiales dans ce fichier - base vide

\echo 'COMPTE_COURANT_DB réinitialisé ✅'

-- =========================================
-- COMPTE_DEPOT_DB - Nettoyage et réinitialisation
-- =========================================
\c compte_depot_db;
\echo 'Réinitialisation de COMPTE_DEPOT_DB...'

-- Nettoyage des données (ordre important)
DELETE FROM mouvement_depot WHERE id > 0;
DELETE FROM compte_depot WHERE id > 0;
DELETE FROM parametre WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE mouvement_depot_id_seq RESTART WITH 1;
ALTER SEQUENCE compte_depot_id_seq RESTART WITH 1;
ALTER SEQUENCE parametre_id_seq RESTART WITH 1;

-- Réinsertion des paramètres globaux initiaux
INSERT INTO parametre (plafond_retrait_global, taux_interet_depot) VALUES
(1000000.00, 2.0);

\echo 'COMPTE_DEPOT_DB réinitialisé ✅'

-- =========================================
-- PRET_DB - Nettoyage et réinitialisation
-- =========================================
\c pret_db;
\echo 'Réinitialisation de PRET_DB...'

-- Nettoyage des données (ordre important)
DELETE FROM remboursement WHERE id > 0;
DELETE FROM pret WHERE id > 0;
DELETE FROM type_pret WHERE id > 0;

-- Réinitialisation des séquences
ALTER SEQUENCE remboursement_id_seq RESTART WITH 1;
ALTER SEQUENCE pret_id_seq RESTART WITH 1;
ALTER SEQUENCE type_pret_id_seq RESTART WITH 1;

-- Réinsertion des types de prêt initiaux
INSERT INTO type_pret (libelle, nb_mois_remboursement, interet, montant) VALUES
('Prêt Etudiant', 36, 24.00, 5000000),     -- 5 000 000 Ar max sur 3 ans
('Prêt Immobilier', 240, 18.00, 200000000), -- 200 000 000 Ar max sur 20 ans
('Prêt Court Terme', 12, 30.00, 2000000),  -- 2 000 000 Ar max sur 1 an
('Prêt Long Terme', 60, 20.00, 30000000);  -- 30 000 000 Ar max sur 5 ans

\echo 'PRET_DB réinitialisé ✅'

-- =========================================
-- VERIFICATION FINALE
-- =========================================
\echo '========================================='
\echo 'VERIFICATION DES DONNEES REINITIALISEES'
\echo '========================================='

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

\echo '========================================='
\echo 'REINITIALISATION TERMINEE AVEC SUCCES !'
\echo '========================================='
\echo 'Toutes les bases sont revenus à leur état initial'
\echo 'Vous pouvez maintenant créer de nouveaux comptes/clients'