package com.comptecourant.service;

import com.comptecourant.entity.Utilisateur;
import com.comptecourant.entity.Direction;
import com.comptecourant.entity.ActionRole;
import com.comptecourant.session.SessionUtilisateur;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
import java.util.List;

@Stateful
public class LoginService implements IAuthService {
    @EJB
    private AuthService authService;

    private SessionUtilisateur sessionUtilisateur;

    @Override
    public SessionUtilisateur login(String email, String motDePasse) {
        Utilisateur user = authService.authentifier(email, motDePasse);
        if (user != null) {
            List<Direction> directions = authService.getAllDirections();
            List<ActionRole> actionsRoles = authService.getAllActionRoles();
            sessionUtilisateur = new SessionUtilisateur();
            sessionUtilisateur.initSession(user, directions, actionsRoles);
            return sessionUtilisateur;
        }
        return null;
    }

    @Override
    @Remove
    public void logout() {
        if (sessionUtilisateur != null) {
            sessionUtilisateur.logout(); // Appelle la méthode @Remove de SessionUtilisateur
        }
        sessionUtilisateur = null;
        // Le bean LoginService sera détruit après cette méthode (@Remove)
    }

    @Override
    public SessionUtilisateur getCurrentSession() {
        return sessionUtilisateur;
    }

    @Override
    public boolean isLoggedIn() {
        return sessionUtilisateur != null && sessionUtilisateur.isConnecte();
    }

    @Override
    public List<Utilisateur> listerUtilisateurs() {
        return authService.getAllUtilisateurs();
    }
}