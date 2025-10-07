<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Opérations sur Compte Dépôt</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Opérations sur Compte Dépôt</h2>
            <a href="comptesDepot.jsp" class="btn btn-secondary">Retour</a>
        </div>
        
        <!-- Navigation rapide -->
        <div class="card">
            <h3>Navigation</h3>
            <div class="btn-group">
                <a href="comptesDepot.jsp" class="btn btn-secondary">Gestion Comptes</a>
                <a href="depotParametre" class="btn btn-secondary">Modifier paramètres</a>
            </div>
        </div>
        
        <!-- Verser de l'argent -->
        <div class="card">
            <h3>Verser de l'argent</h3>
            <form action="depotOperation" method="post">
                <input type="hidden" name="action" value="versement"/>
                
                <div class="form-row">
                    <label>
                        Compte ID
                        <input type="number" name="compteId" required>
                    </label>
                    
                    <label>
                        Montant
                        <input type="number" name="montant" step="0.01" min="0.01" required placeholder="0.00">
                    </label>
                </div>
                
                <button type="submit" class="btn">Effectuer le versement</button>
            </form>
        </div>

        <!-- Retirer de l'argent -->
        <div class="card">
            <h3>Retirer de l'argent</h3>
            <form action="depotOperation" method="post">
                <input type="hidden" name="action" value="retrait"/>
                
                <div class="form-row">
                    <label>
                        Compte ID
                        <input type="number" name="compteId" required>
                    </label>
                    
                    <label>
                        Montant
                        <input type="number" name="montant" step="0.01" min="0.01" required placeholder="0.00">
                    </label>
                </div>
                
                <button type="submit" class="btn btn-warning">Effectuer le retrait</button>
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