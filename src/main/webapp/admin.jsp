<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ma.ensa.model.Utilisateur" %>

<%  Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur == null || !utilisateur.getRole().name().equals("ADMIN")) {
        response.sendRedirect("login.jsp");
        return;}
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Administration - Don de sang</title>
</head>
<body>

<h1>Espace administrateur</h1>
<p>Bienvenue <%= utilisateur.getPrenom() %> !</p>
<p><a href="donneurs">Gérer les donneurs</a></p>
<h2>Gestion des dons de sang</h2>
<p>Le tableau de bord administrateur sera développé prochainement.</p>
<a href="deconnexion">Se déconnecter</a>

</body>
</html>
