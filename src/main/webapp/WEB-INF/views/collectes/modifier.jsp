<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier une collecte</title>
</head>
<body>

<h2>Modifier une collecte</h2>

<form action="${pageContext.request.contextPath}/collectes"
      method="post">

    <input type="hidden" name="action" value="modifier">
    <input type="hidden" name="id" value="${collecte.id}">

    <label>Nom :</label>
    <input type="text" name="nom"
           value="${collecte.nom}" required>
    <br><br>

    <label>Date :</label>
    <input type="date" name="dateCollecte"
           value="${collecte.dateCollecte}" required>
    <br><br>

    <label>Heure début :</label>
    <input type="time" name="heureDebut"
           value="${collecte.heureDebut}" required>
    <br><br>

    <label>Heure fin :</label>
    <input type="time" name="heureFin"
           value="${collecte.heureFin}" required>
    <br><br>

    <label>Lieu :</label>
    <input type="text" name="lieu"
           value="${collecte.lieu}" required>
    <br><br>

    <label>Capacité :</label>
    <input type="number" name="capaciteMaximale"
           value="${collecte.capaciteMaximale}"
           min="1" required>
    <br><br>

    <label>Description :</label>
    <textarea name="description">${collecte.description}</textarea>
    <br><br>

    <button type="submit">Enregistrer les modifications</button>

</form>

</body>
</html>
