<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Demander un rendez-vous</title>
</head>
<body>

<h1>Demander un rendez-vous</h1>

<form action="${pageContext.request.contextPath}/rendezvous"
      method="post">

    <input type="hidden" name="action" value="demander">

    <label>Choisir une collecte :</label>

    <select name="collecteId" required>
        <option value="">Sélectionner une collecte</option>

        <c:forEach items="${collectes}" var="c">
            <option value="${c.id}">
                ${c.nom} - ${c.dateCollecte} - ${c.lieu}
            </option>
        </c:forEach>

    </select>

    <br><br>

    <label>Heure souhaitée :</label>
    <input type="time" name="heure" required>

    <br><br>

    <button type="submit">Demander un rendez-vous</button>

</form>

</body>
</html>