package com.pret.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "type_pret")
public class TypePret implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @Column(name = "nb_mois_remboursement", nullable = false)
    private Integer nbMoisRemboursement;

    private Double interet;
    private Double montant;

    // Getters
    public Long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Integer getNbMoisRemboursement() {
        return nbMoisRemboursement;
    }

    public Double getInteret() {
        return interet;
    }

    public Double getMontant() {
        return montant;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setNbMoisRemboursement(Integer nbMoisRemboursement) {
        this.nbMoisRemboursement = nbMoisRemboursement;
    }

    public void setInteret(Double interet) {
        this.interet = interet;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
