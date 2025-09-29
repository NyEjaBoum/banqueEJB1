package com.centralisateur;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class ClientDAO {
    @PersistenceContext(unitName = "centralisateurPU")
    private EntityManager em;

    public List<Client> findAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    public Client findById(Long id) {
        return em.find(Client.class, id);
    }

    public void save(Client client) {
        if (client.getId() == 0) { // 0 = nouvel objet, pas encore persisté
            em.persist(client);
        } else {
            em.merge(client);
        }
    }
}