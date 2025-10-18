<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion Compte Dépôt</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Gestion Compte Dépôt</h2>
        </div>
            <div class="card">
        <h2>📋 Liste des Comptes Dépôt</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Client ID</th>
                    <th>Solde</th>
                    <th>Taux Intérêt</th>
                    <th>Plafond Retrait</th>
                    <th>Date Dernier Intérêt</th>
                    <th>Actif</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="compte" items="${comptesDepot}">
                    <tr>
                        <td>
                            <a href="clientInfo?clientId=${compte.clientId}">
                                ${compte.clientId}
                            </a>
                        </td>
                        <td>${compte.clientId}</td>
                        <td>${compte.solde}</td>
                        <td>${compte.tauxInteret}</td>
                        <td>${compte.plafondRetrait}</td>
                        <td>${compte.dateDernierInteret}</td>
                        <td>
                            <c:if test="${compte.actif}">✅</c:if>
                            <c:if test="${not compte.actif}">❌</c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

        <div class="card">
            <h3>Créer un compte dépôt</h3>
            <form action="compte_depot" method="post">
                <input type="hidden" name="action" value="creer"/>
                <label>
                    Client ID
                    <input type="number" name="clientId" required>
                </label>
                <label>
                    Plafond Retrait (optionnel)
                    <input type="number" name="plafondRetrait" step="0.01">
                </label>
                <label>
                    Taux Intérêt (optionnel)
                    <input type="number" name="tauxInteret" step="0.01">
                </label>
                <button type="submit" class="btn">Créer</button>
            </form>
        </div>

        <div class="card">
            <h3>Opérations disponibles</h3>
            <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                <a href="depotOperation" class="btn btn-secondary">Versement / Retrait</a>
                <a href="depotParametre" class="btn btn-secondary">Modifier paramètres</a>
            </div>
        </div>

        <div class="card">
            <h3>Consulter solde</h3>
            <form action="compte_depot" method="post">
                <input type="hidden" name="action" value="solde"/>
                <label>
                    Compte ID
                    <input type="number" name="compteId" required>
                </label>
                <button type="submit" class="btn">Voir solde</button>
            </form>
        </div>

        <c:if test="${not empty solde}">
            <div class="alert alert-info">
                <strong>Solde du compte :</strong> ${solde}
            </div>
        </c:if>

        <div class="card">
            <h3>Historique des mouvements</h3>
            <form action="compte_depot" method="post">
                <input type="hidden" name="action" value="historique"/>
                <label>
                    Compte ID
                    <input type="number" name="compteId" required>
                </label>
                <button type="submit" class="btn">Voir historique</button>
            </form>
        </div>

        <c:if test="${not empty historique}">
            <div class="card">
                <h3>Historique des mouvements</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Montant</th>
                            <th>Type</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mvt" items="${historique}">
                            <tr>
                                <td>${mvt.id}</td>
                                <td>${mvt.montant}</td>
                                <td>
                                    <c:forEach var="type" items="${typesMouvement}">
                                        <c:if test="${type.id == mvt.typeMouvementId}">
                                            ${type.libelle}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>${mvt.dateMouvement}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        
        <c:if test="${not empty result}">
            <div class="alert alert-success">${result}</div>
        </c:if>
    </div>
</body>
</html>