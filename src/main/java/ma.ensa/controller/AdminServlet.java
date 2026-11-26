package ma.ensa.controller;
import ma.ensa.model.Role;
import ma.ensa.model.Utilisateur;
import ma.ensa.model.StatutCollecte;
import ma.ensa.model.StatutRendezVous;
import ma.ensa.dao.UtilisateurDAO;
import ma.ensa.dao.DonneurDAO;
import ma.ensa.service.CollecteService;
import ma.ensa.service.RendezVousService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private UtilisateurDAO utilisateurDAO;
    private DonneurDAO donneurDAO;
    private CollecteService collecteService;
    private RendezVousService rendezVousService;

    @Override
    public void init() {
        utilisateurDAO = new UtilisateurDAO();
        donneurDAO = new DonneurDAO();
        collecteService = new CollecteService();
        rendezVousService = new RendezVousService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur.getRole() != Role.ADMIN) {response.sendRedirect(request.getContextPath() + "/profil");
            return;}

        // Récupération des données
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
        List<ma.ensa.model.Donneur> donneurs = donneurDAO.findAll();
        List<ma.ensa.model.Collecte> collectes = collecteService.listerCollectes();
        List<ma.ensa.model.RendezVous> rendezVous = rendezVousService.listerRendezVous();
        // Nombre total
        request.setAttribute("nombreUtilisateurs", utilisateurs.size());
        request.setAttribute("nombreDonneurs", donneurs.size());
        request.setAttribute("nombreCollectes", collectes.size());
        request.setAttribute("nombreRendezVous", rendezVous.size());
        // Statistiques par statut
        long collectesPlanifiees = collectes.stream()
                .filter(c -> c.getStatut() == StatutCollecte.PLANIFIEE).count();
        long rendezVousEnAttente = rendezVous.stream()
                .filter(r -> r.getStatut() == StatutRendezVous.EN_ATTENTE).count();
        long rendezVousConfirmes = rendezVous.stream()
                .filter(r -> r.getStatut() == StatutRendezVous.CONFIRME).count();

        request.setAttribute("collectesPlanifiees", collectesPlanifiees);
        request.setAttribute("rendezVousEnAttente", rendezVousEnAttente);
        request.setAttribute("rendezVousConfirmes", rendezVousConfirmes);
        request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
    }
}