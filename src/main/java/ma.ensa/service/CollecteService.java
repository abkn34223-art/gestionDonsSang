
package ma.ensa.service;

import ma.ensa.dao.CollecteDAO;
import ma.ensa.model.Collecte;
import ma.ensa.model.StatutCollecte;

import java.util.List;

public class CollecteService {

    private final CollecteDAO collecteDAO = new CollecteDAO();

    public void ajouterCollecte(Collecte collecte) {
        if (collecte.getCapaciteMaximale() <= 0) {
            throw new IllegalArgumentException(
                    "La capacité doit être supérieure à zéro."
            );
        }

        collecte.setStatut(StatutCollecte.PLANIFIEE);
        collecteDAO.ajouter(collecte);
    }

    public List<Collecte> listerCollectes() {
        return collecteDAO.lister();
    }

    public Collecte trouverParId(Long id) {
        return collecteDAO.trouverParId(id);
    }

    public void modifierCollecte(Collecte collecte) {
        if (collecte.getCapaciteMaximale() <= 0) {
            throw new IllegalArgumentException(
                    "Capacité invalide."
            );
        }

        collecteDAO.modifier(collecte);
    }

    public void annulerCollecte(Long id) {
        Collecte collecte = collecteDAO.trouverParId(id);

        if (collecte == null) {
            throw new IllegalArgumentException("Collecte introuvable.");
        }

        collecte.setStatut(StatutCollecte.ANNULEE);
        collecteDAO.modifier(collecte);
    }

    public void terminerCollecte(Long id) {
        Collecte collecte = collecteDAO.trouverParId(id);

        if (collecte == null) {
            throw new IllegalArgumentException("Collecte introuvable.");
        }

        collecte.setStatut(StatutCollecte.TERMINEE);
        collecteDAO.modifier(collecte);
    }

    public void supprimerCollecte(Long id) {
        collecteDAO.supprimer(id);
    }
}
