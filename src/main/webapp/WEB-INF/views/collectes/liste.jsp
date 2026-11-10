
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des collectes</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
        }

        h1 {
            color: #b71c1c;
        }

        .btn {
            display: inline-block;
            padding: 9px 14px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            color: white;
            cursor: pointer;
            margin: 2px;
        }

        .add { background: #198754; }
        .edit { background: #0d6efd; }
        .cancel { background: #dc3545; }
        .details { background: #6c757d; }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th, td {
            padding: 13px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background: #b71c1c;
            color: white;
        }

        tr:nth-child(even) {
            background: #f8f8f8;
        }

        .status {
            font-weight: bold;
        }

        .empty {
            text-align: center;
            padding: 25px;
            color: #777;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Gestion des collectes de sang</h1>

    <p>Consulter et gérer les collectes organisées.</p>

    <a href="${pageContext.request.contextPath}/collectes?action=ajouterForm"
       class="btn add">
        + Ajouter une collecte
    </a>

    <table>

        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Date</th>
            <th>Heure début</th>
            <th>Heure fin</th>
            <th>Lieu</th>
            <th>Capacité</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="collecte" items="${collectes}">

            <tr>

                <td>${collecte.id}</td>
                <td>${collecte.nom}</td>
                <td>${collecte.dateCollecte}</td>
                <td>${collecte.heureDebut}</td>
                <td>${collecte.heureFin}</td>
                <td>${collecte.lieu}</td>
                <td>${collecte.capaciteMaximale}</td>

                <td class="status">
                    ${collecte.statut}
                </td>

                <td>

                    <a href="${pageContext.request.contextPath}/collectes?action=modifierForm&id=${collecte.id}"
                       class="btn edit">
                        Modifier
                    </a>

                    <form action="${pageContext.request.contextPath}/collectes"
                          method="post"
                          style="display:inline;">

                        <input type="hidden"
                               name="action"
                               value="annuler">

                        <input type="hidden"
                               name="id"
                               value="${collecte.id}">

                        <button type="submit"
                                class="btn cancel"
                                onclick="return confirm('Voulez-vous annuler cette collecte ?')">
                            Annuler
                        </button>

                    </form>

                    <a href="${pageContext.request.contextPath}/rendezvous?collecteId=${collecte.id}"
                       class="btn details">
                        Voir les rendez-vous
                    </a>

                </td>

            </tr>

        </c:forEach>

        <c:if test="${empty collectes}">
            <tr>
                <td colspan="9" class="empty">
                    Aucune collecte disponible.
                </td>
            </tr>
        </c:if>

        </tbody>

    </table>

</div>

</body>
</html>
