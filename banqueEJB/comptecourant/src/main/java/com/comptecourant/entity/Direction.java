package com.comptecourant.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "direction")
public class Direction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String libelle;
    private int niveau;
    
    // Constructeurs
    public Direction() {}
    
    public Direction(String libelle, int niveau) {
        this.libelle = libelle;
        this.niveau = niveau;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    
    public int getNiveau() { return niveau; }
    public void setNiveau(int niveau) { this.niveau = niveau; }
}