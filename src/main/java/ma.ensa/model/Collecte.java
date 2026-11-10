package ma.ensa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collecte")
public class Collecte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private LocalDate dateCollecte;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private String lieu;

    @Column(length = 1000)
    private String description;

    private int capaciteMaximale;

    @Enumerated(EnumType.STRING)
    private StatutCollecte statut = StatutCollecte.PLANIFIEE;

    @OneToMany(mappedBy = "collecte")
    private List<RendezVous> rendezVous = new ArrayList<>();

    public Collecte() {
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateCollecte() {
        return dateCollecte;
    }

    public void setDateCollecte(LocalDate dateCollecte) {
        this.dateCollecte = dateCollecte;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapaciteMaximale() {
        return capaciteMaximale;
    }

    public void setCapaciteMaximale(int capaciteMaximale) {
        this.capaciteMaximale = capaciteMaximale;
    }

    public StatutCollecte getStatut() {
        return statut;
    }

    public void setStatut(StatutCollecte statut) {
        this.statut = statut;
    }

    public List<RendezVous> getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(List<RendezVous> rendezVous) {
        this.rendezVous = rendezVous;
    }
}