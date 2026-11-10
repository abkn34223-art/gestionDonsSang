
package ma.ensa.controller;

import ma.ensa.model.Collecte;
import ma.ensa.service.CollecteService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/collectes")
public class CollecteServlet extends HttpServlet {

    private CollecteService collecteService;

    @Override
    public void init() {
        collecteService = new CollecteService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("ajouterForm".equals(action)) {
            request.getRequestDispatcher(
                    "/WEB-INF/views/collectes/ajouter.jsp"
            ).forward(request, response);

        } else if ("modifierForm".equals(action)) {

            Long id = Long.parseLong(request.getParameter("id"));
            Collecte collecte = collecteService.trouverParId(id);

            request.setAttribute("collecte", collecte);

            request.getRequestDispatcher(
                    "/WEB-INF/views/collectes/modifier.jsp"
            ).forward(request, response);

        } else {
            request.setAttribute(
                    "collectes",
                    collecteService.listerCollectes()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/collectes/liste.jsp"
            ).forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            if ("ajouter".equals(action)) {

                Collecte collecte = new Collecte();

                collecte.setNom(request.getParameter("nom"));
                collecte.setLieu(request.getParameter("lieu"));
                collecte.setDescription(
                        request.getParameter("description")
                );

                collecte.setDateCollecte(
                        java.time.LocalDate.parse(
                                request.getParameter("dateCollecte")
                        )
                );

                collecte.setHeureDebut(
                        java.time.LocalTime.parse(
                                request.getParameter("heureDebut")
                        )
                );

                collecte.setHeureFin(
                        java.time.LocalTime.parse(
                                request.getParameter("heureFin")
                        )
                );

                collecte.setCapaciteMaximale(
                        Integer.parseInt(
                                request.getParameter("capaciteMaximale")
                        )
                );

                collecteService.ajouterCollecte(collecte);

            } else if ("modifier".equals(action)) {

                Long id = Long.parseLong(request.getParameter("id"));

                Collecte collecte = collecteService.trouverParId(id);

                if (collecte == null) {
                    throw new IllegalArgumentException(
                            "Collecte introuvable."
                    );
                }

                collecte.setNom(request.getParameter("nom"));
                collecte.setLieu(request.getParameter("lieu"));
                collecte.setDescription(
                        request.getParameter("description")
                );

                collecte.setDateCollecte(
                        java.time.LocalDate.parse(
                                request.getParameter("dateCollecte")
                        )
                );

                collecte.setHeureDebut(
                        java.time.LocalTime.parse(
                                request.getParameter("heureDebut")
                        )
                );

                collecte.setHeureFin(
                        java.time.LocalTime.parse(
                                request.getParameter("heureFin")
                        )
                );

                collecte.setCapaciteMaximale(
                        Integer.parseInt(
                                request.getParameter("capaciteMaximale")
                        )
                );

                collecteService.modifierCollecte(collecte);

            } else if ("annuler".equals(action)) {

                Long id = Long.parseLong(request.getParameter("id"));
                collecteService.annulerCollecte(id);
            }

            response.sendRedirect(
                    request.getContextPath() + "/collectes"
            );

        } catch (Exception e) {
            throw new ServletException(
                    "Erreur dans la gestion des collectes.", e
            );
        }
    }
}
