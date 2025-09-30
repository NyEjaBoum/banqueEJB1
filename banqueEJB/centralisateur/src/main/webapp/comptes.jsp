<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.comptecourant.entity.CompteCourant,com.comptecourant.entity.MouvementCourant,java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion Compte Courant</title>
</head>
<body>
<h2>Créer un compte courant</h2>
<form action="comptes" method="post">
    <label>Client ID: <input type="number" name="clientId" required></label>
    <button type="submit" name="action" value="creer">Créer</button>
</form>

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
                <form action="comptes" method="get" style="display:inline;">
                    <input type="hidden" name="compteId" value="${compte.id}" />
                    <button type="submit" name="action" value="solde">Voir solde</button>
                </form>
                <form action="mouvements" method="get" style="display:inline;">
                    <input type="hidden" name="compteId" value="${compte.id}" />
                    <button type="submit">Historique</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Ajouter un mouvement courant</h3>
<form action="compte_courant" method="post">
    <input type="hidden" name="action" value="mouvement"/>
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
            <c:forEach var="type" items="${typesMouvement}">
                <option value="${type.id}">${type.libelle}</option>
            </c:forEach>
        </select>

    </label>
    <button type="submit">Valider</button>
</form>

<c:if test="${not empty solde}">
    <h3>Solde du compte sélectionné : ${solde}</h3>
</c:if>

<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
</body>
</html>