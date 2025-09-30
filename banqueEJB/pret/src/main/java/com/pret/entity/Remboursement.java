package com.pret.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "remboursement")
public class Remboursement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pret_id", nullable = false)
    private Long pretId;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "interet_payes")
    private Double interetPayes;

    @Column(name = "capital_rembourse")
    private Double capitalRembourse;

    @Column(name = "date_remboursement", nullable = false)
    private LocalDate dateRemboursement;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPretId() { return pretId; }
    public void setPretId(Long pretId) { this.pretId = pretId; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public Double getInteretPayes() { return interetPayes; }
    public void setInteretPayes(Double interetPayes) { this.interetPayes = interetPayes; }
    public Double getCapitalRembourse() { return capitalRembourse; }
    public void setCapitalRembourse(Double capitalRembourse) { this.capitalRembourse = capitalRembourse; }
    public LocalDate getDateRemboursement() { return dateRemboursement; }
    public void setDateRemboursement(LocalDate dateRemboursement) { this.dateRemboursement = dateRemboursement; }
}