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
        if (client.getId() == null) { // null = nouvel objet, pas encore persisté
            em.persist(client);
        } else {
            em.merge(client);
        }
    }

    public Client update(Client client){
        return em.merge(client);
    }
    
    public List<Client> findByInfos(String nom, String prenom, String email, String telephone) {
        return em.createQuery(
            "SELECT c FROM Client c WHERE c.nom = :nom AND c.prenom = :prenom AND c.email = :email AND c.telephone = :telephone",
            Client.class)
            .setParameter("nom", nom)
            .setParameter("prenom", prenom)
            .setParameter("email", email)
            .setParameter("telephone", telephone)
            .getResultList();
    }
}