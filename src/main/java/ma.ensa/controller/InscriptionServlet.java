package ma.ensa.controller;

import ma.ensa.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/inscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {request.setCharacterEncoding("UTF-8");

        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("motDePasse");
            LocalDate dateNaissance = LocalDate.parse(request.getParameter("dateNaissance"));
            String telephone = request.getParameter("telephone");
            String groupeSanguin = request.getParameter("groupeSanguin");
            String adresse = request.getParameter("adresse");

            utilisateurService.inscrire(nom, prenom, email, motDePasse, dateNaissance,
                    telephone, groupeSanguin, adresse);

            response.sendRedirect(request.getContextPath() + "/login.jsp?inscription=success");

        } catch (Exception e) {request.setAttribute("erreur", e.getMessage());

            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
        }
    }
}