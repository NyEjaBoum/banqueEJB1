<!-- filepath: centralisateur/src/main/webapp/pret/rembourserPret.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rembourser un prêt</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Rembourser le prêt</h2>
        </div>
        
        <div class="card">
            <form action="pret" method="post">
                <input type="hidden" name="action" value="rembourser"/>
                <input type="hidden" name="pretId" value="${pretId}"/>
                
                <label>
                    Montant à rembourser
                    <input type="number" name="montant" step="0.01" required/>
                </label>
                
                <button type="submit" class="btn">Valider le remboursement</button>
                <a href="pret" class="btn btn-secondary">Annuler</a>
            </form>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
    </div>
</body>
</html>