<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Validation des mouvements</title>
</head>
<body>
    <h2>Mouvements en attente de validation</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Compte</th>
            <th>Montant</th>
            <th>Type</th>
            <th>Date</th>
            <th>Action</th>
        </tr>
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
                            <button type="submit">Valider</button>
                        </form>
                        <form action="updateMouvement" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${mvt.id}" />
                            <input type="hidden" name="statut" value="2" />
                            <button type="submit">Refuser</button>
                        </form>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</body>
</html>