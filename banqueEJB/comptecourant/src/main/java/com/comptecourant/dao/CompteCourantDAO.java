package com.comptecourant.dao;

import com.comptecourant.entity.CompteCourant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CompteCourantDAO {
    @PersistenceContext
    private EntityManager em;

    public CompteCourant findById(Long id) {
        return em.find(CompteCourant.class, id);
    }

    public void save(CompteCourant compte) {
        if (compte.getId() == null) {
            em.persist(compte);
        } else {
            em.merge(compte);
        }
    }

    public List<CompteCourant> findAll() {
        return em.createQuery("SELECT c FROM CompteCourant c", CompteCourant.class).getResultList();
    }
}
