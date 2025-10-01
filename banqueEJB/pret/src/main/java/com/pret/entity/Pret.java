package com.pret.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "pret")
public class Pret implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "type_pret_id", nullable = false)
    private Long typePretId;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "taux_interet", nullable = false)
    private Double tauxInteret;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "etat", nullable = false)
    private String etat; // ENCOURS ou REMBOURSE

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Long getTypePretId() { return typePretId; }
    public void setTypePretId(Long typePretId) { this.typePretId = typePretId; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public Double getTauxInteret() { return tauxInteret; }
    public void setTauxInteret(Double tauxInteret) { this.tauxInteret = tauxInteret; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}