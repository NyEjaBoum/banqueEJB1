package com.comptecourant.web;

import com.comptecourant.dao.CompteCourantDAO;
import com.comptecourant.entity.CompteCourant;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/comptes")
public class CompteCourantServlet extends HttpServlet {
    @EJB
    private CompteCourantDAO compteDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CompteCourant> comptes = compteDAO.findAll();
        req.setAttribute("comptes", comptes);
        req.getRequestDispatcher("/comptes.jsp").forward(req, resp);
    }
}
