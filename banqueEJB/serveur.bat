@echo off
REM Chemin vers le dossier d'installation de WildFly
set WILDFLY_HOME=C:\wildfly-37.0.1.Final

REM Démarrer le serveur WildFly en mode standalone
cd %WILDFLY_HOME%\bin
call standalone.bat

REM Pause pour garder la fenêtre ouverte
pause