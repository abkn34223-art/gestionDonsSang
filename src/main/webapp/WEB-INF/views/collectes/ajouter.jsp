<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter une collecte</title>
</head>
<body>

<h1>Ajouter une collecte de sang</h1>

<form action="${pageContext.request.contextPath}/collectes"
      method="post">

    <input type="hidden" name="action" value="ajouter">

    <label>Nom de la collecte :</label>
    <input type="text" name="nom" required>
    <br><br>

    <label>Date :</label>
    <input type="date" name="dateCollecte" required>
    <br><br>

    <label>Heure de début :</label>
    <input type="time" name="heureDebut" required>
    <br><br>

    <label>Heure de fin :</label>
    <input type="time" name="heureFin" required>
    <br><br>

    <label>Lieu :</label>
    <input type="text" name="lieu" required>
    <br><br>

    <label>Description :</label>
    <textarea name="description"></textarea>
    <br><br>

    <label>Capacité maximale :</label>
    <input type="number" name="capaciteMaximale"
           min="1" required>
    <br><br>

    <button type="submit">Enregistrer</button>

</form>

</body>
</html>