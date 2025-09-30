<!-- filepath: centralisateur/src/main/webapp/pret/prets.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Prêts</title>
</head>
<body>
<h2>Liste des prêts</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Client</th>
        <th>Type</th>
        <th>Montant</th>
        <th>Taux</th>
        <th>Date début</th>
        <th>Date fin</th>
        <th>Etat</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="pret" items="${prets}">
        <tr>
                <td>${pret.id}</td>
                <td>${pret.clientId}</td>
                <td>
                    <c:forEach var="type" items="${typesPret}">
                        <c:if test="${type.id == pret.typePretId}">
                            ${type.libelle}
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="type" items="${typesPret}">
                        <c:if test="${type.id == pret.typePretId}">
                            ${type.montant}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${pret.tauxInteret}</td>
                <td>${pret.dateDebut}</td>
                <td>${pret.dateFin}</td>
                <td>${pret.etat}</td>
            <td>
                <form action="pret" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="historique"/>
                    <input type="hidden" name="pretId" value="${pret.id}"/>
                    <button type="submit">Historique</button>
                </form>
                <form action="pret" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="rembourser"/>
                    <input type="hidden" name="pretId" value="${pret.id}"/>
                    <input type="number" name="montant" step="0.01" placeholder="Montant" required/>
                    <input type="number" name="interetPayes" step="0.01" placeholder="Intérêts payés" required/>
                    <input type="number" name="capitalRembourse" step="0.01" placeholder="Capital remboursé" required/>
                    <button type="submit">Rembourser</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Créer un nouveau prêt</h3>
<form action="pret" method="post">
    <input type="hidden" name="action" value="creer"/>
    <label>Client ID: <input type="number" name="clientId" required></label>
    <label>Type Prêt ID: <input type="number" name="typePretId" required></label>
    <label>Date Début: <input type="date" name="dateDebut" required></label>
    <label>Date Fin: <input type="date" name="dateFin" required></label>
    <button type="submit">Créer</button>
</form>

<c:if test="${not empty erreur}">
    <div style="color:red;">${erreur}</div>
</c:if>
</body>
</html>