package com.comptecourant.dao;

import com.comptecourant.entity.MouvementCourant;
import com.comptecourant.entity.CompteCourant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class MouvementCourantDAO {
    @PersistenceContext
    private EntityManager em;

    public MouvementCourant findById(Long id) {
        return em.find(MouvementCourant.class, id);
    }

    public void save(MouvementCourant mouvement) {
        if (mouvement.getId() == null) {
            em.persist(mouvement);
        } else {
            em.merge(mouvement);
        }
    }

    public List<MouvementCourant> findAll() {
        return em.createQuery("SELECT m FROM MouvementCourant m", MouvementCourant.class).getResultList();
    }

    public List<MouvementCourant> findByCompteOrderByDate(CompteCourant compte) {
        return em.createQuery("SELECT m FROM MouvementCourant m WHERE m.compte = :compte ORDER BY m.dateMouvement ASC", MouvementCourant.class)
            .setParameter("compte", compte)
            .getResultList();
    }
}
