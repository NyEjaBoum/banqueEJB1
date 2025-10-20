<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Fiche Client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .main-content {
            max-width: 650px;
            margin: 40px auto;
            background: #fff;
            border-radius: 16px;
            padding: 35px 40px;
            box-shadow: 0 4px 24px #e0e7ef;
        }
        .client-header {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-bottom: 25px;
        }
        .client-header i {
            font-size: 2.2rem;
            color: #1e40af;
        }
        .client-info {
            margin-bottom: 30px;
            font-size: 1.08em;
        }
        .client-info strong {
            width: 120px;
            display: inline-block;
            color: #1e40af;
        }
        .section-title {
            margin: 30px 0 10px 0;
            font-size: 1.15em;
            color: #1e40af;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .section-title i {
            font-size: 1.2em;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
            background: #f8fafc;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 10px;
            text-align: left;
        }
        th {
            background: #f5f7fa;
            color: #1e40af;
            font-weight: 600;
            font-size: 0.95em;
        }
        tr:nth-child(even) { background: #f1f5f9; }
        .badge {
            padding: 4px 12px;
            border-radius: 8px;
            font-size: 0.95em;
            background: #e0e7ef;
            color: #1e40af;
            font-weight: 500;
        }
        @media (max-width: 800px) {
            .main-content { padding: 15px; }
        }
    </style>
</head>
<body>
<%@ include file="/includes/sidebar.jsp" %>
<div class="main-content">
    <div class="client-header">
        <i class="fa fa-user"></i>
        <h2>Fiche Client</h2>
    </div>
    <div class="client-info">
        <p><strong>Nom :</strong> ${client.nom}</p>
        <p><strong>Prénom :</strong> ${client.prenom}</p>
        <p><strong>Email :</strong> ${client.email}</p>
        <p><strong>Téléphone :</strong> ${client.telephone}</p>
    </div>

    <div>
        <div class="section-title"><i class="fa fa-credit-card"></i> Comptes Courants</div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Solde</th>
                    <th>Date Maj</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${courants}">
                    <tr>
                        <td>${c.id}</td>
                        <td><fmt:formatNumber value="${c.solde}" type="number" groupingUsed="true"/> Ar</td>
                        <td>${c.dateMaj}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div>
        <div class="section-title"><i class="fa fa-piggy-bank"></i> Comptes Dépôt</div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Solde</th>
                    <th>Taux Intérêt (%)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="d" items="${depots}">
                    <tr>
                        <td>${d.id}</td>
                        <td><fmt:formatNumber value="${d.solde}" type="number" groupingUsed="true"/> Ar</td>
                        <td>${d.tauxInteret}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div>
        <div class="section-title"><i class="fa fa-university"></i> Prêts</div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Montant</th>
                    <th>Taux (%)</th>
                    <th> Date Debut </th>
                    <th> Date Fin </th>
                    <th>État</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${prets}">
                    <tr>
                        <td>${p.id}</td>
                        <td><fmt:formatNumber value="${p.montant}" type="number" groupingUsed="true"/> Ar</td>
                        <td>${p.tauxInteret}</td>
                        <td>${p.dateDebut} </td>
                        <td>${p.dateFin} </td>
                        <td><span class="badge">${p.etat}</span></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Ajoute la librairie FontAwesome si tu veux les icônes -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</body>
</html>