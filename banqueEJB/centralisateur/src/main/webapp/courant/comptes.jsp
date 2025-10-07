<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.comptecourant.entity.CompteCourant,com.comptecourant.entity.MouvementCourant,java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion Compte Courant</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>Gestion des Comptes Courants</h1>
        </div>
        
        <!-- Créer un compte courant -->
        <div class="card">
            <h3>Créer un compte courant</h3>
            <form action="comptes" method="post">
                <label>
                    Client ID
                    <input type="number" name="clientId" required>
                </label>
                <button type="submit" name="action" value="creer" class="btn">Créer le compte</button>
            </form>
        </div>

        <!-- Liste des comptes courants -->
        <div class="card">
            <h3>Comptes Courants</h3>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
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
                                    <c:forEach var="clients" items="${listClients}">
                                        <c:if test="${clients.id eq compte.clientId}">
                                            ${clients.nom} ${clients.prenom}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="${compte.solde < 0 ? 'amount-negative' : 'amount-positive'}">${compte.solde} €</td>
                                <td>${compte.dateMaj}</td>
                                <td>
                                    <div class="btn-group">
                                        <form action="compte_courant" method="get" style="display:inline;">
                                            <input type="hidden" name="compteId" value="${compte.id}" />
                                            <button type="submit" name="action" value="solde" class="btn btn-small">Solde</button>
                                        </form>
                                        <form action="compte_courant" method="get" style="display:inline;">
                                            <input type="hidden" name="compteId" value="${compte.id}" />
                                            <input type="hidden" name="action" value="historique"/>
                                            <button type="submit" class="btn btn-small btn-secondary">Historique</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Ajouter un mouvement -->
        <div class="card">
            <h3>Ajouter un mouvement courant</h3>
            <form action="compte_courant" method="post">
                <input type="hidden" name="action" value="mouvement"/>
                
                <div class="form-row">
                    <label>
                        Compte
                        <select name="compteId" required>
                            <option value="">Sélectionner un compte</option>
                            <c:forEach var="compte" items="${comptes}">
                                <option value="${compte.id}">
                                    ${compte.id} - 
                                    <c:forEach var="clients" items="${listClients}">
                                        <c:if test="${clients.id eq compte.clientId}">
                                            ${clients.nom} ${clients.prenom}
                                        </c:if>
                                    </c:forEach>
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                    
                    <label>
                        Montant
                        <input type="number" name="montant" step="0.01" required placeholder="0.00">
                    </label>
                    
                    <label>
                        Type
                        <select name="type" required>
                            <option value="">Sélectionner un type</option>
                            <c:forEach var="type" items="${typesMouvement}">
                                <option value="${type.id}">${type.libelle}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                
                <button type="submit" class="btn">Valider le mouvement</button>
            </form>
        </div>

        <!-- Affichage du solde -->
        <c:if test="${not empty solde}">
            <div class="alert alert-info">Solde du compte sélectionné : ${solde} €</div>
        </c:if>

        <!-- Messages d'erreur -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
    </div>
</body>
</html>