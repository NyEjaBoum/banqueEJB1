<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historique des mouvements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>📜 Historique des Mouvements</h1>
            <p>Consultez l'historique des transactions du compte</p>
        </div>

        <div class="card">
            <h2>Liste des mouvements</h2>
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
                    <c:forEach var="mvt" items="${mouvements}">
                        <tr>
                            <td>${mvt.id}</td>
                            <td>${mvt.montant} €</td>
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
            <br>
            <a href="compte_courant" class="btn btn-secondary">Retour aux comptes</a>
        </div>
    </div>
</body>
</html>