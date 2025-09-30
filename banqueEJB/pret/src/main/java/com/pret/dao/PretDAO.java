package com.pret.dao;

import com.pret.entity.Pret;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class PretDAO {
    @PersistenceContext
    private EntityManager em;

    public Pret save(Pret pret) {
        if (pret.getId() == null) {
            em.persist(pret);
            return pret;
        } else {
            return em.merge(pret);
        }
    }

    public Pret findById(Long id) {
        return em.find(Pret.class, id);
    }

    public List<Pret> findAll() {
        return em.createQuery("SELECT p FROM Pret p", Pret.class).getResultList();
    }
}