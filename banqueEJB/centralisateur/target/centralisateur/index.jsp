<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Système Bancaire - Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>🏦 Système de Gestion Bancaire</h1>
            <p>Bienvenue dans votre espace de gestion bancaire</p>
        </div>

        <div class="card">
            <h2>📊 Tableau de bord</h2>
            <p>Utilisez le menu à gauche pour accéder aux différents modules :</p>
            <ul>
                <li><strong>Comptes Courants</strong> : Gérer les comptes courants et leurs mouvements</li>
                <li><strong>Comptes Dépôt</strong> : Gérer les comptes dépôt avec intérêts</li>
                <li><strong>Prêts</strong> : Gérer les prêts et les remboursements</li>
                <li><strong>Clients</strong> : Ajouter et gérer les clients</li>
            </ul>
        </div>

        <div class="card">
            <h2>🚀 Démarrage rapide</h2>
            <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                <a href="clients/ajouterClient.jsp" class="btn btn-primary">➕ Nouveau Client</a>
                <a href="compte_courant" class="btn btn-primary">🏦 Comptes Courants</a>
                <a href="compte_depot" class="btn btn-primary">💰 Comptes Dépôt</a>
                <a href="pret" class="btn btn-primary">💳 Prêts</a>
            </div>
        </div>
    </div>
</body>
</html>