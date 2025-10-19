# Guide Complet d'Installation et d'Utilisation - Application Bancaire EJB

## Vue d'ensemble du projet

Cette application bancaire utilise une architecture EJB distribuée avec plusieurs modules :
- centralisateur : Interface web principale (WAR)
- comptecourant : Module de gestion des comptes courants (JAR)  
- comptedepot : API .NET pour les comptes dépôt
- pret : Module de gestion des prêts (JAR)

L'application utilise 4 bases de données PostgreSQL distinctes pour séparer les données métier.

## Table des matières
1. Prérequis
2. Installation des logiciels
3. Configuration des bases de données
4. Configuration de WildFly
5. Compilation et déploiement
6. Utilisation de l'application
7. Dépannage

## Prérequis

### Logiciels nécessaires
- Java JDK 11+ 
- Apache Maven 3.6+
- PostgreSQL 12+ (avec mot de passe mamaika77)
- WildFly 37.0.1.Final
- .NET 9.0 SDK

### Vérification des installations
java -version
mvn -version
psql --version
dotnet --version

## Installation des logiciels

### 1. Java JDK
1. Téléchargez depuis Oracle (https://www.oracle.com/java/technologies/downloads/)
2. Installez et configurez JAVA_HOME
3. Ajoutez %JAVA_HOME%/bin au PATH

### 2. Apache Maven
1. Téléchargez depuis maven.apache.org (https://maven.apache.org/download.cgi)
2. Extrayez dans C:\apache-maven-3.x.x
3. Configurez M2_HOME et ajoutez %M2_HOME%/bin au PATH

### 3. PostgreSQL
1. Téléchargez depuis postgresql.org (https://www.postgresql.org/download/)
2. IMPORTANT : Utilisez le mot de passe mamaika77 (requis par l'application)
3. Port par défaut : 5432

### 4. WildFly
1. Téléchargez WildFly 37.0.1.Final
2. Extrayez dans C:\wildfly-37.0.1.Final\
3. Configurez WILDFLY_HOME

### 5. .NET SDK
Téléchargez et installez .NET 9.0 SDK depuis dotnet.microsoft.com

## Configuration des bases de données

### 1. Création des bases de données
Exécutez les scripts SQL dans l'ordre :

# Connectez-vous à PostgreSQL
psql -U postgres

# Exécutez les scripts de création (dans cet ordre)
\i C:\path\to\your\project\central_db.sql
\i C:\path\to\your\project\compte_courant.sql
\i C:\path\to\your\project\compte_depot.sql
\i C:\path\to\your\project\pret.sql

base efatra

### 3. Vérification
# Listez les bases créées
\l

# Vous devriez voir :
# - central_db       (clients, types de mouvements)
# - compte_courant_db (comptes courants, mouvements)
# - compte_depot_db   (comptes dépôt, paramètres)
# - pret_db          (prêts, remboursements)

## Configuration de WildFly

### 1. Installation du driver PostgreSQL
1. Téléchargez le driver JDBC PostgreSQL (postgresql-42.7.8.jar)
2. Créez le répertoire : C:\wildfly-37.0.1.Final\modules\system\layers\base\org\postgresql\main\
3. Copiez le JAR dans ce répertoire
4. Créez le fichier module.xml :

<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="org.postgresql">
    <resources>
        <resource-root path="postgresql-xx.x.x.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
    </dependencies>
</module>

3. Copiez le JAR dans ce répertoire deployements de wildfly

### 2. Configuration des DataSources
Éditez le fichier C:\wildfly-37.0.1.Final\standalone\configuration\standalone.xml :

<!-- Cherchez la section <drivers> dans <subsystem xmlns="urn:jboss:domain:datasources:7.0"> -->
<drivers>
    <driver name="postgresql" module="org.postgresql">
        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
    </driver>
</drivers>

<!-- Ajoutez vos datasources avant </subsystem> -->
<datasources>
    <datasource jndi-name="java:/jdbc/CentralDS" pool-name="CentralDS" enabled="true" use-java-context="true" statistics-enabled="false">
        <connection-url>jdbc:postgresql://localhost:5432/central_db</connection-url>
        <driver>postgresql</driver>
        <security>
            <user-name>postgres</user-name>
            <password>mamaika77</password>
        </security>
        <validation>
            <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
            <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
        </validation>
    </datasource>
    
    <datasource jndi-name="java:/jdbc/CompteCourantDS" pool-name="CompteCourantDS" enabled="true" use-java-context="true">
        <connection-url>jdbc:postgresql://localhost:5432/compte_courant_db</connection-url>
        <driver>postgresql</driver>
        <security>
            <user-name>postgres</user-name>
            <password>mamaika77</password>
        </security>
        <validation>
            <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
            <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
        </validation>
    </datasource>
    
    <datasource jndi-name="java:/jdbc/PretDS" pool-name="PretDS" enabled="true" use-java-context="true">
        <connection-url>jdbc:postgresql://localhost:5432/pret_db</connection-url>
        <driver>postgresql</driver>
        <security>
            <user-name>postgres</user-name>
            <password>mamaika77</password>
        </security>
        <validation>
            <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
            <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
        </validation>
    </datasource>
</datasources>

## Compilation et déploiement

### 1. Compilation des modules
Utilisez le script compile.bat fourni :

compile.bat

Ordre de compilation important :
1. pret (module indépendant)
2. comptecourant (module indépendant) 
3. centralisateur (dépend des deux précédents)

### 2. Déploiement dans WildFly
Utilisez le script deploy.bat :

deploy.bat

Ce script :
- Compile tous les modules
- Copie les artefacts dans WildFly :
  - centralisateur.war → Interface web principale
  - comptecourant-1.0-SNAPSHOT.jar → EJB comptecourant
  - pret-1.0-SNAPSHOT.jar → EJB pret

### 3. Démarrage de l'API .NET
cd comptedepot
dotnet run

## Utilisation de l'application

### 1. Démarrage des services

#### Démarrer WildFly
serveur.bat

Ou manuellement :
C:\wildfly-37.0.1.Final\bin\standalone.bat

#### Démarrer l'API comptedepot
cd comptedepot
dotnet run

### 2. Accès aux interfaces

#### Interface principale
- URL : http://localhost:8080/centralisateur
- Point d'entrée : centralisateur/src/main/webapp/index.jsp

#### Navigation disponible
Via centralisateur/src/main/webapp/includes/sidebar.jsp :
- 🏠 Accueil : Vue d'ensemble
- 👥 Clients : Gestion des clients via ClientDAO
- 💳 Comptes Courants : Via CompteCourantCentralService
- 🏦 Comptes Dépôt : Via CompteDepotService
- 💰 Prêts : Interface dans centralisateur/src/main/webapp/pret/prets.jsp

### 3. Fonctionnalités principales

#### Gestion des Prêts (votre fichier actuel)
Le fichier centralisateur/src/main/webapp/pret/prets.jsp permet :

- Affichage des prêts : Liste avec montants formatés en Ariary (Ar)
<fmt:formatNumber value="${pret.montant}" type="number" groupingUsed="true" /> Ar

- Création de prêts : Via PretServlet.doPost()
- Remboursements : Accès aux détails via PretCentralService

#### Comptes Courants
- Service principal : CompteCourantService
- Mouvements : Gestion via MouvementServlet
- Interface : centralisateur/src/main/webapp/courant/comptes.jsp

#### Comptes Dépôt (.NET API)
- Service : CompteDepotService.cs
- Configuration : comptedepot/appsettings.json
- URL API : http://localhost:5004

## Dépannage

### Problèmes courants

#### 1. Erreur de connexion base de données
# Vérifiez PostgreSQL
net start postgresql-x64-xx

# Testez la connexion
psql -U postgres -d central_db

#### 2. Problème de déploiement WildFly
# Consultez les logs
tail -f C:\wildfly-37.0.1.Final\standalone\log\server.log

# Forcer le redéploiement
touch C:\wildfly-37.0.1.Final\standalone\deployments\centralisateur.war.dodeploy

#### 3. Erreur de compilation Maven
# Nettoyage complet
mvn clean install -DskipTests

# Ou utilisez le script fourni
compile.bat

### Scripts utiles fournis

- serveur.bat : Démarre WildFly
- compile.bat : Compile tous les modules dans l'ordre
- deploy.bat : Compile et déploie
- acces_base.bat : Ouvre les consoles PostgreSQL
- script.sql : Réinitialise les données de test

### Ports utilisés
- 8080 : WildFly (application principale)
- 9990 : Console d'administration WildFly
- 5432 : PostgreSQL
- 5004 : API .NET (comptedepot)

### Architecture des données

Les 4 bases de données sont organisées ainsi :
- central_db : Clients et types de mouvements globaux
- compte_courant_db : Comptes courants et leurs mouvements
- compte_depot_db : Comptes dépôt avec paramètres d'intérêts
- pret_db : Prêts, types de prêts et remboursements

Cette séparation permet une gestion modulaire et une scalabilité future de chaque service métier.

L'application utilise le pattern EJB avec des services centralisés dans le module centralisateur qui orchestrent les appels vers les différents modules métier (comptecourant, pret) et l'API
