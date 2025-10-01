package com.pret.service;

import com.pret.entity.Pret;
import com.pret.entity.TypePret;
import com.pret.entity.Remboursement;
import java.util.List;

public interface IPretService {
    Pret creerPret(Pret pret);
    Remboursement rembourserPret(Long pretId, Double montant);
    List<Remboursement> historiqueRemboursements(Long pretId);
    List<Pret> listerPrets();
    List<TypePret> listerTypesPret();

}