package ma.ensa.service;

import ma.ensa.dao.CollecteDAO;
import ma.ensa.model.Collecte;
import ma.ensa.model.StatutCollecte;
import ma.ensa.model.Utilisateur;
import ma.ensa.model.Role;
import java.util.List;

public class CollecteService {
    private CollecteDAO collecteDAO;
    public CollecteService(){
        this.collecteDAO = new CollecteDAO();
    }
    private void verifierAdmin(Utilisateur utilisateur) {
        if (utilisateur == null || utilisateur.getRole() != Role.ADMIN) {
            throw new SecurityException("Accès refusé : seuls les administrateurs peuvent gérer les collectes.");
        }
    }
    public void ajouterCollecte(Collecte collecte, Utilisateur utilisateur){
        verifierAdmin(utilisateur);
        if(collecte == null){
            throw new IllegalArgumentException("La collecte est obligatoire.");
        }
        if(collecte.getCapaciteMaximale()<=0){
            throw new IllegalArgumentException("La capacité doit être positive.");
        }
        if (collecte.getNom() == null || collecte.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la collecte est obligatoire.");
        }
        collecte.setStatut(StatutCollecte.PLANIFIEE);
        collecteDAO.ajouter(collecte);
    }
    public void modifierCollecte(Collecte collecte, Utilisateur utilisateur){
        verifierAdmin(utilisateur);
        if (collecte == null || collecte.getId()==null){
            throw new IllegalArgumentException("collecte invalide.");
        }
        if(collecte.getCapaciteMaximale()<=0){
            throw new IllegalArgumentException("La capacité doit être positive.");
        }
        if(collecteDAO.trouverParId(collecte.getId()) == null){
            throw new IllegalArgumentException("Collecte introuvable.");
        }
        collecteDAO.modifier(collecte);
    }
    public void supprimerCollecte(Long id, Utilisateur utilisateur){
        verifierAdmin(utilisateur);
        if(id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        if(collecteDAO.trouverParId(id) == null){
            throw new IllegalArgumentException("Collecte introuvable.");
        }
        collecteDAO.supprimer(id);
    }
    public void annulerCollecte(Long id, Utilisateur utilisateur){
        verifierAdmin(utilisateur);
        if(id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        Collecte collecte = collecteDAO.trouverParId(id);
        if (collecte == null){
            throw new IllegalArgumentException("Collecte introuvable.");
        }
        if (collecte.getStatut() != StatutCollecte.PLANIFIEE){
            throw new IllegalArgumentException("Seule une collecte planifiée peut être annulée.");
        }
        collecte.setStatut(StatutCollecte.ANNULEE);
        collecteDAO.modifier(collecte);
    }
    public void terminerCollecte(Long id, Utilisateur utilisateur){
        verifierAdmin(utilisateur);
        if (id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        Collecte collecte = collecteDAO.trouverParId(id);
        if (collecte == null){
            throw new IllegalArgumentException("Collecte introuvable.");
        }
        if (collecte.getStatut() != StatutCollecte.PLANIFIEE){
            throw new IllegalArgumentException("Seule une collecte planifiée peut être terminée.");
        }
        collecte.setStatut(StatutCollecte.TERMINEE);
        collecteDAO.modifier(collecte);
    }
    public Collecte trouverParId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        return collecteDAO.trouverParId(id);
    }
    public List<Collecte> listerCollectes(){
        return collecteDAO.lister();
        }
}