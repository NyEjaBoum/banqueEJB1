<!-- filepath: centralisateur/src/main/webapp/courant/mouvements.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Historique des mouvements</title>
</head>
<body>
<h2>Historique des mouvements du compte</h2>
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
            <td>
                <c:forEach var="type" items="${typesMouvement}">
                    <c:if test="${type.id == mvt.typeMouvementId}">
                        ${type.libelle}
                    </c:if>
                </c:forEach>
            </td>
            <td>${mvt.dateMouvement}</td>
        </tr>
    </c:forEach>
</table>
<a href="compte_courant">Retour aux comptes</a>
</body>
</html>