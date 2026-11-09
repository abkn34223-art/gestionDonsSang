package ma.ensa.service;

import ma.ensa.dao.UtilisateurDAO;
import ma.ensa.dao.DonneurDAO;
import ma.ensa.model.Utilisateur;
import ma.ensa.model.Donneur;
import ma.ensa.model.Role;

import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDate;

public class UtilisateurService {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private DonneurDAO donneurDAO = new DonneurDAO();

    public void inscrire(
            String nom,
            String prenom,
            String email,
            String motDePasse,
            LocalDate dateNaissance,
            String telephone,
            String groupeSanguin,
            String adresse) {

        // Vérifier si l'email existe déjà
        if (utilisateurDAO.findByEmail(email) != null) {
            throw new RuntimeException("Cette adresse email existe déjà !");}

        // Créer l'utilisateur
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);

        // Sécuriser le mot de passe
        utilisateur.setMotDePasse(
                BCrypt.hashpw(motDePasse, BCrypt.gensalt()));

        utilisateur.setRole(Role.DONNEUR);
        // Enregistrer l'utilisateur
        utilisateurDAO.save(utilisateur);

        // Créer le donneur
        Donneur donneur = new Donneur();
        donneur.setDateNaissance(dateNaissance);
        donneur.setTelephone(telephone);
        donneur.setGroupeSanguin(groupeSanguin);
        donneur.setAdresse(adresse);
        donneur.setUtilisateur(utilisateur);

        // Enregistrer le donneur
        donneurDAO.save(donneur);
    }

    public Utilisateur connecter(String email, String motDePasse) {
        // Rechercher l'utilisateur par email
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email);
        // Vérifier si le compte existe
        if (utilisateur == null) {throw new RuntimeException("Email ou mot de passe incorrect !");}
        // Vérifier le mot de passe
        if (!BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect !");}
        return utilisateur;
    }

}
