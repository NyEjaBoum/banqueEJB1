<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.comptecourant.entity.CompteCourant,com.comptecourant.entity.MouvementCourant,java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion Compte Courant</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>🏦 Gestion Comptes Courants</h1>
            <p>Gérez vos comptes courants et leurs mouvements</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty solde}">
            <div class="alert alert-info">Solde du compte sélectionné : ${solde} €</div>
        </c:if>

        <div class="card">
            <h2>➕ Créer un compte courant</h2>
            <form action="comptes" method="post">
                <div class="form-group">
                    <label>Client ID:</label>
                    <input type="number" name="clientId" required>
                </div>
                <button type="submit" name="action" value="creer" class="btn btn-primary">Créer</button>
            </form>
        </div>

        <div class="card">
            <h2>📋 Liste des Comptes Courants</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th> Client Id </th>
                        <th>Client</th>
                        <th>Solde</th>
                        <th>Date Maj</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="compte" items="${comptes}">
                        <tr>
                            <td>${compte.id}</td>
                            <td>
                                <a href="clientInfo?clientId=${compte.clientId}">
                                    ${compte.clientId}
                                </a>
                    </td>
                            <td>
                                <c:forEach var="clients" items="${listClients}">
                                    <c:if test="${clients.id eq compte.clientId}">
                                        ${clients.nom} ${clients.prenom}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${compte.solde} €</td>
                            <td>${compte.dateMaj}</td>
                            <td>
                                <form action="compte_courant" method="get" style="display:inline;">
                                    <input type="hidden" name="compteId" value="${compte.id}" />
                                    <button type="submit" name="action" value="solde" class="btn btn-sm">Solde</button>
                                </form>
                                <form action="compte_courant" method="get" style="display:inline;">
                                    <input type="hidden" name="compteId" value="${compte.id}" />
                                    <input type="hidden" name="action" value="historique"/>
                                    <button type="submit" class="btn btn-sm">Historique</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="card">
            <h2>💸 Ajouter un mouvement</h2>
            <form action="compte_courant" method="post">
                <input type="hidden" name="action" value="mouvement"/>
                <div class="form-group">
                    <label>Compte:</label>
                    <select name="compteId" required>
                        <c:forEach var="compte" items="${comptes}">
                            <option value="${compte.id}">${compte.id} - 
                                <c:forEach var="clients" items="${listClients}">
                                    <c:if test="${clients.id eq compte.clientId}">
                                        ${clients.nom} ${clients.prenom}
                                    </c:if>
                                </c:forEach>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Montant:</label>
                    <input type="number" name="montant" step="0.01" required>
                </div>
                <div class="form-group">
                    <label>Type de mouvement:</label>
                    <select name="type" required>
                        <c:forEach var="type" items="${typesMouvement}">
                            <option value="${type.id}">${type.libelle}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Valider</button>
            </form>
        </div>
        <div class="card">
    <h2>🔄 Actions rapides</h2>
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
        <a href="virement" class="btn btn-primary">💸 Effectuer un virement</a>
        <a href="${pageContext.request.contextPath}" class="btn btn-secondary">🏠 Accueil</a>
    </div>
</div>
    </div>
</body>
</html>