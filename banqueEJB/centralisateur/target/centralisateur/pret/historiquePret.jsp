<!-- filepath: centralisateur/src/main/webapp/pret/historiquePret.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Historique des remboursements</title>
</head>
<body>
<h2>Historique des remboursements du prêt</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Montant</th>
        <th>Intérêts payés</th>
        <th>Capital remboursé</th>
        <th>Date</th>
    </tr>
    <c:forEach var="r" items="${remboursements}">
        <tr>
            <td>${r.id}</td>
            <td>${r.montant}</td>
            <td>${r.interetPayes}</td>
            <td>${r.capitalRembourse}</td>
            <td>${r.dateRemboursement}</td>
        </tr>
    </c:forEach>
</table>
<a href="pret">Retour à la liste des prêts</a>
</body>
</html>