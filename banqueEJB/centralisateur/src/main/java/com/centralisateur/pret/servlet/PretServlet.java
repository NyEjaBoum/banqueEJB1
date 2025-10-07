package com.centralisateur.pret.servlet;

import com.centralisateur.pret.PretCentralService;
import com.pret.entity.Pret;
import com.pret.entity.Remboursement;
import com.pret.entity.TypePret;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/pret")
public class PretServlet extends HttpServlet {
    @EJB
    private PretCentralService pretService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("historique".equals(action)) {
            Long pretId = Long.valueOf(req.getParameter("pretId"));
            List<Remboursement> remboursements = pretService.historiqueRemboursements(pretId);
            req.setAttribute("remboursements", remboursements);
            req.getRequestDispatcher("/pret/historiquePret.jsp").forward(req, resp);
        } else if ("rembourserPage".equals(action)) {
            Long pretId = Long.valueOf(req.getParameter("pretId"));
            req.setAttribute("pretId", pretId);
            req.getRequestDispatcher("/pret/rembourserPret.jsp").forward(req, resp);
        } else {
            List<Pret> prets = pretService.listerPrets();
            req.setAttribute("prets", prets);
            List<TypePret> typesPret = pretService.listerTypesPret();
            req.setAttribute("typesPret", typesPret);
            req.getRequestDispatcher("/pret/prets.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("creer".equals(action)) {
                Pret pret = new Pret();
                pret.setClientId(Long.valueOf(req.getParameter("clientId")));
                pret.setTypePretId(Long.valueOf(req.getParameter("typePretId")));
                pret.setMontant(Double.valueOf(req.getParameter("montant")));
                pret.setDateDebut(LocalDate.parse(req.getParameter("dateDebut")));
                pretService.creerPret(pret);
                resp.sendRedirect("pret");
            } else if ("rembourser".equals(action)) {
                Long pretId = Long.valueOf(req.getParameter("pretId"));
                Double montant = Double.valueOf(req.getParameter("montant"));
                pretService.rembourserPret(pretId, montant);
                resp.sendRedirect("pret?action=historique&pretId=" + pretId);
            }
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
            doGet(req, resp);
        }
    }
}