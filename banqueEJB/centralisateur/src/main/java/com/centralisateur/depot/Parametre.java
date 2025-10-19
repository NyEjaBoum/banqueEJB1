package com.centralisateur.depot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parametre {
    private Double plafondRetraitGlobal;
    private Double tauxInteretDepot;

    public Double getPlafondRetraitGlobal() {
        return plafondRetraitGlobal;
    }
    public void setPlafondRetraitGlobal(Double plafondRetraitGlobal) {
        this.plafondRetraitGlobal = plafondRetraitGlobal;
    }

    public Double getTauxInteretDepot() {
        return tauxInteretDepot;
    }
    public void setTauxInteretDepot(Double tauxInteretDepot) {
        this.tauxInteretDepot = tauxInteretDepot;
    }
}