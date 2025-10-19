@echo off
set PGPASSWORD=mamaika77
start cmd /k "psql -U postgres -d central_db"
start cmd /k "psql -U postgres -d pret_db"
start cmd /k "psql -U postgres -d compte_courant_db"
start cmd /k "psql -U postgres -d compte_depot_db"