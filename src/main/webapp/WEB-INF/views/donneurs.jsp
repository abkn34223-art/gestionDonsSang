<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ma.ensa.model.Donneur" %>
<%@ page import="ma.ensa.model.Utilisateur" %>
<%@ page import="ma.ensa.model.Role" %>

<%  List<Donneur> donneurs = (List<Donneur>) request.getAttribute("donneurs"); %>

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
<% String suppression = request.getParameter("suppression");
    if ("success".equals(suppression)) { %>
<p style="color: green;">Donneur supprimé avec succès !</p>
<% }
    else if ("error".equals(suppression)) { %>
<p style="color: red;">Impossible de supprimer ce donneur. Il possède peut-être des rendez-vous.</p>
<%  }  %>

<%  String modification = request.getParameter("modification");
    String erreur = request.getParameter("erreur");
    if ("success".equals(modification)) {
%>
<p style="color: green;">Donneur modifié avec succès !</p>
<%
} else if ("modification".equals(erreur)) {
%>
<p style="color: red;">Erreur lors de la modification du donneur.</p>
<%  }    %>

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
        <a href="<%= request.getContextPath() %>/donneurs?action=modifier&id=<%= donneur.getId() %>">Modifier</a>

            <a href="<%= request.getContextPath() %>/donneurs?action=supprimer&id=<%= donneur.getId() %>"
               onclick="return confirm('Voulez-vous vraiment supprimer ce donneur ?');">Supprimer</a>
        </td>
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
<a href="<%= request.getContextPath() %>/admin">Retour au tableau de bord</a>

</body>
</html>
