package com.centralisateur.servlet;

import com.centralisateur.Client;
import com.centralisateur.ClientDAO;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.centralisateur.CentralisateurService;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    @EJB
    private ClientDAO clientDAO;

    @EJB
    private CentralisateurService centralisateurService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("edit".equals(action)){
            Long id = Long.valueOf(req.getParameter("id"));
            Client clientEdit = clientDAO.findById(id);
            req.setAttribute("clientEdit", clientEdit);
        }
        List<Client> clients = clientDAO.findAll();
        req.setAttribute("clients", clients);   
        req.getRequestDispatcher("/clients/ajouterClient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String telephone = req.getParameter("telephone");

            if (idStr != null && !idStr.isEmpty()) {
                // UPDATE
                Long id = Long.valueOf(idStr);
                Client client = clientDAO.findById(id);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setEmail(email);
                client.setTelephone(telephone);
                clientDAO.update(client);
            } else {
                // CREATE
                Client client = new Client();
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setEmail(email);
                client.setTelephone(telephone);
                centralisateurService.enregistrerClient(client);
            }
            resp.sendRedirect(req.getContextPath() + "/clients?success=1");
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
            req.getRequestDispatcher("/clients/ajouterClient.jsp").forward(req, resp);
        }
    }
}