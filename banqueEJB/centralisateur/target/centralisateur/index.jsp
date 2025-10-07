<!-- filepath: centralisateur/src/main/webapp/index.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Banque EJB - Accueil</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>Système Bancaire EJB</h1>
            <p>Bienvenue dans l'application de gestion bancaire</p>
        </div>
        
        <div class="card">
            <h3>Services disponibles</h3>
            <div class="grid-2">
                <div class="card-item">
                    <h4>Comptes Courants</h4>
                    <p>Gestion des comptes courants et mouvements</p>
                    <a href="courant/comptes.jsp" class="btn">Accéder</a>
                </div>
                <div class="card-item">
                    <h4>Comptes Dépôt</h4>
                    <p>Gestion des comptes d'épargne et dépôts</p>
                    <a href="depot/comptesDepot.jsp" class="btn">Accéder</a>
                </div>
                <div class="card-item">
                    <h4>Prêts</h4>
                    <p>Gestion des prêts et remboursements</p>
                    <a href="pret?action=lister" class="btn">Accéder</a>
                </div>
                <div class="card-item">
                    <h4>Clients</h4>
                    <p>Gestion des clients</p>
                    <a href="clients/ajouterClient.jsp" class="btn">Accéder</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>