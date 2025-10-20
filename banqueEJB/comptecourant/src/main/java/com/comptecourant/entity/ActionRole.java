package com.comptecourant.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "action_role")
public class ActionRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom_table")
    private String nomTable;
    
    private String action;
    private int role;
    
    // Constructeurs
    public ActionRole() {}
    
    public ActionRole(String nomTable, String action, int role) {
        this.nomTable = nomTable;
        this.action = action;
        this.role = role;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNomTable() { return nomTable; }
    public void setNomTable(String nomTable) { this.nomTable = nomTable; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }
}