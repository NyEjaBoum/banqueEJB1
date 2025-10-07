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
            <h1>Gestion des Comptes Dépôt</h1>
        </div>
        
        <!-- Créer un compte dépôt -->
        <div class="card">
            <h3>Créer un compte dépôt</h3>
            <form action="compte_depot" method="post">
                <input type="hidden" name="action" value="creer"/>
                
                <div class="form-row">
                    <label>
                        Client ID
                        <input type="number" name="clientId" required>
                    </label>
                    
                    <label>
                        Plafond Retrait
                        <input type="number" name="plafondRetrait" step="0.01" placeholder="0.00">
                    </label>
                    
                    <label>
                        Taux Intérêt (%)
                        <input type="number" name="tauxInteret" step="0.01" placeholder="0.00">
                    </label>
                </div>
                
                <button type="submit" class="btn">Créer le compte</button>
            </form>
        </div>

        <!-- Navigation des opérations -->
        <div class="card">
            <h3>Opérations disponibles</h3>
            <div class="grid-2">
                <a href="depotOperation" class="card-item-link">
                    <div class="card-item">
                        <h4>Versement / Retrait</h4>
                        <p>Effectuer des opérations sur les comptes</p>
                    </div>
                </a>
                <a href="depotParametre" class="card-item-link">
                    <div class="card-item">
                        <h4>Modifier paramètres</h4>
                        <p>Ajuster les paramètres des comptes</p>
                    </div>
                </a>
            </div>
        </div>

        <!-- Consulter solde -->
        <div class="card">
            <h3>Consulter solde</h3>
            <form action="compte_depot" method="post">
                <input type="hidden" name="action" value="solde"/>
                <label>
                    Compte ID
                    <input type="number" name="compteId" required>
                </label>
                <button type="submit" class="btn btn-secondary">Voir solde</button>
            </form>
        </div>

        <!-- Historique des mouvements -->
        <div class="card">
            <h3>Historique des mouvements</h3>
            <form action="compte_depot" method="post">
                <input type="hidden" name="action" value="historique"/>
                <label>
                    Compte ID
                    <input type="number" name="compteId" required>
                </label>
                <button type="submit" class="btn btn-secondary">Voir historique</button>
            </form>
        </div>

        <!-- Messages d'état -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty result}">
            <div class="alert alert-success">${result}</div>
        </c:if>
        <c:if test="${not empty solde}">
            <div class="alert alert-info">Solde du compte : ${solde} €</div>
        </c:if>

        <!-- Historique des mouvements -->
        <c:if test="${not empty historique}">
            <div class="card">
                <h3>Historique des mouvements</h3>
                <div class="table-container">
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
                                    <td class="${mvt.type == 'DEBIT' ? 'amount-negative' : 'amount-positive'}">${mvt.montant} €</td>
                                    <td>
                                        <span class="status ${mvt.type == 'CREDIT' ? 'status-active' : 'status-inactive'}">${mvt.type}</span>
                                    </td>
                                    <td>${mvt.dateMouvement}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </div>
</body>
</html>