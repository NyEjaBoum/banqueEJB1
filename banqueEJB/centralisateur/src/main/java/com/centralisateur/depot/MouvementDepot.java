package com.centralisateur.depot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MouvementDepot {
    private int id;
    private int compteId;
    private double montant;
    private int TypeMouvementId;
    private String dateMouvement;

    public int getId() { return id; }
    public int getCompteId() { return compteId; }
    public double getMontant() { return montant; }
    public int getTypeMouvementId() { return TypeMouvementId; }
    public String getDateMouvement() { return dateMouvement; }

    // setters optionnels si besoin
}
