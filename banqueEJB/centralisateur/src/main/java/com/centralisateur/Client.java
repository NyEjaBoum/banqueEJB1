package com.centralisateur;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private int numeroClient;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public int getNumeroClient() { return numeroClient; }
    public void setNumeroClient(int numeroClient) { this.numeroClient = numeroClient; }
}