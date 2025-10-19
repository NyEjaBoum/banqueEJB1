<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Prêts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>💰 Gestion des Prêts</h1>
            <p>Gérez les prêts et les remboursements</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>

        <div class="card">
            <h2>📋 Liste des prêts</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Client ID</th>
                        <th>Type</th>
                        <th>Montant</th>
                        <th>Taux (%)</th>
                        <th>Date début</th>
                        <th>Date fin</th>
                        <th>État</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pret" items="${prets}">
                        <tr>
                            <td>
                                <a href="clientInfo?clientId=${pret.clientId}">
                                    ${pret.clientId}
                                </a>
                            </td>
                            <td>${pret.clientId}</td>
                            <td>
                                <c:forEach var="type" items="${typesPret}">
                                    <c:if test="${type.id == pret.typePretId}">
                                        ${type.libelle}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="type" items="${typesPret}">
                                    <c:if test="${type.id == pret.typePretId}">
                                        ${type.montant} €
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${pret.tauxInteret}</td>
                            <td>${pret.dateDebut}</td>
                            <td>${pret.dateFin}</td>
                            <td><span class="badge">${pret.etat}</span></td>
                            <td>
                                <form action="pret" method="get" style="display:inline;">
                                    <input type="hidden" name="action" value="historique"/>
                                    <input type="hidden" name="pretId" value="${pret.id}"/>
                                    <button type="submit" class="btn btn-sm">Historique</button>
                                </form>
                                <a href="pret?action=rembourserPage&pretId=${pret.id}" class="btn btn-sm btn-primary">Rembourser</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="card">
            <h2>➕ Créer un nouveau prêt</h2>
            <form action="pret" method="post">
                <input type="hidden" name="action" value="creer"/>
                <div class="form-group">
                    <label>Client ID:</label>
                    <input type="number" name="clientId" required>
                </div>
                <div class="form-group">
                    <label>Montant:</label>
                    <input type="number" name="montant" required>
                </div>
                <div class="form-group">
                    <label>Type de prêt:</label>
                        <select name="typePretId" required>
                            <c:forEach var="type" items="${typesPret}">
                                <option value="${type.id}">
                                    ${type.libelle} (
                                    <fmt:formatNumber value="${type.montant}" type="number" groupingUsed="true" /> Ar,
                                    ${type.interet}% sur ${type.nbMoisRemboursement} mois)
                                </option>
                            </c:forEach>
                        </select>
                </div>
                <div class="form-group">
                    <label>Date Début:</label>
                    <input type="date" name="dateDebut" required>
                </div>
                <button type="submit" class="btn btn-primary">Créer</button>
            </form>
        </div>
    </div>
</body>
</html>