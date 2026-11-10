package ma.ensa.service;

import ma.ensa.dao.DonneurDAO;
import ma.ensa.dao.UtilisateurDAO;
import ma.ensa.model.Donneur;
import ma.ensa.model.Utilisateur;
import java.util.List;

public class DonneurService {
    private DonneurDAO donneurDAO = new DonneurDAO();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public List<Donneur> listerDonneurs() {return donneurDAO.findAll();}

    public Donneur chercherDonneur(Long id) {return donneurDAO.findById(id);}

    public void modifierDonneur(
            Long id,
            String nom,
            String prenom,
            String email,
            String telephone,
            String groupeSanguin,
            String adresse) {

        Donneur donneur = donneurDAO.findById(id);
        Utilisateur utilisateur = donneur.getUtilisateur();

        if (utilisateurDAO.emailExiste(email, utilisateur.getId())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre utilisateur !");}

        if (donneur == null) {throw new RuntimeException("Donneur introuvable !");}

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);


        donneur.setTelephone(telephone);
        donneur.setGroupeSanguin(groupeSanguin);
        donneur.setAdresse(adresse);

        utilisateurDAO.update(utilisateur);
        donneurDAO.update(donneur);

    }
}
