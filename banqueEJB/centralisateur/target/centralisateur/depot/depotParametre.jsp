<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Paramètres Compte Dépôt</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Paramètres Compte Dépôt</h2>
            <a href="comptesDepot.jsp" class="btn btn-secondary">Retour</a>
        </div>
        
        <!-- Navigation rapide -->
        <div class="card">
            <h3>Navigation</h3>
            <div class="btn-group">
                <a href="comptesDepot.jsp" class="btn btn-secondary">Gestion Comptes</a>
                <a href="depotOperation" class="btn btn-secondary">Opérations</a>
            </div>
        </div>
        
        <!-- Modifier le plafond de retrait -->
        <div class="card">
            <h3>Modifier le plafond de retrait</h3>
            <form action="depotParametre" method="post">
                <input type="hidden" name="action" value="plafond"/>
                
                <div class="form-row">
                    <label>
                        Compte ID
                        <input type="number" name="compteId" required>
                    </label>
                    
                    <label>
                        Nouveau plafond
                        <input type="number" name="nouveauPlafond" step="0.01" min="0" required placeholder="0.00">
                    </label>
                </div>
                
                <button type="submit" class="btn">Modifier le plafond</button>
            </form>
        </div>

        <!-- Modifier le taux d'intérêt -->
        <div class="card">
            <h3>Modifier le taux d'intérêt</h3>
            <form action="depotParametre" method="post">
                <input type="hidden" name="action" value="taux"/>
                
                <div class="form-row">
                    <label>
                        Compte ID
                        <input type="number" name="compteId" required>
                    </label>
                    
                    <label>
                        Nouveau taux d'intérêt (%)
                        <input type="number" name="nouveauTaux" step="0.01" min="0" max="100" required placeholder="0.00">
                    </label>
                </div>
                
                <button type="submit" class="btn">Modifier le taux</button>
            </form>
        </div>

        <!-- Messages d'état -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty result}">
            <div class="alert alert-success">${result}</div>
        </c:if>
    </div>
</body>
</html>