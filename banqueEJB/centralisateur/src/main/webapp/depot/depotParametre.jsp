<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Paramètres Compte Dépôt</title>
</head>
<body>
<a href="comptesDepot.jsp">Retour</a>
<h2>Modifier le plafond de retrait d'un compte</h2>
<form action="depotParametre" method="post">
    <input type="hidden" name="action" value="plafond"/>
    <label>Compte ID: <input type="number" name="compteId" required></label>
    <label>Nouveau plafond: <input type="number" name="nouveauPlafond" step="0.01" required></label>
    <button type="submit">Modifier</button>
</form>
<ul>
    <li><a href="depotOperation">Versement / Retrait</a></li>
    <li><a href="compte_depot">Versement / Retrait</a></li>
</ul>
<h2>Modifier le taux d'intérêt d'un compte</h2>
<form action="depotParametre" method="post">
    <input type="hidden" name="action" value="taux"/>
    <label>Compte ID: <input type="number" name="compteId" required></label>
    <label>Nouveau taux d'intérêt: <input type="number" name="nouveauTaux" step="0.01" required></label>
    <button type="submit">Modifier</button>
</form>

<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
<c:if test="${not empty result}">
    <div style="color:green;">${result}</div>
</c:if>
</body>
</html>