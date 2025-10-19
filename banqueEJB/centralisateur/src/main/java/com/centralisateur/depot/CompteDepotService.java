package com.centralisateur.depot;

import java.net.http.*;
import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@jakarta.ejb.Stateless
public class CompteDepotService {
    private static final String BASE_URL = "http://localhost:5004/api/depot/";

    public String creerCompte(int clientId, Double plafondRetrait, Double tauxInteret) throws Exception {
        StringBuilder url = new StringBuilder(BASE_URL + "creer?clientId=" + clientId);
        if (plafondRetrait != null) url.append("&plafondRetrait=").append(plafondRetrait);
        if (tauxInteret != null) url.append("&tauxInteret=").append(tauxInteret);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url.toString()))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public List<CompteDepot> findByClientId(Long clientId) {
        try {
            String url = BASE_URL + "byClient/" + clientId;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<CompteDepot>>() {});
        } catch (Exception ex) {
            return java.util.Collections.emptyList();
        }
    }

    public String findAllComptes() throws Exception {
        String url = BASE_URL + "all";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String verser(int compteId, double montant) throws Exception {
        String url = BASE_URL + "versement?compteId=" + compteId + "&montant=" + montant;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String retirer(int compteId, double montant) throws Exception {
        String url = BASE_URL + "retrait?compteId=" + compteId + "&montant=" + montant;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getSolde(int compteId) throws Exception {
        String url = BASE_URL + "solde/" + compteId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getHistorique(int compteId) throws Exception {
        String url = BASE_URL + "historique/" + compteId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String modifierPlafond(int compteId, Double nouveauPlafond) throws Exception {
        String url = BASE_URL + "plafond/" + compteId + "?nouveauPlafond=" + nouveauPlafond;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String modifierTauxInteret(int compteId, Double nouveauTaux) throws Exception {
        String url = BASE_URL + "taux/" + compteId + "?nouveauTaux=" + nouveauTaux;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getParametreGlobal() throws Exception {
        String url = BASE_URL + "parametre";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String modifierParametreGlobal(Double plafond, Double taux) throws Exception {
        StringBuilder url = new StringBuilder(BASE_URL + "modifierParametre?");
        boolean hasParam = false;
        if (plafond != null) {
            url.append("plafond=").append(plafond);
            hasParam = true;
        }
        if (taux != null) {
            if (hasParam) url.append("&");
            url.append("taux=").append(taux);
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url.toString()))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}