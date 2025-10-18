<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rembourser un prêt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>💳 Rembourser un Prêt</h1>
            <p>Effectuez un remboursement sur votre prêt</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>

        <div class="card">
            <h2>Formulaire de remboursement</h2>
            <form action="pret" method="post">
                <input type="hidden" name="action" value="rembourser"/>
                <input type="hidden" name="pretId" value="${pretId}"/>
                <div class="form-group">
                    <label>Montant à rembourser:</label>
                    <input type="number" name="montant" step="0.01" required/>
                </div>
                <button type="submit" class="btn btn-primary">Valider le remboursement</button>
                <a href="pret" class="btn btn-secondary">Annuler</a>
            </form>
        </div>
    </div>
</body>
</html>