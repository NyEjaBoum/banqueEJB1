package com.centralisateur.courant;

import com.comptecourant.service.ICompteCourantService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.centralisateur.TypeMouvement;
import com.centralisateur.Client;
import com.centralisateur.TypeMouvementDAO;
import com.centralisateur.ClientDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class CompteCourantCentralService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantEJB;

    @EJB
    private TypeMouvementDAO typeMouvementDAO;

    @EJB
    private ClientDAO clientDAO;

    public Double getSolde(Long compteId) {
        return compteCourantEJB.getSolde(compteId);
    }

    public CompteCourant creerCompte(Long clientId) {
        return compteCourantEJB.creerCompte(clientId);
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, int type) {
        return compteCourantEJB.ajouterMouvement(compteId, montant, type);
    }

    public List<MouvementCourant> listerMouvementsCourant(Long compteId) {
        return compteCourantEJB.listerMouvements(compteId);
    }

    public List<CompteCourant> listerComptes() {
        return compteCourantEJB.listerComptes();
    }

    public List<TypeMouvement> listerTypesMouvement() {
        return typeMouvementDAO.findAll();
    }

    public List<Client> listerClients() {
        return clientDAO.findAll();
    }
}