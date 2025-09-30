<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion Compte Dépôt</title>
</head>
<body>
<h2>Créer un compte dépôt</h2>
<form action="compte_depot" method="post">
    <input type="hidden" name="action" value="creer"/>
    <label>Client ID: <input type="number" name="clientId" required></label>
    <label>Plafond Retrait: <input type="number" name="plafondRetrait" step="0.01" ></label>
    <label>Taux Intérêt: <input type="number" name="tauxInteret" step="0.01" ></label>
    <button type="submit">Créer</button>
</form>

<h2>Opérations</h2>
<ul>
    <li><a href="depotOperation">Versement / Retrait</a></li>
    <li><a href="depotParametre">Modifier paramètres</a></li>
</ul>

<h2>Consulter solde</h2>
<form action="compte_depot" method="post">
    <input type="hidden" name="action" value="solde"/>
    <label>Compte ID: <input type="number" name="compteId" required></label>
    <button type="submit">Voir solde</button>
</form>

<h2>Historique des mouvements</h2>
<form action="compte_depot" method="post">
    <input type="hidden" name="action" value="historique"/>
    <label>Compte ID: <input type="number" name="compteId" required></label>
    <button type="submit">Voir historique</button>
</form>

<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
<c:if test="${not empty result}">
    <div style="color:green;">${result}</div>
</c:if>
<c:if test="${not empty solde}">
    <div>Solde du compte : ${solde}</div>
</c:if>
<c:if test="${not empty historique}">
    <h3>Historique des mouvements</h3>
    <c:out value="${historique}" />
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Montant</th>
            <th>Type</th>
            <th>Date</th>
        </tr>
        <c:forEach var="mvt" items="${historique}">
            <tr>
                <td>${mvt.id}</td>
                <td>${mvt.montant}</td>
                <td>${mvt.type}</td>
                <td>${mvt.dateMouvement}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>