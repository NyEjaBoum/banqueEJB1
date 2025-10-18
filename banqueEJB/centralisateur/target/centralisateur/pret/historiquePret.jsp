<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historique des remboursements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>📜 Historique des Remboursements</h1>
            <p>Consultez l'historique des remboursements du prêt</p>
        </div>

        <div class="card">
            <h2>Liste des remboursements</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Montant</th>
                        <th>Intérêts payés</th>
                        <th>Capital remboursé</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${remboursements}">
                        <tr>
                            <td>${r.id}</td>
                            <td>${r.montant} €</td>
                            <td>${r.interetPayes} €</td>
                            <td>${r.capitalRembourse} €</td>
                            <td>${r.dateRemboursement}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>
            <a href="pret" class="btn btn-secondary">Retour à la liste des prêts</a>
        </div>
    </div>
</body>
</html>