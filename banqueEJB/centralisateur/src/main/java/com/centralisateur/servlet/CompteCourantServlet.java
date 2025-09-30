package com.centralisateur;

import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.comptecourant.service.ICompteCourantService;
import com.comptecourant.service.*;
import com.comptecourant.service.BusinessException;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/compte_courant")
public class CompteCourantServlet extends HttpServlet {
    // @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    @EJB
    private CentralisateurService compteService = new CentralisateurService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CompteCourant> comptes = compteService.listerComptes();
        req.setAttribute("comptes", comptes);

        List<TypeMouvement> types = compteService.listerTypesMouvement();
        req.setAttribute("typesMouvement", types);

        List<Client> clients = compteService.listerClients();
        req.setAttribute("listClients",clients);

        String action = req.getParameter("action");
        if ("solde".equals(action)) {
            Long compteId = Long.valueOf(req.getParameter("compteId"));
            Double solde = compteService.getSolde(compteId);
            req.setAttribute("solde", solde);
        }
        else if ("historique".equals(action)) {
            Long compteId = Long.valueOf(req.getParameter("compteId"));
            List<MouvementCourant> mouvements = compteService.listerMouvementsCourant(compteId);
            req.setAttribute("mouvements", mouvements);
            req.setAttribute("typesMouvement", types);
            req.setAttribute("compteId", compteId);
            req.getRequestDispatcher("/courant/mouvements.jsp").forward(req, resp);
        }
            req.getRequestDispatcher("/courant/comptes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("creer".equals(action)) {
                Long clientId = Long.valueOf(req.getParameter("clientId"));
                compteService.creerCompte(clientId);
                resp.sendRedirect("comptes");
            } else if ("mouvement".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                Double montant = Double.valueOf(req.getParameter("montant"));
                int type = Integer.parseInt(req.getParameter("type"));
                // MouvementCourant.TypeMouvement type = MouvementCourant.TypeMouvement.valueOf(typeStr);
                compteService.ajouterMouvement(compteId, montant, type);
                resp.sendRedirect(req.getContextPath() + "/compte_courant");
            }
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
            doGet(req, resp);
        }
    }
}