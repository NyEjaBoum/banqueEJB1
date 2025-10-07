package com.centralisateur.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.centralisateur.depot.CompteDepotService;

@WebServlet("/depotParametre")
public class DepotParametreServlet extends HttpServlet {
    private CompteDepotService service = new CompteDepotService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/depot/depotParametre.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            int compteId = Integer.parseInt(req.getParameter("compteId"));
            String result = null;
            if ("plafond".equals(action)) {
                Double nouveauPlafond = Double.valueOf(req.getParameter("nouveauPlafond"));
                result = service.modifierPlafond(compteId, nouveauPlafond);
            } else if ("taux".equals(action)) {
                Double nouveauTaux = Double.valueOf(req.getParameter("nouveauTaux"));
                result = service.modifierTauxInteret(compteId, nouveauTaux);
            }
            req.setAttribute("result", result);
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/depot/depotParametre.jsp").forward(req, resp);
    }
}