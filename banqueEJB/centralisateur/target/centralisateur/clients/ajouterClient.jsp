<!-- filepath: centralisateur/src/main/webapp/clients/ajouterClient.jsp -->
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
            <!-- filepath: centralisateur/src/main/webapp/clients/ajouterClient.jsp -->
            <form action="clients" method="post">
                <input type="hidden" name="id" value="${clientEdit.id}" />
                <div class="form-group">
                    <label>Nom:</label>
                    <input type="text" name="nom" value="${clientEdit.nom}" required>
                </div>
                <div class="form-group">
                    <label>Prénom:</label>
                    <input type="text" name="prenom" value="${clientEdit.prenom}" required>
                </div>
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value="${clientEdit.email}">
                </div>
                <div class="form-group">
                    <label>Téléphone:</label>
                    <input type="text" name="telephone" value="${clientEdit.telephone}">
                </div>
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${not empty clientEdit}">Mettre à jour</c:when>
                        <c:otherwise>Ajouter le client</c:otherwise>
                    </c:choose>
                </button>
            </form>
        </div>

        <div class="card">
            <h2>👤 Liste des clients</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Email</th>
                        <th>Téléphone</th>
                        <th>Modifier</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="client" items="${clients}">
                        <tr>
                            <td>${client.id}</td>
                            <td>${client.nom}</td>
                            <td>${client.prenom}</td>
                            <td>${client.email}</td>
                            <td>${client.telephone}</td>
                            <td> <a href="clients?action=edit&id=${client.id}" class="btn btn-secondary">Modifier</a> </td>
                        </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>