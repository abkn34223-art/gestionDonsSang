
package ma.ensa.service;

import ma.ensa.dao.RendezVousDAO;
import ma.ensa.model.RendezVous;
import ma.ensa.model.StatutRendezVous;

import java.util.List;

public class RendezVousService {

    private final RendezVousDAO rendezVousDAO = new RendezVousDAO();

    // Demander un rendez-vous
    public void demanderRendezVous(RendezVous rendezVous) {

        if (rendezVous == null) {
            throw new IllegalArgumentException(
                    "Le rendez-vous ne peut pas être null."
            );
        }

        if (rendezVous.getCollecte() == null) {
            throw new IllegalArgumentException(
                    "Une collecte doit être sélectionnée."
            );
        }

        if (rendezVous.getDonneur() == null) {
            throw new IllegalArgumentException(
                    "Un donneur doit être associé au rendez-vous."
            );
        }

        rendezVous.setStatut(StatutRendezVous.EN_ATTENTE);
        rendezVousDAO.ajouter(rendezVous);
    }

    // Lister tous les rendez-vous
    public List<RendezVous> listerRendezVous() {
        return rendezVousDAO.lister();
    }

    // Chercher un rendez-vous par son identifiant
    public RendezVous trouverParId(Long id) {

        return rendezVousDAO.trouverParId(id);
    }

    // Lister les rendez-vous d'un donneur
    public List<RendezVous> listerParDonneur(Long donneurId) {

        return rendezVousDAO.listerParDonneur(donneurId);
    }

    // Lister les rendez-vous d'une collecte
    public List<RendezVous> listerParCollecte(Long collecteId) {

        return rendezVousDAO.listerParCollecte(collecteId);
    }

    // Confirmer un rendez-vous
    public void confirmerRendezVous(Long id) {

        RendezVous rendezVous = trouverParId(id);

        if (rendezVous == null) {
            throw new IllegalArgumentException(
                    "Rendez-vous introuvable."
            );
        }

        if (rendezVous.getStatut() != StatutRendezVous.EN_ATTENTE) {
            throw new IllegalStateException(
                    "Ce rendez-vous ne peut pas être confirmé."
            );
        }

        rendezVous.setStatut(StatutRendezVous.CONFIRME);
        rendezVousDAO.modifier(rendezVous);
    }

    // Annuler un rendez-vous
    public void annulerRendezVous(Long id) {

        RendezVous rendezVous = trouverParId(id);

        if (rendezVous == null) {
            throw new IllegalArgumentException(
                    "Rendez-vous introuvable."
            );
        }

        if (rendezVous.getStatut() != StatutRendezVous.EN_ATTENTE
                && rendezVous.getStatut() != StatutRendezVous.CONFIRME) {

            throw new IllegalStateException(
                    "Ce rendez-vous ne peut pas être annulé."
            );
        }

        rendezVous.setStatut(StatutRendezVous.ANNULE);
        rendezVousDAO.modifier(rendezVous);
    }

    // Marquer un rendez-vous comme effectué
    public void marquerCommeEffectue(Long id) {

        RendezVous rendezVous = trouverParId(id);

        if (rendezVous == null) {
            throw new IllegalArgumentException(
                    "Rendez-vous introuvable."
            );
        }

        if (rendezVous.getStatut() != StatutRendezVous.CONFIRME) {
            throw new IllegalStateException(
                    "Seul un rendez-vous confirmé peut être effectué."
            );
        }

        rendezVous.setStatut(StatutRendezVous.EFFECTUE);
        rendezVousDAO.modifier(rendezVous);
    }
}
