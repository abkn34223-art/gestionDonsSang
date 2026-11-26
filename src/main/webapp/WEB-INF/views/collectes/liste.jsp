<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des collectes</title>

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

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
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        .btn-ajouter {
            display: inline-block;
            background: #198754;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            text-decoration: none;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 13px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background: #343a40;
            color: white;
        }

        tr:hover {
            background: #f8f9fa;
        }

        .actions {
            white-space: nowrap;
        }

        .icon-btn {
            border: none;
            background: none;
            cursor: pointer;
            font-size: 17px;
            margin: 0 6px;
            text-decoration: none;
        }

        .modifier {
            color: #0d6efd;
        }

        .annuler {
            color: #fd7e14;
        }

        .supprimer {
            color: #dc3545;
        }

        .rendezvous {
            display: inline-block;
            background: #0d6efd;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            text-decoration: none;
        }

        .message {
            padding: 12px;
            margin-bottom: 15px;
            border-radius: 5px;
            background: #e8f5e9;
            color: #256029;
        }

        .vide {
            padding: 20px;
            color: #777;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>
        <i class="fas fa-hand-holding-medical"></i>
        Liste des collectes
    </h2>

    <c:if test="${param.success == 'ajout'}">
        <div class="message">Collecte ajoutée avec succès.</div>
    </c:if>

    <c:if test="${param.success == 'modification'}">
        <div class="message">Collecte modifiée avec succès.</div>
    </c:if>

    <c:if test="${param.success == 'annulation'}">
        <div class="message">Collecte annulée avec succès.</div>
    </c:if>

    <c:if test="${param.success == 'suppression'}">
        <div class="message">Collecte supprimée avec succès.</div>
    </c:if>

    <!-- Bouton réservé à l'administrateur -->
    <c:if test="${sessionScope.utilisateur.role == 'ADMIN'}">
        <a class="btn-ajouter"
           href="${pageContext.request.contextPath}/collectes?action=ajouter">
            <i class="fas fa-plus"></i>
            Ajouter une collecte
        </a>
    </c:if>

    <table>
        <thead>
        <tr>
            <th>ID</th>
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

        <c:choose>
            <c:when test="${empty collectes}">
                <tr>
                    <td colspan="8" class="vide">
                        Aucune collecte trouvée.
                    </td>
                </tr>
            </c:when>

            <c:otherwise>
                <c:forEach var="collecte" items="${collectes}">
                    <tr>
                        <td>${collecte.id}</td>
                        <td>${collecte.dateCollecte}</td>
                        <td>${collecte.heureDebut}</td>
                        <td>${collecte.heureFin}</td>
                        <td>${collecte.lieu}</td>
                        <td>${collecte.capaciteMaximale}</td>
                        <td>${collecte.statut}</td>

                        <td class="actions">

                            <!-- Actions ADMIN -->
                            <c:if test="${sessionScope.utilisateur.role == 'ADMIN'}">

                                <!-- Modifier -->
                                <a class="icon-btn modifier"
                                   title="Modifier"
                                   href="${pageContext.request.contextPath}/collectes?action=modifier&id=${collecte.id}">
                                    <i class="fas fa-edit"></i>
                                </a>

                                <!-- Terminer -->
                                <c:if test="${collecte.statut == 'PLANIFIEE'}">
                                    <form action="${pageContext.request.contextPath}/collectes"
                                          method="post"
                                          style="display:inline;">

                                        <input type="hidden" name="action" value="terminer">
                                        <input type="hidden" name="id" value="${collecte.id}">

                                        <button type="submit"
                                                title="Marquer comme terminée"
                                                onclick="return confirm('Terminer cette collecte ?');">
                                            ✓
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Annuler -->
                                <c:if test="${collecte.statut == 'PLANIFIEE'}">
                                    <form action="${pageContext.request.contextPath}/collectes"
                                          method="post"
                                          style="display:inline;"
                                          onsubmit="return confirm('Voulez-vous annuler cette collecte ?');">

                                        <input type="hidden" name="action" value="annuler">
                                        <input type="hidden" name="id" value="${collecte.id}">

                                        <button type="submit"
                                                class="icon-btn annuler"
                                                title="Annuler">
                                            <i class="fas fa-ban"></i>
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Supprimer -->
                                <form action="${pageContext.request.contextPath}/collectes"
                                      method="post"
                                      style="display:inline;"
                                      onsubmit="return confirm('Voulez-vous vraiment supprimer cette collecte ?');">

                                    <input type="hidden" name="action" value="supprimer">
                                    <input type="hidden" name="id" value="${collecte.id}">

                                    <button type="submit"
                                            class="icon-btn supprimer"
                                            title="Supprimer">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </form>

                            </c:if>

                            <!-- Action DONNEUR -->
                            <c:if test="${sessionScope.utilisateur.role == 'DONNEUR'}">

                                <c:if test="${collecte.statut == 'PLANIFIEE'}">
                                    <a class="rendezvous"
                                       href="${pageContext.request.contextPath}/rendezvous?action=demander&idCollecte=${collecte.id}">
                                        <i class="fas fa-calendar-plus"></i>
                                        Demander un rendez-vous
                                    </a>
                                </c:if>

                            </c:if>

                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        </tbody>
    </table>

</div>

</body>
</html>