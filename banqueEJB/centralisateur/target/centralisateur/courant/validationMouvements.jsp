<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Validation des mouvements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    <div class="main-content">
        <div class="page-header">
            <h1>✅ Validation des mouvements</h1>
            <p>Liste des mouvements en attente de validation</p>
        </div>
        <div class="card">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Compte</th>
                        <th>Montant</th>
                        <th>Type</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mvt" items="${mouvements}">
                        <c:if test="${mvt.statut == 0}">
                            <tr>
                                <td>${mvt.id}</td>
                                <td>${mvt.compte.id}</td>
                                <td>${mvt.montant}</td>
                                <td>${mvt.typeMouvementId}</td>
                                <td>${mvt.dateMouvement}</td>
                                <td>
                                    <form action="updateMouvement" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="${mvt.id}" />
                                        <input type="hidden" name="statut" value="1" />
                                        <button type="submit" class="btn btn-success">Valider</button>
                                    </form>
                                    <form action="updateMouvement" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="${mvt.id}" />
                                        <input type="hidden" name="statut" value="2" />
                                        <button type="submit" class="btn btn-danger">Refuser</button>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>