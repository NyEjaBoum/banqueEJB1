<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Opérations sur Compte Dépôt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>💳 Opérations sur Compte Dépôt</h1>
            <p>Effectuez des versements et des retraits sur vos comptes dépôt</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty result}">
            <div class="alert alert-success">${result}</div>
        </c:if>

        <div class="card">
            <h2>💰 Verser de l'argent</h2>
            <form action="depotOperation" method="post">
                <input type="hidden" name="action" value="versement"/>
                <div class="form-group">
                    <label>Compte ID:</label>
                    <input type="number" name="compteId" required>
                </div>
                <div class="form-group">
                    <label>Montant:</label>
                    <input type="number" name="montant" step="0.01" required>
                </div>
                <button type="submit" class="btn btn-primary">Verser</button>
            </form>
        </div>

        <div class="card">
            <h2>🏧 Retirer de l'argent</h2>
            <form action="depotOperation" method="post">
                <input type="hidden" name="action" value="retrait"/>
                <div class="form-group">
                    <label>Compte ID:</label>
                    <input type="number" name="compteId" required>
                </div>
                <div class="form-group">
                    <label>Montant:</label>
                    <input type="number" name="montant" step="0.01" required>
                </div>
                <button type="submit" class="btn btn-warning">Retirer</button>
            </form>
        </div>

        <div class="card">
            <h3>🔗 Actions rapides</h3>
            <a href="comptesDepot.jsp" class="btn btn-secondary">Retour aux comptes</a>
            <a href="depotParametre" class="btn btn-secondary">Modifier paramètres</a>
        </div>
    </div>
</body>
</html>