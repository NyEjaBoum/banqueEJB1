package com.centralisateur;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class TypeMouvementDAO {
    @PersistenceContext(unitName = "centralisateurPU")
    private EntityManager em;

    public List<TypeMouvement> findAll() {
        return em.createQuery("SELECT t FROM TypeMouvement t", TypeMouvement.class).getResultList();
    }
}