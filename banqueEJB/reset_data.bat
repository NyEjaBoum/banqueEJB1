@echo off
echo =========================================
echo REINITIALISATION DES DONNEES INITIALES
echo =========================================

set PGPASSWORD=mamaika77

echo Exécution du script de réinitialisation...
psql -U postgres -f reset_data.sql

echo.
echo =========================================
echo DONNEES REINITIALISEES !
echo =========================================
echo.
echo État après réinitialisation :
echo - CENTRAL_DB : 2 clients + 6 types de mouvements
echo - COMPTE_COURANT_DB : vide (prêt pour créer des comptes)
echo - COMPTE_DEPOT_DB : paramètres globaux seulement
echo - PRET_DB : 4 types de prêts disponibles
echo.
echo Utilisez acces_base.bat pour vérifier
pause