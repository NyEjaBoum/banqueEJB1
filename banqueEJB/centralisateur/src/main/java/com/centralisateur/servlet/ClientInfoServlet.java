package com.centralisateur.servlet;

import com.centralisateur.Client;
import com.centralisateur.ClientDAO;
import com.centralisateur.depot.CompteDepot;
import com.comptecourant.entity.CompteCourant;
import com.pret.entity.Pret;
import com.centralisateur.depot.CompteDepotService;
import com.centralisateur.courant.CompteCourantCentralService;
import com.centralisateur.pret.PretCentralService;

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
    private CompteDepotService depotService;
    @EJB
    private CompteCourantCentralService courantService;
    @EJB
    private PretCentralService pretService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long clientId = Long.valueOf(req.getParameter("clientId"));
        Client client = clientDAO.findById(clientId);
        List<CompteDepot> depots = depotService.findByClientId(clientId);
        List<CompteCourant> courants = courantService.findByClientId(clientId);
        List<Pret> prets = pretService.findByClientId(clientId);

        req.setAttribute("client", client);
        req.setAttribute("depots", depots);
        req.setAttribute("courants", courants);
        req.setAttribute("prets", prets);

        req.getRequestDispatcher("/clients/infoClient.jsp").forward(req, resp);
    }
}