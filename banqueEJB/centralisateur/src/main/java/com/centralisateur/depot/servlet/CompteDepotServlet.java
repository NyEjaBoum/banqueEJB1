package com.centralisateur.depot.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.ejb.EJB;
import java.io.IOException;
import com.centralisateur.depot.CompteDepotService;
import com.centralisateur.depot.MouvementDepot;
import com.centralisateur.TypeMouvement;
import com.centralisateur.TypeMouvementDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import com.centralisateur.depot.CompteDepot;
import com.centralisateur.Client;
import com.centralisateur.ClientDAO;

@WebServlet("/compte_depot")
public class CompteDepotServlet extends HttpServlet {
    @EJB
    private TypeMouvementDAO typeMouvementDAO;
    
    private CompteDepotService service = new CompteDepotService();

    @EJB
    private ClientDAO clientDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Charger les types de mouvement pour toutes les pages
        List<TypeMouvement> types = typeMouvementDAO.findAll();
        req.setAttribute("typesMouvement", types);

        List<Client> clients = clientDAO.findAll();
        req.setAttribute("listClients",clients);

        // Charger la liste des comptes dépôt depuis l'API
        try {
            String comptesJson = service.findAllComptes();
            ObjectMapper mapper = new ObjectMapper();
            List<CompteDepot> comptes = mapper.readValue(comptesJson, new TypeReference<List<CompteDepot>>() {});
            req.setAttribute("comptesDepot", comptes);
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }

        req.getRequestDispatcher("/depot/comptesDepot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        // Charger les types de mouvement avant toute action
        List<TypeMouvement> types = typeMouvementDAO.findAll();
        req.setAttribute("typesMouvement", types);
        
        try {
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
                String result = service.creerCompte(clientId, plafond, taux);
                req.setAttribute("result", result);
            }
            else if ("solde".equals(action)) {
                int compteId = Integer.parseInt(req.getParameter("compteId"));
                String solde = service.getSolde(compteId);
                req.setAttribute("solde", solde);
            } else if ("historique".equals(action)) {
                int compteId = Integer.parseInt(req.getParameter("compteId"));
                String historiqueJson = service.getHistorique(compteId);
                ObjectMapper mapper = new ObjectMapper();
                List<MouvementDepot> historique = mapper.readValue(historiqueJson, new TypeReference<List<MouvementDepot>>() {});
                req.setAttribute("historique", historique);
            }
            // else if ("liste".equals(action)) {
            //     String comptesJson = service.findAllComptes();
            //     ObjectMapper mapper = new ObjectMapper();
            //     List<CompteDepot> comptes = mapper.readValue(comptesJson, new TypeReference<List<CompteDepot>>() {});
            //     req.setAttribute("comptesDepot", comptes);
            // }
            
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/depot/comptesDepot.jsp").forward(req, resp);
    }
}