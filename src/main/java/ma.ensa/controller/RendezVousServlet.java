package ma.ensa.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.ensa.model.*;
import ma.ensa.service.RendezVousService;
import ma.ensa.service.CollecteService;
import java.time.LocalDateTime;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/rendezvous")
public class RendezVousServlet extends HttpServlet {

    private RendezVousService rendezVousService;
    private CollecteService collecteService;

    @Override
    public void init() {
        rendezVousService = new RendezVousService();
        collecteService = new CollecteService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Vérifier la connexion
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String action = request.getParameter("action");

        try {

            // ADMIN : consulter tous les rendez-vous
            if (utilisateur.getRole() == Role.ADMIN) {
                List<RendezVous> liste;
                if ("parDonneur".equals(action)) {
                    Long idDonneur = Long.parseLong(
                            request.getParameter("idDonneur")
                    );
                    liste = rendezVousService.listerParDonneur(idDonneur);

                } else {
                    liste = rendezVousService.listerRendezVous();
                }

                request.setAttribute("rendezVousList", liste);

                request.getRequestDispatcher(
                        "/WEB-INF/views/rendezvous/liste.jsp"
                ).forward(request, response);
            }

            // DONNEUR : consulter ses rendez-vous
            else if (utilisateur.getRole() == Role.DONNEUR) {

                Donneur donneur = (Donneur) session.getAttribute("donneur");
                // Afficher ses rendez-vous
                if (action == null || "mesRendezVous".equals(action)
                        || "historique".equals(action)) {
                    List<RendezVous> liste = rendezVousService.listerParDonneur(donneur.getId());
                    request.setAttribute("rendezVousList", liste);
                    String vue = "historique".equals(action)
                            ? "historique.jsp"
                            : "mesRendezVous.jsp";

                    request.getRequestDispatcher(
                            "/WEB-INF/views/rendezvous/" + vue
                    ).forward(request, response);

                    return;
                }

                // Ajouter un rendez-vous : afficher les collectes
                if ("ajouter".equals(action)) {

                    List<Collecte> collectes = collecteService.listerCollectes();
                    request.setAttribute("collectes", collectes);
                    request.getRequestDispatcher("/WEB-INF/views/rendezvous/demander.jsp"
                    ).forward(request, response);
                    return;
                }
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Vérifier la connexion
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String action = request.getParameter("action");
        try {
            // DONNEUR : demander un rendez-vous
            if ("demander".equals(action) && utilisateur.getRole() == Role.DONNEUR) {
                Donneur donneur = (Donneur) session.getAttribute("donneur");
                Long idCollecte = Long.parseLong(request.getParameter("idCollecte"));
                RendezVous rendezVous = new RendezVous();
                // Associer la collecte par son identifiant
                Collecte collecte = new Collecte();
                collecte.setId(idCollecte);
                rendezVous.setCollecte(collecte);
                rendezVous.setDonneur(donneur);
                rendezVous.setHeure(LocalTime.parse(request.getParameter("heure")));
                rendezVous.setDateDemande(LocalDateTime.now());
                // Le service effectue les vérifications métier
                rendezVousService.demanderRendezVous(rendezVous);
                response.sendRedirect(request.getContextPath() + "/rendezvous?action=mesRendezVous");
                return;
            }
            // ANNULER : ADMIN ou propriétaire du rendez-vous
            if ("annuler".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                RendezVous rendezVous = rendezVousService.trouverParId(id);
                if (rendezVous == null) {
                    throw new IllegalArgumentException("Rendez-vous introuvable.");
                }
                // Vérifier les droits du donneur
                if (utilisateur.getRole() != Role.ADMIN) {
                    Donneur donneur = (Donneur) session.getAttribute("donneur");
                    if (utilisateur.getRole() != Role.DONNEUR
                            || donneur == null
                            || !donneur.getId().equals(rendezVous.getDonneur().getId())) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
                rendezVousService.annulerRendezVous(id);
            }
            // AUTRES ACTIONS : ADMIN uniquement
            else if (utilisateur.getRole() == Role.ADMIN) {
                Long id = Long.parseLong(request.getParameter("id"));
                if ("confirmer".equals(action)) {
                    rendezVousService.confirmerRendezVous(id);
                } else if ("effectuer".equals(action)) {
                    rendezVousService.marquerCommeEffectue(id);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            // Redirection après l'opération
            String destination = utilisateur.getRole() == Role.ADMIN
                    ? "/rendezvous"
                    : "/rendezvous?action=mesRendezVous";
            response.sendRedirect(request.getContextPath() + destination);
        } catch (NumberFormatException e) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            if (utilisateur.getRole() == Role.ADMIN) {
                request.setAttribute(
                        "rendezVousList",
                        rendezVousService.listerRendezVous()
                );
                request.getRequestDispatcher("/WEB-INF/views/rendezvous/liste.jsp"
                ).forward(request, response);

            } else {

                Donneur donneur = (Donneur) session.getAttribute("donneur");

                if (donneur == null || donneur.getId() == null) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                request.setAttribute(
                        "rendezVousList",
                        rendezVousService.listerParDonneur(donneur.getId())
                );
                request.getRequestDispatcher("/WEB-INF/views/rendezvous/liste.jsp"
                ).forward(request, response);
            }
        }
    }
}
