<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un client</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/sidebar.jsp" %>
    
    <div class="main-content">
        <div class="page-header">
            <h2>Ajouter un client</h2>
        </div>
        
        <div class="card">
            <form action="clients" method="post">
                <input type="hidden" name="action" value="ajouter"/>
                
                <div class="form-row">
                    <label>
                        Nom
                        <input type="text" name="nom" required>
                    </label>
                    
                    <label>
                        Prénom
                        <input type="text" name="prenom" required>
                    </label>
                </div>
                
                <div class="form-row">
                    <label>
                        Email
                        <input type="email" name="email" placeholder="exemple@email.com">
                    </label>
                    
                    <label>
                        Téléphone
                        <input type="text" name="telephone" placeholder="+261 XX XX XXX XX">
                    </label>
                </div>
                
                <label>
                    Numéro client
                    <input type="number" name="numeroClient" required>
                </label>
                
                <button type="submit" class="btn">Ajouter le client</button>
            </form>
        </div>

        <!-- Messages d'état -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">${erreur}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
    </div>
</body>
</html>