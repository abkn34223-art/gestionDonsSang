package ma.ensa.service;

import ma.ensa.dao.DonneurDAO;
import ma.ensa.dao.UtilisateurDAO;
import ma.ensa.dao.RendezVousDAO;
import ma.ensa.model.Donneur;
import ma.ensa.model.Utilisateur;
import ma.ensa.model.RendezVous;
import java.util.List;

public class DonneurService {
    private DonneurDAO donneurDAO = new DonneurDAO();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private RendezVousDAO rendezVousDAO = new RendezVousDAO();
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

        if (donneur == null) {throw new RuntimeException("Donneur introuvable !");}
        Utilisateur utilisateur = donneur.getUtilisateur();
        if (utilisateurDAO.emailExiste(email, utilisateur.getId())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre utilisateur !");}

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        donneur.setTelephone(telephone);
        donneur.setGroupeSanguin(groupeSanguin);
        donneur.setAdresse(adresse);
        utilisateurDAO.update(utilisateur);
        donneurDAO.update(donneur);
    }

    public boolean possedeDesRendezVous(Long donneurId) {
        List<RendezVous> rendezVous = rendezVousDAO.listerParDonneur(donneurId);
        return !rendezVous.isEmpty();
    }

    public void supprimerDonneur(Long id) {
        Donneur donneur = donneurDAO.findById(id);
        if (donneur == null) {throw new RuntimeException("Donneur n'existe pas !");}
        // Vérifier qu'il ne possède aucun rendez-vous
        if (possedeDesRendezVous(id)) {
            throw new RuntimeException("Impossible de supprimer ce donneur : il possède des rendez-vous !");
        }
        //  Supprimer le donneur et son compte utilisateur
        donneurDAO.supprimerDonneurEtUtilisateur(id);
    }
}