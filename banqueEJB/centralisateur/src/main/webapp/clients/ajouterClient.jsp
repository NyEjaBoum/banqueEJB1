<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h1>👤 Ajouter un Client</h1>
            <p>Enregistrez un nouveau client dans le système</p>
        </div>

        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <div class="card">
            <h2>Formulaire d'inscription</h2>
            <form action="clients" method="post">
                <input type="hidden" name="action" value="ajouter"/>
                <div class="form-group">
                    <label>Nom:</label>
                    <input type="text" name="nom" required>
                </div>
                <div class="form-group">
                    <label>Prénom:</label>
                    <input type="text" name="prenom" required>
                </div>
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email">
                </div>
                <div class="form-group">
                    <label>Téléphone:</label>
                    <input type="text" name="telephone">
                </div>
                <%-- <div class="form-group">
                    <label>Numéro client:</label>
                    <input type="number" name="numeroClient" required>
                </div> --%>
                <button type="submit" class="btn btn-primary">Ajouter le client</button>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Retour</a>
            </form>
        </div>
    </div>
</body>
</html>