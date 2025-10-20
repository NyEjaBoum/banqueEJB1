<!-- filepath: centralisateur/src/main/webapp/WEB-INF/views/login.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Centralisateur</title>
</head>
<body>
    <h2>Connexion</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div style="color:red;"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="login">
        <label>Email:</label>
        <input type="email" name="email" required><br>
        <label>Mot de passe:</label>
        <input type="password" name="motDePasse" required><br>
        <button type="submit">Se connecter</button>
    </form>
</body>
</html>