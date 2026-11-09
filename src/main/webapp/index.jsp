<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des dons de sang</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f5f5f5;
            text-align: center;
        }

        header {
            background-color: #b71c1c;
            color: white;
            padding: 25px;
        }

        .container {
            margin-top: 100px;
        }

        h1 {
            color: #b71c1c;
        }

        .btn {
            display: inline-block;
            background-color: #b71c1c;
            color: white;
            padding: 12px 25px;
            margin: 10px;
            text-decoration: none;
            border-radius: 5px;
        }

        .btn:hover {
            background-color: #8e0000;
        }
    </style>
</head>

<body>
<header><h2>Gestion des dons de sang</h2></header>

<div class="container">
    <h1>Bienvenue sur notre plateforme</h1>

    <p>Ensemble, sauvons des vies grâce au don de sang.</p>

    <a href="login.jsp" class="btn">Se connecter</a>

    <a href="inscription.jsp" class="btn">Créer un compte</a>
</div>

</body>
</html>

