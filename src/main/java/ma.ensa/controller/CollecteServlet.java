package ma.ensa.controller;

import ma.ensa.model.Collecte;
import ma.ensa.service.CollecteService;
import ma.ensa.model.Utilisateur;
import ma.ensa.model.Role;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/collectes")
public class CollecteServlet extends HttpServlet{
    private CollecteService collecteService;

    @Override
    public void init(){
        collecteService = new CollecteService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = null;
        if (session != null) {
            utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        }
        if (action == null || action.equals("liste")) {
            request.setAttribute("collectes", collecteService.listerCollectes());
            request.getRequestDispatcher("/WEB-INF/views/collectes/liste.jsp"
            ).forward(request, response);
        }else if (action.equals("ajouter")) {
            if (utilisateur == null || utilisateur.getRole() != Role.ADMIN) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/collectes/ajouter.jsp"
            ).forward(request, response);
        }
        else if (action.equals("modifier")) {
            if (utilisateur == null || utilisateur.getRole() != Role.ADMIN) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Long id = Long.parseLong(request.getParameter("id"));
            Collecte collecte = collecteService.trouverParId(id);
            request.setAttribute("collecte", collecte);
            request.getRequestDispatcher("/WEB-INF/views/collectes/modifier.jsp"
            ).forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = null;
        if (session != null) {
            utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        }
        try {
            if ("ajouter".equals(action)) {
                Collecte collecte = new Collecte();
                collecte.setNom(request.getParameter("nom"));
                collecte.setDateCollecte(LocalDate.parse(request.getParameter("dateCollecte")));
                collecte.setHeureDebut(LocalTime.parse(request.getParameter("heureDebut")));
                collecte.setHeureFin(LocalTime.parse(request.getParameter("heureFin")));
                collecte.setLieu(request.getParameter("lieu"));
                collecte.setCapaciteMaximale(Integer.parseInt(request.getParameter("capaciteMaximale")));
                collecte.setDescription(request.getParameter("description"));
                collecteService.ajouterCollecte(collecte, utilisateur);
            } else if ("modifier".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                Collecte collecte = collecteService.trouverParId(id);
                if (collecte == null) {
                    throw new IllegalArgumentException("Collecte introuvable.");
                }
                collecte.setNom(request.getParameter("nom"));
                collecte.setDateCollecte(LocalDate.parse(request.getParameter("dateCollecte")));
                collecte.setHeureDebut(LocalTime.parse(request.getParameter("heureDebut")));
                collecte.setHeureFin(LocalTime.parse(request.getParameter("heureFin")));
                collecte.setLieu(request.getParameter("lieu"));
                collecte.setCapaciteMaximale(Integer.parseInt(request.getParameter("capaciteMaximale")));
                collecte.setDescription(request.getParameter("description"));
                collecteService.modifierCollecte(collecte,utilisateur);
            } else if ("supprimer".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                collecteService.supprimerCollecte(id,utilisateur);
            } else if ("annuler".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                collecteService.annulerCollecte(id,utilisateur);
            } else if ("terminer".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                collecteService.terminerCollecte(id,utilisateur);
            } else {
                throw new IllegalArgumentException("Action inconnue.");
            }
            // Redirection après une opération réussie
            response.sendRedirect(request.getContextPath() + "/collectes?action=liste");
        }catch (SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.setAttribute("collectes", collecteService.listerCollectes());
            request.getRequestDispatcher("/WEB-INF/views/collectes/liste.jsp"
            ).forward(request, response);
        }
    }
}