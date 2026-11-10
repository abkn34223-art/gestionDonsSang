<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier une collecte</title>
</head>
<body>

<h1>Modifier la collecte</h1>

<form action="${pageContext.request.contextPath}/collectes"
      method="post">

    <input type="hidden" name="action" value="modifier">

    <input type="hidden" name="id" value="${collecte.id}">

    <label>Nom :</label>
    <input type="text" name="nom"
           value="${collecte.nom}" required>

    <label>Date :</label>
    <input type="date" name="dateCollecte"
           value="${collecte.dateCollecte}" required>

    <label>Lieu :</label>
    <input type="text" name="lieu"
           value="${collecte.lieu}" required>

    <label>Capacité maximale :</label>
    <input type="number" name="capaciteMaximale"
           value="${collecte.capaciteMaximale}" min="1" required>

    <button type="submit">Enregistrer les modifications</button>

</form>

</body>
</html>