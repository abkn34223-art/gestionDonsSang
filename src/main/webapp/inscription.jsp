<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription - Don de sang</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            margin: 0;
            padding: 30px;
        }

        .container {
            width: 400px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
        }

        h2 {
            text-align: center;
            color: #c62828;
        }

        input, select, textarea, button {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
        }

        button {
            background: #c62828;
            color: white;
            border: none;
            cursor: pointer;
        }

        .erreur {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Créer un compte donneur</h2>

    <% if (request.getAttribute("erreur") != null) { %>
    <p class="erreur"><%= request.getAttribute("erreur") %></p>
    <% } %>

    <form action="inscription" method="post">
        <input type="text" name="nom" placeholder="Nom" required>

        <input type="text" name="prenom" placeholder="Prénom" required>

        <input type="email" name="email" placeholder="Adresse email" required>

        <input type="password" name="motDePasse" placeholder="Mot de passe" required>

        <label>Date de naissance :</label>
        <input type="date" name="dateNaissance" required>

        <input type="tel" name="telephone" placeholder="Téléphone" required>

        <label>Groupe sanguin :</label>
        <select name="groupeSanguin" required>
            <option value="">Choisir</option>
            <option value="A+">A+</option>
            <option value="A-">A-</option>
            <option value="B+">B+</option>
            <option value="B-">B-</option>
            <option value="AB+">AB+</option>
            <option value="AB-">AB-</option>
            <option value="O+">O+</option>
            <option value="O-">O-</option>
        </select>

        <textarea name="adresse" placeholder="Adresse"></textarea>
        <button type="submit">S'inscrire</button>
    </form>

    <p style="text-align:center;"> Déjà inscrit ?  <a href="login.jsp">Se connecter</a></p>

</div>
</body>
</html>
