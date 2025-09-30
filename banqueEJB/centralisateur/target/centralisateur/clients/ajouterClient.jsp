<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ajouter un client</title>
</head>
<body>
<h2>Ajouter un client</h2>
<form action="clients" method="post">
    <input type="hidden" name="action" value="ajouter"/>
    <label>Nom: <input type="text" name="nom" required></label><br>
    <label>Prénom: <input type="text" name="prenom" required></label><br>
    <label>Email: <input type="email" name="email"></label><br>
    <label>Téléphone: <input type="text" name="telephone"></label><br>
    <label>Numéro client: <input type="number" name="numeroClient" required></label><br>
    <button type="submit">Ajouter</button>
</form>
<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
<c:if test="${not empty success}">
    <div style="color:green;">${success}</div>
</c:if>
</body>
</html>