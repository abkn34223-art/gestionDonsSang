package ma.ensa.controller;

import ma.ensa.model.Role;
import ma.ensa.model.Utilisateur;
import ma.ensa.model.Donneur;
import ma.ensa.service.DonneurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/donneurs")
public class DonneurServlet extends HttpServlet {
    private DonneurService donneurService = new DonneurService();
    private boolean estAdmin(HttpServletRequest request) {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        return utilisateur != null && utilisateur.getRole() == Role.ADMIN;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!estAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("modifier".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Donneur donneur = donneurService.chercherDonneur(id);

                if (donneur == null) {response.sendRedirect(request.getContextPath() + "/donneurs");
                    return;
                }

                request.setAttribute("donneur", donneur);
                request.getRequestDispatcher("/modifier-donneur.jsp").forward(request, response);

            } catch (NumberFormatException e) {response.sendRedirect(request.getContextPath() + "/donneurs");
            }

        } else {
            request.setAttribute("donneurs", donneurService.listerDonneurs());
            request.getRequestDispatcher("/donneurs.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!estAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        try {Long id = Long.parseLong(request.getParameter("id"));

            donneurService.modifierDonneur(
                    id,
                    request.getParameter("nom"),
                    request.getParameter("prenom"),
                    request.getParameter("email"),
                    request.getParameter("telephone"),
                    request.getParameter("groupeSanguin"),
                    request.getParameter("adresse")
            );

            response.sendRedirect(request.getContextPath() + "/donneurs?modification=success"
            );

        } catch (Exception e) {response.sendRedirect(request.getContextPath() + "/donneurs?erreur=modification");
        }
    }
}


