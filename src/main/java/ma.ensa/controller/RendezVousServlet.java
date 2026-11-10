
package ma.ensa.controller;

import ma.ensa.service.RendezVousService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/rendezvous")
public class RendezVousServlet extends HttpServlet {

    private RendezVousService rendezVousService;

    @Override
    public void init() {
        rendezVousService = new RendezVousService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setAttribute(
                "rendezVousList",
                rendezVousService.listerRendezVous()
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/rendezvous/liste.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            if ("confirmer".equals(action)) {
                rendezVousService.confirmerRendezVous(id);

            } else if ("annuler".equals(action)) {
                rendezVousService.annulerRendezVous(id);

            } else if ("effectuer".equals(action)) {
                rendezVousService.marquerCommeEffectue(id);
            }

            response.sendRedirect(
                    request.getContextPath() + "/rendezvous"
            );

        } catch (Exception e) {
            throw new ServletException(
                    "Erreur dans la gestion des rendez-vous.", e
            );
        }
    }
}
