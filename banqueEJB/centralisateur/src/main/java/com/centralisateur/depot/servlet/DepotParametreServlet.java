package com.centralisateur.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.centralisateur.depot.CompteDepotService;
import com.fasterxml.jackson.databind.ObjectMapper; // Pour ObjectMapper
import com.centralisateur.depot.Parametre; // Pour Parametre

@WebServlet("/depotParametre")
public class DepotParametreServlet extends HttpServlet {
    private CompteDepotService service = new CompteDepotService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String paramJson = service.getParametreGlobal();
            ObjectMapper mapper = new ObjectMapper();
            // Adapter le type selon ton modèle Parametre
            Parametre param = mapper.readValue(paramJson, Parametre.class);
            req.setAttribute("parametreGlobal", param);
        } catch (Exception ex) { 
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/depot/depotParametre.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String result = null;
        try {
            if ("plafond".equals(action)) {
                int compteId = Integer.parseInt(req.getParameter("compteId"));
                Double nouveauPlafond = Double.valueOf(req.getParameter("nouveauPlafond"));
                result = service.modifierPlafond(compteId, nouveauPlafond);
            } else if ("taux".equals(action)) {
                int compteId = Integer.parseInt(req.getParameter("compteId"));
                Double nouveauTaux = Double.valueOf(req.getParameter("nouveauTaux"));
                result = service.modifierTauxInteret(compteId, nouveauTaux);
            } else if ("modifierParametre".equals(action)) {
                String paramJson = service.getParametreGlobal();
                ObjectMapper mapper = new ObjectMapper();
                Parametre param = mapper.readValue(paramJson, Parametre.class);

                Double plafond;
                String plafondStr = req.getParameter("plafond");
                if (plafondStr != null && !plafondStr.trim().isEmpty()) {
                    plafond = Double.valueOf(plafondStr);
                } else {
                    plafond = param.getPlafondRetraitGlobal();
                }

                Double taux;
                String tauxStr = req.getParameter("taux");
                if (tauxStr != null && !tauxStr.trim().isEmpty()) {
                    taux = Double.valueOf(tauxStr);
                } else {
                    taux = param.getTauxInteretDepot();
                }

                result = service.modifierParametreGlobal(plafond, taux);
            }
            req.setAttribute("result", result);
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/depot/depotParametre.jsp").forward(req, resp);
    }
}