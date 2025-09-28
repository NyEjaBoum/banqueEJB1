package com.comptecourant.dao;

import com.comptecourant.entity.Client;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClientDAO {
    @PersistenceContext
    private EntityManager em;

    public Client findById(Long id) {
        return em.find(Client.class, id);
    }

    public void save(Client client) {
        if (client.getId() == null) {
            em.persist(client);
        } else {
            em.merge(client);
        }
    }

    public List<Client> findAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }
}
