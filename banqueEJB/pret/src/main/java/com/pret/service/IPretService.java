package com.pret.service;

import com.pret.entity.Pret;
import com.pret.entity.TypePret;
import com.pret.entity.Remboursement;
import java.util.List;

@jakarta.ejb.Remote
public interface IPretService {
    Pret creerPret(Pret pret);
    Remboursement rembourserPret(Long pretId, Double montant);
    List<Remboursement> historiqueRemboursements(Long pretId);
    List<Pret> listerPrets();
    List<TypePret> listerTypesPret();
    List<Pret> findByClientId(Long clientId);
    Double montantRestant(Long pretId);
    Double montantMensuelRemboursement(Long pretId);
}