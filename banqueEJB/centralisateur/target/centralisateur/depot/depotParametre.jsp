<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paramètres Compte Dépôt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>⚙️ Paramètres Compte Dépôt</h1>
            <p>Gérez les paramètres de vos comptes dépôt</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty result}">
            <div class="alert alert-success">${result}</div>
        </c:if>

        <div class="card">
            <h2>📊 Modifier le plafond de retrait</h2>
            <form action="depotParametre" method="post">
                <input type="hidden" name="action" value="plafond"/>
                <div class="form-group">
                    <label>Compte ID:</label>
                    <input type="number" name="compteId" required>
                </div>
                <div class="form-group">
                    <label>Nouveau plafond:</label>
                    <input type="number" name="nouveauPlafond" step="0.01" required>
                </div>
                <button type="submit" class="btn btn-primary">Modifier le plafond</button>
            </form>
        </div>

        <div class="card">
            <h2>📈 Modifier le taux d'intérêt</h2>
            <form action="depotParametre" method="post">
                <input type="hidden" name="action" value="taux"/>
                <div class="form-group">
                    <label>Compte ID:</label>
                    <input type="number" name="compteId" required>
                </div>
                <div class="form-group">
                    <label>Nouveau taux d'intérêt (%):</label>
                    <input type="number" name="nouveauTaux" step="0.01" required>
                </div>
                <button type="submit" class="btn btn-primary">Modifier le taux</button>
            </form>
        </div>

        <div class="card">
            <h2>⚙️ Modifier les paramètres globaux</h2>
            <form action="depotParametre" method="post">
                <input type="hidden" name="action" value="modifierParametre"/>
                <div class="form-group">
                    <label>Nouveau plafond global :</label>
                    <input type="number" name="plafond" step="0.01">
                </div>
                <div class="form-group">
                    <label>Nouveau taux d'intérêt global (%):</label>
                    <input type="number" name="taux" step="0.01">
                </div>
                <button type="submit" class="btn btn-primary">Modifier</button>
            </form>
        </div>

        <div class="card">
            <h3>🔗 Actions rapides</h3>
            <a href="comptesDepot.jsp" class="btn btn-secondary">Retour aux comptes</a>
            <a href="depotOperation" class="btn btn-secondary">Opérations</a>
        </div>
    </div>
</body>
</html>