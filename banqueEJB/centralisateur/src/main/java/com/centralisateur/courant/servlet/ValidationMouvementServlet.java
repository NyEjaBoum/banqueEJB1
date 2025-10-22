package com.centralisateur.courant.servlet;

import com.centralisateur.courant.CompteCourantCentralService;
import com.comptecourant.session.SessionUtilisateur;
import com.comptecourant.entity.MouvementCourant;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/updateMouvement")
public class ValidationMouvementServlet extends HttpServlet {

    @EJB
    private CompteCourantCentralService compteService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");

        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<MouvementCourant> mouvements = compteService.listerMouvementsEnAttente(sessionUtilisateur);
            req.setAttribute("mouvements", mouvements);
            req.getRequestDispatcher("/courant/validationMouvements.jsp").forward(req, resp);
        } catch (SecurityException ex) {
            req.setAttribute("erreur", ex.getMessage());
            // Reste sur la page précédente (pas de forward ici)
            req.getRequestDispatcher("/accueil.jsp").forward(req, resp); // ou une autre page d'accueil
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
            req.getRequestDispatcher("/accueil.jsp").forward(req, resp); // ou une autre page d'accueil
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");


        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Long mouvementId = Long.valueOf(req.getParameter("id"));
        int nouveauStatut = Integer.parseInt(req.getParameter("statut"));

        try {
            compteService.updateMouvement(mouvementId, nouveauStatut, sessionUtilisateur);
            req.setAttribute("success", "Mouvement traité avec succès !");
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
        }

        // Recharger la liste des mouvements en attente
        req.getRequestDispatcher("/courant/validationMouvements.jsp").forward(req, resp);
    }
}