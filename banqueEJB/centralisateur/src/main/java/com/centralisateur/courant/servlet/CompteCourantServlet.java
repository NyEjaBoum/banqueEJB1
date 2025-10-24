package com.centralisateur.courant.servlet;

import com.centralisateur.courant.CompteCourantCentralService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.centralisateur.TypeMouvement;
import com.centralisateur.Client;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.comptecourant.session.SessionUtilisateur;
import com.change.IChangeService;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/compte_courant")
public class CompteCourantServlet extends HttpServlet {
    @EJB
    private CompteCourantCentralService compteService;

    @EJB(lookup = "java:global/change-1.0-SNAPSHOT/ChangeService!com.change.IChangeService")
    private IChangeService changeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");

        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<String> devises;
        try {
            devises = changeService.listerDevises();
        } catch (Exception e) {
            devises = List.of();
            req.setAttribute("erreur", "Erreur lors du chargement des devises : " + e.getMessage());
        }
        req.setAttribute("devises", devises);

        try {
List<CompteCourant> comptes = compteService.listerComptes(sessionUtilisateur);
Map<Long, Double> soldes = new HashMap<>();
for (CompteCourant compte : comptes) {
    soldes.put(compte.getId(), compteService.getSolde(compte.getId(), sessionUtilisateur));
}
req.setAttribute("comptes", comptes);
req.setAttribute("soldes", soldes);

            List<TypeMouvement> types = compteService.listerTypesMouvement();
            req.setAttribute("typesMouvement", types);

            List<Client> clients = compteService.listerClients();
            req.setAttribute("listClients", clients);

            String action = req.getParameter("action");
            if ("solde".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                Double solde = compteService.getSolde(compteId, sessionUtilisateur);
                req.setAttribute("solde", solde);
            } else if ("historique".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                List<MouvementCourant> mouvements = compteService.listerMouvementsCourant(compteId, sessionUtilisateur);
                req.setAttribute("mouvements", mouvements);
                req.setAttribute("typesMouvement", types);
                req.setAttribute("compteId", compteId);
                req.getRequestDispatcher("/courant/mouvements.jsp").forward(req, resp);
                return;
            }
        } catch (SecurityException ex) {
            req.setAttribute("erreur", "Accès refusé : " + ex.getMessage());
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
        }

        req.getRequestDispatcher("/courant/comptes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
        
        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String action = req.getParameter("action");
        try {
            if ("creer".equals(action)) {
                Long clientId = Long.valueOf(req.getParameter("clientId"));
                compteService.creerCompte(clientId, sessionUtilisateur);
                resp.sendRedirect("compte_courant");
            } else if ("mouvement".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                Double montant = Double.valueOf(req.getParameter("montant"));
                int type = Integer.parseInt(req.getParameter("type"));
                String devise = req.getParameter("devise");
                compteService.ajouterMouvement(compteId, montant, type, devise, sessionUtilisateur);
                resp.sendRedirect(req.getContextPath() + "/compte_courant");
            }
        } catch (SecurityException ex) {
            req.setAttribute("erreur", "Accès refusé : " + ex.getMessage());
            doGet(req, resp);
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
            doGet(req, resp);
        }
    }
}