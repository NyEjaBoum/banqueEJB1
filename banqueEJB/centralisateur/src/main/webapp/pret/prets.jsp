<!-- filepath: centralisateur/src/main/webapp/pret/prets.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Prêts</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Liste des prêts</h2>
        </div>
        
        <div class="card">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Client</th>
                            <th>Type</th>
                            <th>Montant</th>
                            <th>Taux</th>
                            <th>Date début</th>
                            <th>Date fin</th>
                            <th>Etat</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pret" items="${prets}">
                            <tr>
                                <td>${pret.id}</td>
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
                                <td>${pret.tauxInteret}%</td>
                                <td>${pret.dateDebut}</td>
                                <td>${pret.dateFin}</td>
                                <td>
                                    <span class="status ${pret.etat == 'ACTIF' ? 'status-active' : 'status-inactive'}">${pret.etat}</span>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <form action="pret" method="get" style="display:inline;">
                                            <input type="hidden" name="action" value="historique"/>
                                            <input type="hidden" name="pretId" value="${pret.id}"/>
                                            <button type="submit" class="btn btn-small">Historique</button>
                                        </form>
                                        <a href="pret?action=rembourserPage&pretId=${pret.id}" class="btn btn-small btn-secondary">Rembourser</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="card">
            <h3>Créer un nouveau prêt</h3>
            <form action="pret" method="post">
                <input type="hidden" name="action" value="creer"/>
                
                <div class="form-row">
                    <label>
                        Client ID
                        <input type="number" name="clientId" required>
                    </label>
                    
                    <label>
                        Type Prêt ID
                        <input type="number" name="typePretId" required>
                    </label>
                </div>
                
                <div class="form-row">
                    <label>
                        Montant
                        <input type="number" name="montant" step="0.01" required>
                    </label>
                    
                    <label>
                        Date Début
                        <input type="date" name="dateDebut" required>
                    </label>
                </div>
                
                <button type="submit" class="btn">Créer le prêt</button>
            </form>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
    </div>
</body>
</html>