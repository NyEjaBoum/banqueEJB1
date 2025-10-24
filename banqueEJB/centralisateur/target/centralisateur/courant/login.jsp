<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Centralisateur</title>
    <style>
        body {
            background: #f3f4f6;
            font-family: Arial, sans-serif;
        }
        .login-container {
            max-width: 350px;
            margin: 80px auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 24px #e0e7ef;
            padding: 32px 28px;
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #1e40af;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #374151;
            font-weight: 500;
        }
        input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 18px;
            border: 1.5px solid #e2e8f0;
            border-radius: 6px;
            font-size: 1em;
        }
        button {
            width: 100%;
            padding: 10px 0;
            background: #1e40af;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 1em;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }
        button:hover {
            background: #2563eb;
        }
        .error {
            color: #dc2626;
            background: #fee2e2;
            border-radius: 6px;
            padding: 8px 12px;
            margin-bottom: 18px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Connexion</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post" action="login">
            <label>Email:</label>
            <input type="email" name="email" required>
            <label>Mot de passe:</label>
            <input type="password" name="motDePasse" required>
            <button type="submit">Se connecter</button>
        </form>
    </div>
</body>
</html>