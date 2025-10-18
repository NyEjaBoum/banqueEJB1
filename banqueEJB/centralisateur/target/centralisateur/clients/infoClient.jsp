<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fiche Client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/includes/sidebar.jsp" %>
<div class="main-content">
    <h2>👤 Fiche Client</h2>
    <div>
        <strong>Nom :</strong> ${client.nom} <br>
        <strong>Prénom :</strong> ${client.prenom} <br>
        <strong>Email :</strong> ${client.email} <br>
        <strong>Téléphone :</strong> ${client.telephone} <br>
    </div>
    <h3>Comptes Courants</h3>
    <table>
        <c:forEach var="c" items="${courants}">
            <tr>
                <td>${c.id}</td>
                <td>${c.solde}</td>
                <td>${c.dateMaj}</td>
            </tr>
        </c:forEach>
    </table>
    <h3>Comptes Dépôt</h3>
    <table>
        <c:forEach var="d" items="${depots}">
            <tr>
                <td>${d.id}</td>
                <td>${d.solde}</td>
                <td>${d.tauxInteret}</td>
            </tr>
        </c:forEach>
    </table>
    <h3>Prêts</h3>
    <table>
        <c:forEach var="p" items="${prets}">
            <tr>
                <td>${p.id}</td>
                <td>${p.montant}</td>
                <td>${p.tauxInteret}</td>
                <td>${p.etat}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>