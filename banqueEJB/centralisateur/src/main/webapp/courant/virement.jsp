<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Effectuer un Virement</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>💸 Effectuer un Virement</h1>
            <p>Transférez de l'argent entre comptes courants</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <div class="card">
            <h2>💰 Nouveau Virement</h2>
            <form action="virement" method="post">
                <div class="form-group">
                    <label>Compte débiteur (source) :</label>
                    <select name="compteDebiteurId" required>
                        <option value="">-- Sélectionner un compte --</option>
                        <c:forEach var="compte" items="${comptes}">
                            <option value="${compte.id}">
                                ${compte.id} - 
                                <c:forEach var="client" items="${listClients}">
                                    <c:if test="${client.id eq compte.clientId}">
                                        ${client.nom} ${client.prenom}
                                    </c:if>
                                </c:forEach>
                                (Solde: ${compte.solde} €)
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Compte créditeur (destination) :</label>
                    <select name="compteCrediteurId" required>
                        <option value="">-- Sélectionner un compte --</option>
                        <c:forEach var="compte" items="${comptes}">
                            <option value="${compte.id}">
                                ${compte.id} - 
                                <c:forEach var="client" items="${listClients}">
                                    <c:if test="${client.id eq compte.clientId}">
                                        ${client.nom} ${client.prenom}
                                    </c:if>
                                </c:forEach>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Montant :</label>
                    <input type="number" name="montant" step="0.01" min="0.01" required>
                    <small>Montant en ariary</small>
                </div>
                
                <button type="submit" class="btn btn-primary">Effectuer le virement</button>
                <a href="compte_courant" class="btn btn-secondary">Retour aux comptes</a>
            </form>
        </div>
    </div>
</body>
</html>