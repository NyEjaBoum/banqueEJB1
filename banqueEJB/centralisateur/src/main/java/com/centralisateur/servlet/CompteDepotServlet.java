package com.centralisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.centralisateur.CompteDepotClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

@WebServlet("/compte_depot")
public class CompteDepotServlet extends HttpServlet {
    private CompteDepotClient client = new CompteDepotClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Affiche le formulaire de création
        req.getRequestDispatcher("/comptesDepot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
// ...existing code...
            if ("creer".equals(action)) {
                int clientId = Integer.parseInt(req.getParameter("clientId"));
                Double plafond = null;
                Double taux = null;
                String plafondStr = req.getParameter("plafondRetrait");
                String tauxStr = req.getParameter("tauxInteret");
                if (plafondStr != null && !plafondStr.trim().isEmpty()) {
                    plafond = Double.valueOf(plafondStr);
                }
                if (tauxStr != null && !tauxStr.trim().isEmpty()) {
                    taux = Double.valueOf(tauxStr);
                }
                String result = client.creerCompte(clientId, plafond, taux);
                req.setAttribute("result", result);
            }
            else if ("solde".equals(action)) {
                int compteId = Integer.parseInt(req.getParameter("compteId"));
                String solde = client.getSolde(compteId);
                req.setAttribute("solde", solde);
            } else if ("historique".equals(action)) {
                int compteId = Integer.parseInt(req.getParameter("compteId"));
                String historiqueJson = client.getHistorique(compteId);
                ObjectMapper mapper = new ObjectMapper();
                List<MouvementDepot> historique = mapper.readValue(historiqueJson, new TypeReference<List<MouvementDepot>>() {});
                req.setAttribute("historique", historique);
            }
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/comptesDepot.jsp").forward(req, resp);
    }
}