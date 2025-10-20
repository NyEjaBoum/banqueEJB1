package com.comptecourant.service;

import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.comptecourant.session.SessionUtilisateur;
import java.util.List;

public interface ICompteCourantService {
    CompteCourant creerCompte(Long clientId, SessionUtilisateur session);
    MouvementCourant ajouterMouvement(Long compteId, Double montant, int type, SessionUtilisateur session);
    Double getSolde(Long compteId, SessionUtilisateur session);
    List<MouvementCourant> listerMouvements(Long compteId, SessionUtilisateur session);
    List<CompteCourant> listerComptes(SessionUtilisateur session);
    List<CompteCourant> findByClientId(Long clientId, SessionUtilisateur session);
    boolean effectuerVirement(Long compteDebiteurId, Long compteCrediteurId, Double montant, SessionUtilisateur session);
}