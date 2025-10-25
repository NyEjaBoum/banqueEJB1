package com.comptecourant.service;

import com.comptecourant.entity.Utilisateur;
import com.comptecourant.session.SessionUtilisateur;
import java.util.List;
import jakarta.ejb.Remote;

@jakarta.ejb.Remote
public interface IAuthService {
    SessionUtilisateur login(String email, String motDePasse);
    List<Utilisateur> listerUtilisateurs();
    void logout();
    SessionUtilisateur getCurrentSession();
    boolean isLoggedIn();
}