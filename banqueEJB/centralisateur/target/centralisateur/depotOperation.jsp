<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Opérations sur Compte Dépôt</title>
</head>
<body>
<a href="comptesDepot.jsp">Retour</a>
<h2>Verser de l'argent</h2>
<form action="depotOperation" method="post">
    <input type="hidden" name="action" value="versement"/>
    <label>Compte ID: <input type="number" name="compteId" required></label>
    <label>Montant: <input type="number" name="montant" step="0.01" required></label>
    <button type="submit">Verser</button>
</form>

<ul>
    <li><a href="compte_depot">Versement / Retrait</a></li>
    <li><a href="depotParametre">Modifier paramètres</a></li>
</ul>

<h2>Retirer de l'argent</h2>
<form action="depotOperation" method="post">
    <input type="hidden" name="action" value="retrait"/>
    <label>Compte ID: <input type="number" name="compteId" required></label>
    <label>Montant: <input type="number" name="montant" step="0.01" required></label>
    <button type="submit">Retirer</button>
</form>

<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
<c:if test="${not empty result}">
    <div style="color:green;">${result}</div>
</c:if>
</body>
</html>