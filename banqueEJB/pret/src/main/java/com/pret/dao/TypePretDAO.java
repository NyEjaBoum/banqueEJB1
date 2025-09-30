package com.pret.dao;

import com.pret.entity.TypePret;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class TypePretDAO {
    @PersistenceContext
    private EntityManager em;

    public TypePret findById(Long id) {
        return em.find(TypePret.class, id);
    }
    public List<TypePret> findAll() {
        return em.createQuery("SELECT p FROM TypePret p", TypePret.class).getResultList();
    }
}