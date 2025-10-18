package com.centralisateur.depot;

public class CompteDepot {
    private int id;
    private int clientId;
    private double solde;
    private Double tauxInteret;
    private Double plafondRetrait;
    private String dateDernierInteret;
    private boolean actif;

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
    public Double getTauxInteret() { return tauxInteret; }
    public void setTauxInteret(Double tauxInteret) { this.tauxInteret = tauxInteret; }
    public Double getPlafondRetrait() { return plafondRetrait; }
    public void setPlafondRetrait(Double plafondRetrait) { this.plafondRetrait = plafondRetrait; }
    public String getDateDernierInteret() { return dateDernierInteret; }
    public void setDateDernierInteret(String dateDernierInteret) { this.dateDernierInteret = dateDernierInteret; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
}