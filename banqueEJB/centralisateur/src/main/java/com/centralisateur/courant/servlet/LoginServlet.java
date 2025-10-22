package com.centralisateur.courant.servlet;

import com.comptecourant.service.IAuthService;
import com.comptecourant.session.SessionUtilisateur;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

@EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/LoginService!com.comptecourant.service.IAuthService")
private IAuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/courant/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String motDePasse = req.getParameter("motDePasse");

        SessionUtilisateur sessionUtilisateur = authService.login(email, motDePasse);

        if (sessionUtilisateur != null && sessionUtilisateur.isConnecte()) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("sessionUtilisateur", sessionUtilisateur);
            resp.sendRedirect(req.getContextPath() + "/accueil");
        } else {
            req.setAttribute("error", "Email ou mot de passe incorrect");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}