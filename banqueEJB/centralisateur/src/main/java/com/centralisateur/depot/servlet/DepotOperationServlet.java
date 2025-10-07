package com.centralisateur.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.centralisateur.depot.CompteDepotService;

@WebServlet("/depotOperation")
public class DepotOperationServlet extends HttpServlet {
    private CompteDepotService service = new CompteDepotService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/depot/depotOperation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            int compteId = Integer.parseInt(req.getParameter("compteId"));
            Double montant = Double.valueOf(req.getParameter("montant"));
            String result = null;
            if ("versement".equals(action)) {
                result = service.verser(compteId, montant);
            } else if ("retrait".equals(action)) {
                result = service.retirer(compteId, montant);
            }
            req.setAttribute("result", result);
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/depot/depotOperation.jsp").forward(req, resp);
    }
}