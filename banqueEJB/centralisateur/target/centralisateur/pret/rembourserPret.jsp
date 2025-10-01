<!-- filepath: centralisateur/src/main/webapp/pret/rembourserPret.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Rembourser un prêt</title>
</head>
<body>
<h2>Rembourser le prêt</h2>
<form action="pret" method="post">
    <input type="hidden" name="action" value="rembourser"/>
    <input type="hidden" name="pretId" value="${pretId}"/>
    <label>Montant à rembourser: <input type="number" name="montant" step="0.01" required/></label><br>
    <button type="submit">Valider le remboursement</button>
</form>

<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
<a href="pret">Retour à la liste des prêts</a>
</body>
</html>