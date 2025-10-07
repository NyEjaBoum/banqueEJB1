<!-- filepath: centralisateur/src/main/webapp/pret/historiquePret.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historique des remboursements</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Historique des remboursements du prêt</h2>
        </div>
        
        <div class="card">
            <div class="table-container">
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
            </div>
            
            <div class="actions">
                <a href="pret" class="btn btn-secondary">Retour à la liste des prêts</a>
            </div>
        </div>
    </div>
</body>
</html>