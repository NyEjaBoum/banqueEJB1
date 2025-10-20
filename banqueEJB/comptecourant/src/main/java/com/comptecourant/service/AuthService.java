package com.comptecourant.service;

import com.comptecourant.entity.Utilisateur;
import com.comptecourant.entity.Direction;
import com.comptecourant.entity.ActionRole;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import java.util.List;

@Stateless
public class AuthService {
    @PersistenceContext(unitName = "compteCourantPU")
    private EntityManager em;

    public Utilisateur authentifier(String email, String motDePasse) {
        try {
            return em.createQuery(
                "SELECT u FROM Utilisateur u JOIN FETCH u.direction WHERE u.email = :email AND u.motDePasse = :motDePasse",
                Utilisateur.class)
                .setParameter("email", email)
                .setParameter("motDePasse", motDePasse)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ActionRole> getAllActionRoles() {
        return em.createQuery("SELECT ar FROM ActionRole ar", ActionRole.class).getResultList();
    }

    public List<Direction> getAllDirections() {
        return em.createQuery("SELECT d FROM Direction d", Direction.class).getResultList();
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }
}