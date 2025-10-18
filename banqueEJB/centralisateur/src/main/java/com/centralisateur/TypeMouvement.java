package com.centralisateur;

import jakarta.persistence.*;

@Entity
@Table(name = "type_mouvement")
public class TypeMouvement {
    @Id
    private int id;

    private String libelle;
    private String description;

    public int getId() { return id; }
    public String getLibelle() { return libelle; }
    public void setId(int id) { this.id = id; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDescription() { return description; }
    public void setDescription(String s) { this.description = s; } 
}