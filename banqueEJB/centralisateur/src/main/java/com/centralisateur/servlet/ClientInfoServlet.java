package com.centralisateur.servlet;

import com.centralisateur.Client;
import com.centralisateur.ClientDAO;
import com.centralisateur.depot.CompteDepot;
import com.comptecourant.entity.CompteCourant;
import com.pret.entity.Pret;
import com.centralisateur.depot.CompteDepotService;
import com.centralisateur.courant.CompteCourantCentralService;
import com.centralisateur.pret.PretCentralService;
import com.comptecourant.session.SessionUtilisateur;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/clientInfo")
public class ClientInfoServlet extends HttpServlet {
    
    @EJB
    private ClientDAO clientDAO;
    
    @EJB
    private CompteCourantCentralService compteCourantService;
    
    @EJB
    private CompteDepotService compteDepotService;
    
    @EJB
    private PretCentralService pretService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
        
        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            Long clientId = Long.valueOf(req.getParameter("clientId"));
            
            // Récupérer le client
            Client client = clientDAO.findById(clientId);
            req.setAttribute("client", client);
            
            // Récupérer les comptes courants
            List<CompteCourant> courants = compteCourantService.findByClientId(clientId, sessionUtilisateur);
            req.setAttribute("courants", courants);
            
            // Récupérer les comptes dépôt
            List<CompteDepot> depots = compteDepotService.findByClientId(clientId);
            req.setAttribute("depots", depots);
            
            // Récupérer les prêts
            List<Pret> prets = pretService.findByClientId(clientId);
            req.setAttribute("prets", prets);
            
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
        }
        
        req.getRequestDispatcher("/clients/infoClient.jsp").forward(req, resp);
    }
}