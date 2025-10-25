package com.centralisateur.courant.servlet;

import com.centralisateur.courant.CompteCourantCentralService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.centralisateur.TypeMouvement;
import com.centralisateur.Client;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.comptecourant.session.SessionUtilisateur;
import com.change.IChangeService;
import java.util.Map;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NamingEnumeration;
import javax.naming.NameClassPair;

@WebServlet("/compte_courant")
public class CompteCourantServlet extends HttpServlet {
    @EJB
    private CompteCourantCentralService compteService;

    // Supprimez cette ligne qui cause l'erreur :
    // @EJB(lookup = "ejb:change-1.0-SNAPSHOT/ChangeService!com.change.IChangeService")
    // private IChangeService changeService;

    // Méthode pour obtenir le service de change depuis Docker
private IChangeService getChangeService() {
    try {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8081");
        props.put(Context.SECURITY_PRINCIPAL, "nyeja"); // Remplace par ton user WildFly Docker
        props.put(Context.SECURITY_CREDENTIALS, "nyeja"); // Remplace par ton mot de passe Docker
props.put("jboss.naming.client.ejb.context", true);
props.put("jboss.sasl.policy.noanonymous", "true");
props.put("jboss.sasl.policy.noplaintext", "false");
props.put("jboss.sasl.policy.nodictionary", "true");
props.put("jboss.sasl.policy.noactive", "true");
props.put("jboss.sasl.policy.forward_secrecy", "true");
props.put("jboss.sasl.policy.credentials", "true");
        Context context = new InitialContext(props);

        // Liste les JNDI disponibles pour debug
        NamingEnumeration<NameClassPair> list = context.list("");
        while (list.hasMore()) {
            NameClassPair nc = list.next();
            System.out.println("JNDI: " + nc.getName() + " -> " + nc.getClassName());
        }

Object obj = context.lookup("change-1.0-SNAPSHOT/ChangeService!com.change.IChangeService");
    System.out.println("Type retourné par le lookup : " + obj.getClass().getName());
    return (IChangeService) obj;
    } catch (Exception e) {
        System.err.println("Impossible de se connecter au service de change Docker : " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");

        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Récupérer les devises depuis Docker
        List<String> devises;
        try {
            IChangeService changeServ = getChangeService();
            devises = changeServ != null ? changeServ.listerDevises() : List.of("MGA", "EUR", "USD");
        } catch (Exception e) {
            devises = List.of("MGA", "EUR", "USD");
            req.setAttribute("info", "Service de change Docker indisponible, devises par défaut utilisées");
        }
        req.setAttribute("devises", devises);

        try {
            List<CompteCourant> comptes = compteService.listerComptes(sessionUtilisateur);
            Map<Long, Double> soldes = new HashMap<>();
            for (CompteCourant compte : comptes) {
                soldes.put(compte.getId(), compteService.getSolde(compte.getId(), sessionUtilisateur));
            }
            req.setAttribute("comptes", comptes);
            req.setAttribute("soldes", soldes);

            List<TypeMouvement> types = compteService.listerTypesMouvement();
            req.setAttribute("typesMouvement", types);

            List<Client> clients = compteService.listerClients();
            req.setAttribute("listClients", clients);

            String action = req.getParameter("action");
            if ("solde".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                Double solde = compteService.getSolde(compteId, sessionUtilisateur);
                req.setAttribute("solde", solde);
            } else if ("historique".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                List<MouvementCourant> mouvements = compteService.listerMouvementsCourant(compteId, sessionUtilisateur);
                req.setAttribute("mouvements", mouvements);
                req.setAttribute("typesMouvement", types);
                req.setAttribute("compteId", compteId);
                req.getRequestDispatcher("/courant/mouvements.jsp").forward(req, resp);
                return;
            }
        } catch (SecurityException ex) {
            req.setAttribute("erreur", "Accès refusé : " + ex.getMessage());
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
        }

        req.getRequestDispatcher("/courant/comptes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionUtilisateur sessionUtilisateur = (SessionUtilisateur) httpSession.getAttribute("sessionUtilisateur");
        
        if (sessionUtilisateur == null || !sessionUtilisateur.isConnecte()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String action = req.getParameter("action");
        try {
            if ("creer".equals(action)) {
                Long clientId = Long.valueOf(req.getParameter("clientId"));
                compteService.creerCompte(clientId, sessionUtilisateur);
                resp.sendRedirect("compte_courant");
            } else if ("mouvement".equals(action)) {
                Long compteId = Long.valueOf(req.getParameter("compteId"));
                Double montant = Double.valueOf(req.getParameter("montant"));
                int type = Integer.parseInt(req.getParameter("type"));
                String devise = req.getParameter("devise");
                compteService.ajouterMouvement(compteId, montant, type, devise, sessionUtilisateur);
                resp.sendRedirect(req.getContextPath() + "/compte_courant");
            }
        } catch (SecurityException ex) {
            req.setAttribute("erreur", "Accès refusé : " + ex.getMessage());
            doGet(req, resp);
        } catch (Exception ex) {
            req.setAttribute("erreur", "Erreur : " + ex.getMessage());
            doGet(req, resp);
        }
    }
}