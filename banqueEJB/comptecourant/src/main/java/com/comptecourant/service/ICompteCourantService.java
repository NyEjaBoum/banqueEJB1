package com.comptecourant.service;

import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import java.util.List;

public interface ICompteCourantService {
    CompteCourant creerCompte(Long clientId);
    MouvementCourant ajouterMouvement(Long compteId, Double montant, int type);
    Double getSolde(Long compteId);
    List<MouvementCourant> listerMouvements(Long compteId);
    List<CompteCourant> listerComptes();

}