package com.comptecourant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "compte_courant")
public class CompteCourant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId; // id du client dans central_db

    @NotNull
    @Column(name = "solde")
    private Double solde;

    @NotNull
    @Column(name = "date_maj")
    private LocalDate dateMaj;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Double getSolde() { return solde; }
    public void setSolde(Double solde) { this.solde = solde; }
    public LocalDate getDateMaj() { return dateMaj; }
    public void setDateMaj(LocalDate dateMaj) { this.dateMaj = dateMaj; }
}