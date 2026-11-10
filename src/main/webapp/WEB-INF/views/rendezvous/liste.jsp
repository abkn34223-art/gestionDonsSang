
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des rendez-vous</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f5f6fa;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        h1 {
            color: #8b1e2d;
            margin-bottom: 10px;
        }

        .description {
            color: #666;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 13px 10px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }

        th {
            background-color: #8b1e2d;
            color: white;
        }

        tr:hover {
            background-color: #fafafa;
        }

        .statut {
            padding: 6px 10px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
            display: inline-block;
        }

        .attente {
            background: #fff3cd;
            color: #856404;
        }

        .confirme {
            background: #d4edda;
            color: #155724;
        }

        .annule {
            background: #f8d7da;
            color: #721c24;
        }

        .effectue {
            background: #d1ecf1;
            color: #0c5460;
        }

        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 6px;
        }

        button {
            border: none;
            padding: 8px 10px;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            font-size: 12px;
        }

        .btn-confirmer {
            background: #28a745;
        }

        .btn-annuler {
            background: #dc3545;
        }

        .btn-effectue {
            background: #007bff;
        }

        .message-vide {
            text-align: center;
            padding: 25px;
            color: #777;
        }

        @media (max-width: 900px) {
            body {
                padding: 10px;
            }

            .container {
                overflow-x: auto;
            }

            table {
                min-width: 900px;
            }
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Gestion des rendez-vous</h1>

    <p class="description">
        Consultez et gérez les rendez-vous des donneurs.
    </p>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Donneur</th>
            <th>Collecte</th>
            <th>Date de demande</th>
            <th>Date de collecte</th>
            <th>Heure</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="rendezVous" items="${rendezVousList}">

            <tr>
                <td>${rendezVous.id}</td>

                <td>${rendezVous.donneur.nom}
                    ${rendezVous.donneur.prenom}</td>

                <td>${rendezVous.collecte.nom}</td>

                <td>${rendezVous.dateDemande}</td>

                <td>${rendezVous.collecte.dateCollecte}</td>

                <td>${rendezVous.heure}</td>

                <td>
                    <c:choose>
                        <c:when test="${rendezVous.statut == 'EN_ATTENTE'}">
                            <span class="statut attente">En attente</span>
                        </c:when>

                        <c:when test="${rendezVous.statut == 'CONFIRME'}">
                            <span class="statut confirme">Confirmé</span>
                        </c:when>

                        <c:when test="${rendezVous.statut == 'ANNULE'}">
                            <span class="statut annule">Annulé</span>
                        </c:when>

                        <c:when test="${rendezVous.statut == 'EFFECTUE'}">
                            <span class="statut effectue">Effectué</span>
                        </c:when>
                    </c:choose>
                </td>

                <td>
                    <div class="actions">

                        <c:if test="${rendezVous.statut == 'EN_ATTENTE'}">

                            <form action="${pageContext.request.contextPath}/rendezvous"
                                  method="post">

                                <input type="hidden" name="action"
                                       value="confirmer">

                                <input type="hidden" name="id"
                                       value="${rendezVous.id}">

                                <button type="submit" class="btn-confirmer">
                                    Confirmer
                                </button>
                            </form>

                        </c:if>

                        <c:if test="${rendezVous.statut == 'EN_ATTENTE'
                                      || rendezVous.statut == 'CONFIRME'}">

                            <form action="${pageContext.request.contextPath}/rendezvous"
                                  method="post"
                                  onsubmit="return confirm('Voulez-vous vraiment annuler ce rendez-vous ?');">

                                <input type="hidden" name="action"
                                       value="annuler">

                                <input type="hidden" name="id"
                                       value="${rendezVous.id}">

                                <button type="submit" class="btn-annuler">
                                    Annuler
                                </button>
                            </form>

                        </c:if>

                        <c:if test="${rendezVous.statut == 'CONFIRME'}">

                            <form action="${pageContext.request.contextPath}/rendezvous"
                                  method="post">

                                <input type="hidden" name="action"
                                       value="effectuer">

                                <input type="hidden" name="id"
                                       value="${rendezVous.id}">

                                <button type="submit" class="btn-effectue">
                                    Effectué
                                </button>
                            </form>

                        </c:if>

                    </div>
                </td>
            </tr>

        </c:forEach>

        <c:if test="${empty rendezVousList}">
            <tr>
                <td colspan="8" class="message-vide">
                    Aucun rendez-vous n'est disponible.
                </td>
            </tr>
        </c:if>

        </tbody>
    </table>

</div>

</body>
</html>
