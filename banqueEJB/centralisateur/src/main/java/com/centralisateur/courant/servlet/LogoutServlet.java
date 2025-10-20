package com.centralisateur.courant.servlet;

import com.comptecourant.service.IAuthService;
import com.comptecourant.session.SessionUtilisateur;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @EJB
    private IAuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Récupérer la session HTTP
        HttpSession httpSession = req.getSession(false);
        
        if (httpSession != null) {
            // Récupérer la session utilisateur
            SessionUtilisateur sessionUtilisateur = 
                (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
            
            if (sessionUtilisateur != null) {
                try {
                    // Appeler la déconnexion sur l'EJB stateful
                    authService.logout();
                } catch (Exception e) {
                    // Log l'erreur mais continue la déconnexion
                    System.err.println("Erreur lors de la déconnexion EJB: " + e.getMessage());
                }
            }
            
            // Invalider la session HTTP
            httpSession.invalidate();
        }
        
        // Rediriger vers la page de login avec message
        resp.sendRedirect(req.getContextPath() + "/login?message=disconnected");
    }
}