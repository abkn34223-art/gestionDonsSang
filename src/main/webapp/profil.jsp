<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ma.ensa.model.Utilisateur" %>

<% Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur == null) {response.sendRedirect("login.jsp");
        return;}
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mon profil - Don de sang</title>
</head>
<body>

<h1>Bienvenue <%= utilisateur.getPrenom() %> !</h1>
<h2>Mon profil</h2>
<p>Nom : <%= utilisateur.getNom() %></p>
<p>Prénom : <%= utilisateur.getPrenom() %></p>
<p>Email : <%= utilisateur.getEmail() %></p>
<p>Rôle : <%= utilisateur.getRole() %></p>

<a href="deconnexion">Se déconnecter</a>

</body>
</html>
