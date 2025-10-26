package com.comptecourant.service;

import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.comptecourant.session.SessionUtilisateur;
import java.util.List;
import java.time.LocalDate;

public interface ICompteCourantService {
    CompteCourant creerCompte(Long clientId, SessionUtilisateur session);
    MouvementCourant ajouterMouvement(Long compteId, Double montant, int type, String devise, LocalDate dateMouvement, SessionUtilisateur session);
    Double getSolde(Long compteId, SessionUtilisateur session);
    List<MouvementCourant> listerMouvements(Long compteId, SessionUtilisateur session);
    List<CompteCourant> listerComptes(SessionUtilisateur session);
    List<CompteCourant> findByClientId(Long clientId, SessionUtilisateur session);
    boolean effectuerVirement(Long compteDebiteurId, Long compteCrediteurId, Double montant, SessionUtilisateur session);
    void updateMouvement(Long mouvementId, int nouveauStatut, SessionUtilisateur session);
    void checkPermission(SessionUtilisateur session, String table, String action);
    boolean hasPermission(SessionUtilisateur session, String table, String action);
   List<MouvementCourant> listerMouvementsEnAttente(SessionUtilisateur session);

}