package com.change;

import java.time.LocalDate;

public class CoursDevise {
    private String devise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double cour;

    // Getters et setters
    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public double getCour() { return cour; }
    public void setCour(double cour) { this.cour = cour; }
}