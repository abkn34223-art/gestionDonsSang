<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Don de sang</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            padding: 40px;
        }

        .container {
            width: 350px;
            margin: auto;
            padding: 25px;
            background: white;
            border-radius: 10px;
        }

        h2 {text-align: center;
            color: #c62828;
        }

        input, button {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            box-sizing: border-box;
        }

        button {background: #c62828;
            color: white;
            border: none;
            cursor: pointer;
        }

        .success {color: green;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Connexion</h2>
    <% if ("success".equals(request.getParameter("inscription"))) { %>
    <p class="success">Inscription réussie ! Connectez-vous.</p>
    <% } %>

    <form action="connexion" method="post">
        <input type="email" name="email" placeholder="Adresse email" required>
        <input type="password" name="motDePasse" placeholder="Mot de passe" required>
        <button type="submit">Se connecter</button>
    </form>

    <p style="text-align:center;">  Pas encore de compte ?<a href="inscription.jsp">S'inscrire</a></p>
</div>

</body>
</html>
