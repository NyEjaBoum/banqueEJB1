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
    @Column(name = "type_mouvement_id")
    private int typeMouvementId; //ENTREE OU SORTIE 

    @NotNull
    @Column(name = "date_mouvement")
    private LocalDate dateMouvement;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CompteCourant getCompte() { return compte; }
    public void setCompte(CompteCourant compte) { this.compte = compte; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public int getTypeMouvementId() { return typeMouvementId; }
    public void setTypeMouvementId(int typeMouvementId) { this.typeMouvementId = typeMouvementId; }
    public void setTypeMouvement(int type) { this.typeMouvementId = type; }
    public LocalDate getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(LocalDate dateMouvement) { this.dateMouvement = dateMouvement; }
}
