<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ma.ensa.model.RendezVous" %>
<%@ page import="ma.ensa.model.StatutRendezVous" %>

<%
    List<RendezVous> rendezVousList =
        (List<RendezVous>) request.getAttribute("rendezVousList");

    String erreur = (String) request.getAttribute("erreur");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes rendez-vous</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }

        .container {
            margin-top: 40px;
        }

        .page-title {
            color: #333;
            font-weight: bold;
        }

        .table-container {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
    </style>
</head>

<body>

<div class="container">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="page-title">Mes rendez-vous</h2>

        <a href="<%= request.getContextPath() %>/rendezvous?action=ajouter"
           class="btn btn-primary">
            + Demander un rendez-vous
        </a>
    </div>

    <% if (erreur != null) { %>
        <div class="alert alert-danger">
            <%= erreur %>
        </div>
    <% } %>

    <div class="table-container">

        <% if (rendezVousList == null || rendezVousList.isEmpty()) { %>

            <div class="alert alert-info text-center mb-0">
                Vous n'avez pas encore de rendez-vous.
            </div>

        <% } else { %>

            <div class="table-responsive">
                <table class="table table-bordered table-hover align-middle">

                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Date de collecte</th>
                        <th>Lieu</th>
                        <th>Heure du rendez-vous</th>
                        <th>Date de demande</th>
                        <th>Statut</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>

                    <% for (RendezVous rdv : rendezVousList) { %>

                        <tr>
                            <td><%= rdv.getId() %></td>

                            <td>
                                <%= rdv.getCollecte() != null
                                    ? rdv.getCollecte().getDateCollecte()
                                    : "Non disponible" %>
                            </td>

                            <td>
                                <%= rdv.getCollecte() != null
                                    ? rdv.getCollecte().getLieu()
                                    : "Non disponible" %>
                            </td>

                            <td><%= rdv.getHeure() %></td>

                            <td><%= rdv.getDateDemande() %></td>

                            <td>
                                <%
                                    StatutRendezVous statut = rdv.getStatut();

                                    if (statut == StatutRendezVous.EN_ATTENTE) {
                                %>
                                    <span class="badge bg-warning text-dark">
                                        En attente
                                    </span>
                                <% } else if (statut == StatutRendezVous.CONFIRME) { %>
                                    <span class="badge bg-success">
                                        Confirmé
                                    </span>
                                <% } else if (statut == StatutRendezVous.ANNULE) { %>
                                    <span class="badge bg-danger">
                                        Annulé
                                    </span>
                                <% } else if (statut == StatutRendezVous.EFFECTUE) { %>
                                    <span class="badge bg-primary">
                                        Effectué
                                    </span>
                                <% } %>
                            </td>

                            <td>
                                <% if (statut == StatutRendezVous.EN_ATTENTE
                                      || statut == StatutRendezVous.CONFIRME) { %>

                                    <form action="<%= request.getContextPath() %>/rendezvous"
                                          method="post"
                                          onsubmit="return confirm('Voulez-vous vraiment annuler ce rendez-vous ?');">

                                        <input type="hidden" name="action" value="annuler">

                                        <input type="hidden" name="idRendezVous"
                                               value="<%= rdv.getId() %>">

                                        <button type="submit" class="btn btn-sm btn-danger">
                                            Annuler
                                        </button>
                                    </form>

                                <% } else { %>
                                    <span class="text-muted">Aucune action</span>
                                <% } %>
                            </td>

                        </tr>

                    <% } %>

                    </tbody>
                </table>
            </div>

        <% } %>

        <div class="mt-3">
            <a href="<%= request.getContextPath() %>/rendezvous?action=historique"
               class="btn btn-outline-secondary">
                Consulter mon historique
            </a>
        </div>

    </div>

</div>

</body>
</html>
