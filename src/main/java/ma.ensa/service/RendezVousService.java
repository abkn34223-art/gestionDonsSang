
package ma.ensa.service;

import ma.ensa.dao.RendezVousDAO;
import ma.ensa.model.RendezVous;
import ma.ensa.model.StatutRendezVous;

import java.util.List;

public class RendezVousService {

    private final RendezVousDAO rendezVousDAO = new RendezVousDAO();

    public void demanderRendezVous(RendezVous rendezVous) {

        if (rendezVous.getCollecte() == null) {
            throw new IllegalArgumentException(
                    "Une collecte doit être sélectionnée."
            );
        }

        rendezVous.setStatut(StatutRendezVous.EN_ATTENTE);
        rendezVousDAO.ajouter(rendezVous);
    }

    public List<RendezVous> listerRendezVous() {
        return rendezVousDAO.lister();
    }

    public RendezVous trouverParId(Long id) {
        return rendezVousDAO.trouverParId(id);
    }

    public List<RendezVous> listerParDonneur(Long donneurId) {
        return rendezVousDAO.trouverParDonneur(donneurId);
    }

    public List<RendezVous> listerParCollecte(Long collecteId) {
        return rendezVousDAO.trouverParCollecte(collecteId);
    }

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
