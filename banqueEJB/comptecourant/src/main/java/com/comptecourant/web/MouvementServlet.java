package com.comptecourant.web;

import com.comptecourant.dao.CompteCourantDAO;
import com.comptecourant.dao.MouvementCourantDAO;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/mouvement", "/mouvements"})
public class MouvementServlet extends HttpServlet {
    @EJB
    private CompteCourantDAO compteDAO;
    @EJB
    private MouvementCourantDAO mouvementDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long compteId = Long.valueOf(req.getParameter("compteId"));
        Double montant = Double.valueOf(req.getParameter("montant"));
        int typeId = Integer.parseInt(req.getParameter("type")); // type = id du type_mouvement
        CompteCourant compte = compteDAO.findById(compteId);

        // ENTREE = 1, SORTIE = 2
        if (compte != null && montant > 0 && (typeId == 1 || typeId == 2)) {
            // Vérification du solde pour SORTIE
            if (typeId == 2 && compte.getSolde() < montant) {
                req.setAttribute("erreur", "Solde insuffisant pour effectuer ce retrait");
                req.getRequestDispatcher("/comptes.jsp").forward(req, resp);
                return;
            }

            MouvementCourant mvt = new MouvementCourant();
            mvt.setCompte(compte);
            mvt.setMontant(montant);
            mvt.setTypeMouvementId(typeId);
            mvt.setDateMouvement(java.time.LocalDate.now());
            mouvementDAO.save(mvt);

            // Mise à jour du solde
            double nouveauSolde = compte.getSolde();
            if (typeId == 1) { // ENTREE
                nouveauSolde += montant;
            } else { // SORTIE
                nouveauSolde -= montant;
            }
            compte.setSolde(nouveauSolde);
            compte.setDateMaj(java.time.LocalDate.now());
            compteDAO.save(compte);
        }
        resp.sendRedirect("comptes");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long compteId = Long.valueOf(req.getParameter("compteId"));
        CompteCourant compte = compteDAO.findById(compteId);
        List<MouvementCourant> mouvements = mouvementDAO.findByCompteOrderByDate(compte);
        req.setAttribute("compte", compte);
        req.setAttribute("mouvements", mouvements);
        req.getRequestDispatcher("/mouvements.jsp").forward(req, resp);
    }
}