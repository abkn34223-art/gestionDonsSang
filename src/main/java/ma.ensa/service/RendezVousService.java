package ma.ensa.service;

import ma.ensa.dao.CollecteDAO;
import ma.ensa.dao.DonneurDAO;
import ma.ensa.model.*;
import ma.ensa.dao.RendezVousDAO;
import java.util.List;

public class RendezVousService {
    private RendezVousDAO rendezVousDAO;
    private CollecteDAO collecteDAO;
    private DonneurDAO donneurDAO;
    public RendezVousService(){
        this.rendezVousDAO = new RendezVousDAO();
        this.collecteDAO = new CollecteDAO();
        this.donneurDAO = new DonneurDAO();
    }
    public void demanderRendezVous(RendezVous rendezVous){
        if(rendezVous == null){
            throw new IllegalArgumentException("Rendez-vous invalide.");
        }
        if(rendezVous.getCollecte()==null || rendezVous.getCollecte().getId()==null){
            throw new IllegalArgumentException("Une collecte doit être sélectionnée.");
        }
        Collecte collecte = collecteDAO.trouverParId(rendezVous.getCollecte().getId());
        if (collecte == null){
            throw new IllegalArgumentException("Collecte introuvable.");
        }
        if (collecte.getStatut() != StatutCollecte.PLANIFIEE){
            throw new IllegalArgumentException("Cette collecte n'est pas disponible.");
        }
        if (rendezVous.getDonneur()==null || rendezVous.getDonneur().getId() == null){
            throw new IllegalArgumentException("Un donneur doit être associé au rendez-vous.");
        }
        Donneur donneur = donneurDAO.findById(rendezVous.getDonneur().getId());
        if (donneur ==null){
            throw new IllegalArgumentException("Donneur introuvable.");
        }
        Utilisateur utilisateur = donneur.getUtilisateur();
        if (utilisateur == null || utilisateur.getRole() != Role.DONNEUR){
            throw new IllegalArgumentException("Utilisateur non autorisé.");
        }
        List<RendezVous> rendezVousList = rendezVousDAO.listerParCollecte(collecte.getId());
        Long nbrplaceoccup = rendezVousList.stream()
                .filter(r -> r.getStatut() != StatutRendezVous.ANNULE)
                .count();
        if(nbrplaceoccup >= collecte.getCapaciteMaximale()){
            throw new IllegalArgumentException("La capacité maximale de cette collecte est atteinte.");
        }
        List<RendezVous> rendezVousDonneur = rendezVousDAO.listerParDonneur(donneur.getId());
        for (RendezVous r: rendezVousDonneur){
            if (r.getCollecte().getId().equals(collecte.getId())
            && (r.getStatut()== StatutRendezVous.EN_ATTENTE || r.getStatut() == StatutRendezVous.CONFIRME)){
                throw new IllegalArgumentException("Ce donneur possède déjà un rendez-vous pour cette collecte.");
            }
        }
        rendezVous.setStatut(StatutRendezVous.EN_ATTENTE);
        rendezVousDAO.ajouter(rendezVous);
    }
    public void confirmerRendezVous(Long id){
        if(id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        RendezVous rendezVous = rendezVousDAO.trouverParId(id);
        if (rendezVous == null){
            throw new IllegalArgumentException("Rendez-vous introuvable.");
        }
        if(rendezVous.getStatut() != StatutRendezVous.EN_ATTENTE){
            throw new IllegalArgumentException("Ce rendez-vous ne peut pas être confirmé.");
        }
        rendezVousDAO.mettreAJourStatut(id, StatutRendezVous.CONFIRME);
    }
    public void annulerRendezVous(Long id){
        if (id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        RendezVous rendezVous = rendezVousDAO.trouverParId(id);
        if (rendezVous == null){
            throw new IllegalArgumentException("Rendez-vous introuvable.");
        }
        if (rendezVous.getStatut() != StatutRendezVous.EN_ATTENTE
            && rendezVous.getStatut() != StatutRendezVous.CONFIRME){
            throw new IllegalArgumentException("Ce rendez-vous ne peut pas être annulé.");
        }
        rendezVousDAO.mettreAJourStatut(id,StatutRendezVous.ANNULE);
    }
    public void marquerCommeEffectue(Long id){
        if (id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        RendezVous rendezVous = rendezVousDAO.trouverParId(id);
        if (rendezVous == null){
            throw new IllegalArgumentException("Rendez-vous introuvable.");
        }
        if (rendezVous.getStatut() != StatutRendezVous.CONFIRME){
            throw new IllegalArgumentException("Seul un rendez-vous confirmé peut être effectué.");
        }
        rendezVousDAO.mettreAJourStatut(id, StatutRendezVous.EFFECTUE);
    }
    public RendezVous trouverParId(Long id){
        if (id == null){
            throw new IllegalArgumentException("Identifiant invalide.");
        }
        return rendezVousDAO.trouverParId(id);
    }
    public List<RendezVous> listerRendezVous(){
        return rendezVousDAO.lister();
    }
    public List<RendezVous> listerParCollecte(Long idcol){
        if (idcol == null){
            throw new IllegalArgumentException("Collecte invalide.");
        }
        if (collecteDAO.trouverParId(idcol)==null){
            throw new IllegalArgumentException("Collecte introuvable.");
        }
        return rendezVousDAO.listerParCollecte(idcol);
    }
    public List<RendezVous> listerParDonneur(Long iddon){
        if (iddon == null){
            throw new IllegalArgumentException("Donneur invalide");
        }
        return rendezVousDAO.listerParDonneur(iddon);
    }
}