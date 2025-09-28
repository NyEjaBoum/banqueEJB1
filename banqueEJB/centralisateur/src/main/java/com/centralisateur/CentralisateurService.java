package com.centralisateur;

import com.comptecourant.service.ICompteCourantService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import jakarta.ejb.EJB;
import java.util.List;

public class CentralisateurService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantService;

    public CompteCourant creerCompteCourant(Long clientId) {
        return compteCourantService.creerCompte(clientId);
    }

    public MouvementCourant ajouterMouvementCourant(Long compteId, Double montant, MouvementCourant.TypeMouvement type) {
        return compteCourantService.ajouterMouvement(compteId, montant, type);
    }

    public Double getSoldeCourant(Long compteId) {
        return compteCourantService.getSolde(compteId);
    }

    public List<MouvementCourant> listerMouvementsCourant(Long compteId) {
        return compteCourantService.listerMouvements(compteId);
    }

    public List<CompteCourant> listerComptes(){
        return compteCourantService.listerComptes();
    }

}