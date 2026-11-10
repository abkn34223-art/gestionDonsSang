<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ma.ensa.model.Donneur" %>
<%@ page import="ma.ensa.model.Utilisateur" %>
<%@ page import="ma.ensa.model.Role" %>

<%  Utilisateur admin = (Utilisateur) session.getAttribute("utilisateur");
    if (admin == null || admin.getRole() != Role.ADMIN) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;}

    List<Donneur> donneurs = (List<Donneur>) request.getAttribute("donneurs");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des donneurs</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            padding: 30px;
        }

        h1 {
            color: #c62828;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background: #c62828;
            color: white;
        }

        a {color: #c62828;}
    </style>
</head>

<body>

<h1>Liste des donneurs</h1>
<table>
    <tr>
        <th>Actions</th>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Téléphone</th>
        <th>Groupe sanguin</th>
        <th>Adresse</th>
    </tr>

    <% if (donneurs != null && !donneurs.isEmpty()) {
        for (Donneur donneur : donneurs) {Utilisateur utilisateur = donneur.getUtilisateur();
    %>

    <tr>
        <td><a href="donneurs?action=modifier&id=<%= donneur.getId() %>">Modifier</a></td>
        <td><%= donneur.getId() %></td>
        <td><%= utilisateur.getNom() %></td>
        <td><%= utilisateur.getPrenom() %></td>
        <td><%= utilisateur.getEmail() %></td>
        <td><%= donneur.getTelephone() %></td>
        <td><%= donneur.getGroupeSanguin() %></td>
        <td><%= donneur.getAdresse() %></td>
    </tr>

    <%  }} else { %>

    <tr><td colspan="8">Aucun donneur enregistré.</td></tr>
    <% } %>

</table>
<br>
<a href="admin.jsp">Retour au tableau de bord</a>

</body>
</html>
