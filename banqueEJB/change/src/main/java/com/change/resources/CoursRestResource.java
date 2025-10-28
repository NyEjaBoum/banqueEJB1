package com.change.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.io.*;
import java.util.*;
import com.change.CoursDevise;

@Path("coursrest")
public class CoursRestResource {

    private static final String FICHIER_COURS = "cours.csv";

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public double getCours(
        @QueryParam("devise") String devise,
        @QueryParam("date") String dateStr
    ) throws Exception {
        LocalDate date = (dateStr != null && !dateStr.isEmpty())
            ? LocalDate.parse(dateStr)
            : LocalDate.now();
        for (CoursDevise c : lireCours()) {
            if (c.getDevise().equalsIgnoreCase(devise)
                && (date.isEqual(c.getDateDebut()) || date.isAfter(c.getDateDebut()))
                && (c.getDateFin() == null || date.isEqual(c.getDateFin()) || date.isBefore(c.getDateFin()))) {
                return c.getCour();
            }
        }
        throw new Exception("Cours introuvable pour " + devise + " à la date " + date);
    }

    @GET
    @Path("/convertir")
    @Produces(MediaType.APPLICATION_JSON)
    public double convertirEnAriary(
        @QueryParam("devise") String devise,
        @QueryParam("montant") double montant,
        @QueryParam("date") String dateStr
    ) throws Exception {
        if (devise.equalsIgnoreCase("MGA")) return montant;
        double cour = getCours(devise, dateStr);
        return montant * cour;
    }

    @GET
    @Path("/devises")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getDevises() throws Exception {
        Set<String> devises = new LinkedHashSet<>();
        devises.add("MGA");
        for (CoursDevise c : lireCours()) {
            devises.add(c.getDevise());
        }
        return new ArrayList<>(devises);
    }
}