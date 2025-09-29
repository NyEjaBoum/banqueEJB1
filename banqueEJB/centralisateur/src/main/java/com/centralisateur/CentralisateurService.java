package com.centralisateur;

import com.comptecourant.service.ICompteCourantService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import jakarta.ejb.EJB;
import java.util.List;
import jakarta.ejb.Stateless;

@Stateless
public class CentralisateurService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantService;

    public Double getSolde(Long compteId) {
        return compteCourantService.getSolde(compteId);
    }

    public CompteCourant creerCompte(Long clientId) {
        return compteCourantService.creerCompte(clientId);
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, MouvementCourant.TypeMouvement type) {
        return compteCourantService.ajouterMouvement(compteId, montant, type);
    }

    public List<MouvementCourant> listerMouvementsCourant(Long compteId) {
        return compteCourantService.listerMouvements(compteId);
    }

    public List<CompteCourant> listerComptes(){
        return compteCourantService.listerComptes();
    }

}