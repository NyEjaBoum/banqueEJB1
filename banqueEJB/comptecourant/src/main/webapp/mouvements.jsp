<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Historique des Mouvements</title>
</head>
<body>
<h2>Historique des mouvements du compte ${compte.id}</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Montant</th>
        <th>Type</th>
        <th>Date</th>
    </tr>
    <c:forEach var="mvt" items="${mouvements}">
        <tr>
            <td>${mvt.id}</td>
            <td>${mvt.montant}</td>
            <td>${mvt.type}</td>
            <td>${mvt.dateMouvement}</td>
        </tr>
    </c:forEach>
</table>
<a href="comptes">Retour aux comptes</a>
</body>
</html>