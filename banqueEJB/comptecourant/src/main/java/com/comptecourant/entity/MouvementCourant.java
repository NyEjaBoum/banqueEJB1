package com.comptecourant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "mouvement_courant")
public class MouvementCourant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "compte_id")
    private CompteCourant compte;

    @NotNull
    @Positive
    @Column(name = "montant")
    private Double montant;

    @NotNull
    @Column(name = "type")
    private String type; // ENTREE / SORTIE

    @NotNull
    @Column(name = "date_mouvement")
    private LocalDate dateMouvement;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CompteCourant getCompte() { return compte; }
    public void setCompte(CompteCourant compte) { this.compte = compte; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(LocalDate dateMouvement) { this.dateMouvement = dateMouvement; }

    public enum TypeMouvement {
        ENTREE, SORTIE
    }
}
