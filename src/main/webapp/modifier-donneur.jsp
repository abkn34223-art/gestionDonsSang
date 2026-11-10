<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ma.ensa.model.Donneur" %>
<%@ page import="ma.ensa.model.Utilisateur" %>

<%  Donneur donneur = (Donneur) request.getAttribute("donneur");
    if (donneur == null) {
        response.sendRedirect(request.getContextPath() + "/donneurs");
        return;
    }
    Utilisateur utilisateur = donneur.getUtilisateur();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un donneur</title>
</head>
<body>

<h1>Modifier un donneur</h1>
<form action="<%= request.getContextPath() %>/donneurs" method="post">

    <input type="hidden" name="id" value="<%= donneur.getId() %>">
    <label>Nom :</label>
    <input type="text" name="nom" value="<%= utilisateur.getNom() %>" required>
    <br><br>

    <label>Prénom :</label>
    <input type="text" name="prenom" value="<%= utilisateur.getPrenom() %>" required>
    <br><br>

    <label>Email :</label>
    <input type="email" name="email" value="<%= utilisateur.getEmail() %>" required>
    <br><br>

    <label>Téléphone :</label>
    <input type="tel" name="telephone" value="<%= donneur.getTelephone() %>" required>
    <br><br>

    <label>Groupe sanguin :</label>
    <select name="groupeSanguin" required>
        <option value="A+" <%= "A+".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>A+</option>
        <option value="A-" <%= "A-".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>A-</option>
        <option value="B+" <%= "B+".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>B+</option>
        <option value="B-" <%= "B-".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>B-</option>
        <option value="AB+" <%= "AB+".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>AB+</option>
        <option value="AB-" <%= "AB-".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>AB-</option>
        <option value="O+" <%= "O+".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>O+</option>
        <option value="O-" <%= "O-".equals(donneur.getGroupeSanguin()) ? "selected" : "" %>>O-</option>
    </select>
    <br><br>

    <label>Adresse :</label>
    <input type="text" name="adresse"
           value="<%= donneur.getAdresse() == null ? "" : donneur.getAdresse() %>">
    <br><br>

    <button type="submit">Enregistrer les modifications</button>
</form>
<br>
<a href="<%= request.getContextPath() %>/donneurs">Retour à la liste</a>

</body>
</html>
