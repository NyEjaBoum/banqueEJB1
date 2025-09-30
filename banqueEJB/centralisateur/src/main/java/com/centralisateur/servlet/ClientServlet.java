package com.centralisateur.servlet;

import com.centralisateur.Client;
import com.centralisateur.ClientDAO;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    @EJB
    private ClientDAO clientDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/clients/ajouterClient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String telephone = req.getParameter("telephone");
            int numeroClient = Integer.parseInt(req.getParameter("numeroClient"));

            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setTelephone(telephone);
            client.setNumeroClient(numeroClient);

            clientDAO.save(client);
            req.setAttribute("success", "Client ajouté avec succès !");
        } catch (Exception ex) {
            req.setAttribute("erreur", ex.getMessage());
        }
        req.getRequestDispatcher("/clients/ajouterClient.jsp").forward(req, resp);
    }
}