<!-- filepath: centralisateur/src/main/webapp/courant/mouvements.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historique des mouvements</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Historique des mouvements du compte</h2>
        </div>
        
        <div class="card">
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
                        <c:forEach var="mvt" items="${mouvements}">
                            <tr>
                                <td>${mvt.id}</td>
                                <td class="${mvt.montant < 0 ? 'amount-negative' : 'amount-positive'}">${mvt.montant} €</td>
                                <td>
                                    <c:forEach var="type" items="${typesMouvement}">
                                        <c:if test="${type.id == mvt.typeMouvementId}">
                                            <span class="status ${mvt.montant > 0 ? 'status-active' : 'status-inactive'}">${type.libelle}</span>
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>${mvt.dateMouvement}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <div class="actions">
                <a href="compte_courant" class="btn btn-secondary">Retour aux comptes</a>
            </div>
        </div>
    </div>
</body>
</html>