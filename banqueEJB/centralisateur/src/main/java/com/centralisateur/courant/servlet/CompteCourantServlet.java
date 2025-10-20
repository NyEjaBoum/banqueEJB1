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

@WebServlet("/compte_courant")
public class CompteCourantServlet extends HttpServlet {
    @EJB
    private CompteCourantCentralService compteService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
        
        // Si pas de session, rediriger vers login
        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<CompteCourant> comptes = compteService.listerComptes(sessionUtilisateur);
            req.setAttribute("comptes", comptes);

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
                compteService.ajouterMouvement(compteId, montant, type, sessionUtilisateur);
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