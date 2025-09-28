<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Comptes Courants</title>
</head>
<body>
<h2>Comptes Courants</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Client</th>
        <th>Solde</th>
        <th>Date Maj</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="compte" items="${comptes}">
        <tr>
            <td>${compte.id}</td>
            <td>${compte.client.nom} ${compte.client.prenom}</td>
            <td>${compte.solde}</td>
            <td>${compte.dateMaj}</td>
            <td>
                <a href="mouvements?compteId=${compte.id}">Historique</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Ajouter un mouvement</h3>
<form action="mouvement" method="post">
    <label>Compte ID:
        <select name="compteId">
            <c:forEach var="compte" items="${comptes}">
                <option value="${compte.id}">${compte.id} - ${compte.client.nom} ${compte.client.prenom}</option>
            </c:forEach>
        </select>
    </label>
    <label>Montant: <input type="number" name="montant" step="0.01" required></label>
    <label>Type:
        <select name="type">
            <option value="ENTREE">Entrée</option>
            <option value="SORTIE">Sortie</option>
        </select>
    </label>
    <button type="submit">Valider</button>
</form>
</body>
</html>