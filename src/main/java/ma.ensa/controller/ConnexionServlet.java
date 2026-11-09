package ma.ensa.controller;
import ma.ensa.model.Utilisateur;
import ma.ensa.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
    private UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        try {
            Utilisateur utilisateur = utilisateurService.connecter(email, motDePasse);
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);

            if (utilisateur.getRole() == ma.ensa.model.Role.ADMIN) {
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/profil.jsp");
            }

        } catch (Exception e) {request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
        }
    }
}
