@echo off
echo Nettoyage des modules...

REM Nettoyer pret
cd pret
call mvn clean
cd ..

REM Nettoyer comptecourant  
cd comptecourant
call mvn clean
cd ..

REM Nettoyer centralisateur
cd centralisateur
call mvn clean
cd ..

echo Compilation dans l'ordre...

REM Compiler pret en premier
cd pret
call mvn clean install
cd ..

REM Compiler comptecourant
cd comptecourant
call mvn clean install
cd ..

REM Compiler centralisateur en dernier
cd centralisateur
call mvn clean install
cd ..

echo Terminé !
pause