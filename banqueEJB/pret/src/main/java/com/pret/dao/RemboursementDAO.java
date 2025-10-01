package com.pret.dao;

import com.pret.entity.Remboursement;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class RemboursementDAO {
    @PersistenceContext
    private EntityManager em;

    public Remboursement save(Remboursement r) {
        if (r.getId() == null) {
            em.persist(r);
            return r;
        } else {
            return em.merge(r);
        }
    }

    public List<Remboursement> findByPretId(Long pretId) {
        return em.createQuery("SELECT r FROM Remboursement r WHERE r.pretId = :pretId", Remboursement.class)
            .setParameter("pretId", pretId)
            .getResultList();
    }

    public Double totalCapitalRemboursementByPretId(Long pretId) {
        Double total = em.createQuery(
            "SELECT COALESCE(SUM(r.capitalRembourse), 0) FROM Remboursement r WHERE r.pretId = :pretId", Double.class)
            .setParameter("pretId", pretId)
            .getSingleResult();
        return total;
    }
}