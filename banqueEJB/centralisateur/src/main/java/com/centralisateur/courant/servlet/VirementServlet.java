package com.centralisateur.courant.servlet;

import com.centralisateur.courant.CompteCourantCentralService;
import com.centralisateur.Client;
import com.centralisateur.ClientDAO;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.session.SessionUtilisateur;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/virement")
public class VirementServlet extends HttpServlet {
    
    @EJB
    private CompteCourantCentralService compteService;
    
    @EJB
    private ClientDAO clientDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
        
        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // Charger la liste des comptes et clients
            List<CompteCourant> comptes = compteService.listerComptes(sessionUtilisateur);
            List<Client> clients = compteService.listerClients();
            
            req.setAttribute("comptes", comptes);
            req.setAttribute("listClients", clients);
        } catch (SecurityException ex) {
            req.setAttribute("erreur", "Accès refusé : " + ex.getMessage());
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
        }
        
        req.getRequestDispatcher("/courant/virement.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
        
        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            Long compteDebiteurId = Long.valueOf(req.getParameter("compteDebiteurId"));
            Long compteCrediteurId = Long.valueOf(req.getParameter("compteCrediteurId"));
            Double montant = Double.valueOf(req.getParameter("montant"));
            
            // Vérification que les comptes sont différents
            if (compteDebiteurId.equals(compteCrediteurId)) {
                req.setAttribute("erreur", "Les comptes source et destination doivent être différents");
            } else {
                // Effectuer le virement
                boolean success = compteService.effectuerVirement(compteDebiteurId, compteCrediteurId, montant, sessionUtilisateur);
                
                if (success) {
                    req.setAttribute("success", "Virement de " + montant + "€ effectué avec succès !");
                }
            }
        } catch (SecurityException ex) {
            req.setAttribute("erreur", "Accès refusé : " + ex.getMessage());
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur lors du virement : " + ex.getMessage());
        }
        
        // Recharger les données pour afficher la page
        doGet(req, resp);
    }
}