package ma.ensa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "rendez_vous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "heure", nullable = false)
    private LocalTime heure;
    @Column(name = "date_rendezvous", nullable = false)
    private LocalDateTime dateDemande;
    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut ;
    @ManyToOne
    @JoinColumn(name = "collecte_id", nullable = false)
    private Collecte collecte;
    @ManyToOne
    @JoinColumn(name = "donneur_id", nullable = false)
    private Donneur donneur;

    public RendezVous() {}
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public LocalTime getHeure() {return heure;}
    public void setHeure(LocalTime heure) {this.heure = heure;}
    public LocalDateTime getDateDemande() {return dateDemande;}
    public void setDateDemande(LocalDateTime dateDemande) {this.dateDemande = dateDemande;}
    public StatutRendezVous getStatut() {return statut;}
    public void setStatut(StatutRendezVous statut) {this.statut = statut;}
    public Collecte getCollecte() {return collecte;}
    public void setCollecte(Collecte collecte) {this.collecte = collecte;}
    public Donneur getDonneur() {return donneur;}
    public void setDonneur(Donneur donneur) {this.donneur = donneur;}
}
