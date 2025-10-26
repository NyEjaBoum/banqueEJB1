# Déploiement et utilisation du conteneur Docker pour le projet `change-ejb`

Ce guide explique comment utiliser Docker pour déployer et faire fonctionner le service EJB `change` avec WildFly.

---

## 1. Compilation et construction de l'image Docker

- Le script `dock.bat` compile le projet Maven et construit l'image Docker :
  1. Compile le projet Java avec Maven (`mvn clean install`)
  2. Construit l'image Docker avec le code et les ressources (`docker build -t change-ejb .`)

---

## 2. Gestion du conteneur Docker

- Le script arrête le conteneur existant :
  ```
  docker stop change-ejb
  ```
- Puis il démarre le conteneur (ou le crée s'il n'existe pas) :
  ```
  docker start change-ejb || docker run -d -p 8081:8080 --name change-ejb change-ejb
  ```
- Le service EJB est alors disponible sur le port **8081**.

---

## 3. Mise à jour du fichier `cours.csv`

- Pour que le conteneur utilise la dernière version de `cours.csv` :
  - Modifiez le fichier dans `change/src/main/resources/cours.csv`
  - Recompilez et reconstruisez l'image Docker
  - **Si le fichier ne se met pas à jour, utilisez** :
    ```
    docker build --no-cache -t change-ejb .
    ```
  - Supprimez et recréez le conteneur si besoin :
    ```
    docker stop change-ejb
    docker rm change-ejb
    docker run -d -p 8081:8080 --name change-ejb change-ejb
    ```

---

## 4. Création de l'utilisateur EJB dans WildFly

- Pour permettre l'accès distant au service EJB, créez un utilisateur dans le conteneur :
  ```
  docker exec -it change-ejb /opt/jboss/wildfly/bin/add-user.sh -a -u nyeja -p nyeja
  ```
  - Choisissez **ApplicationRealm** quand demandé.

---

## 5. Vérification et dépannage

- Pour vérifier que le bon fichier `cours.csv` est utilisé :
  ```
  docker exec -it change-ejb cat /opt/jboss/wildfly/standalone/change/cours.csv
  ```
- Si le service ne trouve pas le cours ou si l'authentification échoue :
  - Vérifiez le contenu du fichier dans le conteneur
  - Vérifiez l'utilisateur et le mot de passe côté client et serveur
  - Rebuild l'image Docker avec `--no-cache` si nécessaire

---

## 6. Exemple d'accès au service EJB depuis le client Java

```java
Properties props = new Properties();
props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
props.put(Context.PROVIDER_URL, "http-remoting://localhost:8081");
props.put(Context.SECURITY_PRINCIPAL, "nyeja");
props.put(Context.SECURITY_CREDENTIALS, "nyeja");
props.put("jboss.naming.client.ejb.context", true);
props.put("jboss.sasl.local-user.quiet-auth", "true");
```

---

**En résumé** :  
- Compilez et construisez l'image Docker après chaque modification.
- Gérez le conteneur avec les commandes Docker.
- Créez l'utilisateur EJB dans WildFly pour l'accès distant.
- Vérifiez et dépannez avec les commandes Docker si besoin.