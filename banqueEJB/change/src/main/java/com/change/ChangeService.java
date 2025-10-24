package com.change;

import jakarta.ejb.Stateless;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.ejb.Remote;

@Stateless
@Remote(IChangeService.class)
public class ChangeService implements IChangeService {
private static final String FICHIER_COURS = "C:/wildfly-37.0.1.Final/standalone/change/cours.csv";

    public double getCours(String devise, LocalDate date) throws Exception {
        List<CoursDevise> coursList = lireCours();
        for (CoursDevise c : coursList) {
            if (c.getDevise().equalsIgnoreCase(devise)
                && (date.isEqual(c.getDateDebut()) || date.isAfter(c.getDateDebut()))
                && (c.getDateFin() == null || date.isEqual(c.getDateFin()) || date.isBefore(c.getDateFin()))) {
                return c.getCour();
            }
        }
        throw new Exception("Cours introuvable pour " + devise + " à la date " + date);
    }

    

private List<CoursDevise> lireCours() throws IOException {
    List<CoursDevise> list = new ArrayList<>();
    List<String> lignes = Files.readAllLines(Paths.get(FICHIER_COURS));
    for (String ligne : lignes) {
        if (ligne.startsWith("Devise")) continue;
        String[] parts = ligne.split(";");
        CoursDevise c = new CoursDevise();
        c.setDevise(parts[0]);
        c.setDateDebut(LocalDate.parse(parts[1]));
        // Correction ici : si DateFin est vide, mettre null
        c.setDateFin(parts[2].isEmpty() ? null : LocalDate.parse(parts[2]));
        c.setCour(Double.parseDouble(parts[3]));
        list.add(c);
    }
    return list;
}

    public double convertirEnAriary(String devise, double montant, LocalDate date) throws Exception {
        if (devise.equalsIgnoreCase("MGA")) return montant;
        double cour = getCours(devise, date);
        return montant * cour;
    }

    public List<String> listerDevises() throws IOException {
        Set<String> devises = new LinkedHashSet<>();
        devises.add("MGA"); // Toujours proposer Ariary
        List<String> lignes = Files.readAllLines(Paths.get(FICHIER_COURS));
        for (String ligne : lignes) {
            if (ligne.startsWith("Devise")) continue;
            String[] parts = ligne.split(";");
            devises.add(parts[0]);
        }
        return new ArrayList<>(devises);
    }
}