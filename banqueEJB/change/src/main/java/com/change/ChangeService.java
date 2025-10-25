package com.change;

import jakarta.ejb.Stateless;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.ejb.Remote;

@Stateless
@Remote(IChangeService.class)
public class ChangeService implements IChangeService {
    private static final String FICHIER_COURS = "cours.csv"; // nom du fichier dans resources

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
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(FICHIER_COURS);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith("Devise")) continue;
                String[] parts = ligne.split(";");
                CoursDevise c = new CoursDevise();
                c.setDevise(parts[0]);
                c.setDateDebut(LocalDate.parse(parts[1]));
                c.setDateFin(parts[2].isEmpty() ? null : LocalDate.parse(parts[2]));
                c.setCour(Double.parseDouble(parts[3]));
                list.add(c);
            }
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
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(FICHIER_COURS);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith("Devise")) continue;
                String[] parts = ligne.split(";");
                devises.add(parts[0]);
            }
        }
        return new ArrayList<>(devises);
    }
}