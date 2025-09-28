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

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @Column(name = "solde")
    private Double solde;

    @NotNull
    @Column(name = "date_maj")
    private LocalDate dateMaj;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Double getSolde() { return solde; }
    public void setSolde(Double solde) { this.solde = solde; }
    public LocalDate getDateMaj() { return dateMaj; }
    public void setDateMaj(LocalDate dateMaj) { this.dateMaj = dateMaj; }
}
