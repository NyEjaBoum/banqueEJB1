package com.centralisateur.courant.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.comptecourant.session.SessionUtilisateur;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession httpSession = req.getSession();
    SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
    if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
        resp.sendRedirect(req.getContextPath() + "/login");
        return;
    }
    req.setAttribute("sessionUtilisateur", sessionUtilisateur);
    req.getRequestDispatcher("/accueil.jsp").forward(req, resp);
}
}