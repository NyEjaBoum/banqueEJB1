package com.centralisateur.pret;

import com.pret.service.IPretService;
import com.pret.entity.Pret;
import com.pret.entity.TypePret;
import com.pret.entity.Remboursement;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class PretCentralService {
    @EJB(lookup = "java:global/pret-1.0-SNAPSHOT/PretService!com.pret.service.IPretService")
    private IPretService pretEJB;

    public Pret creerPret(Pret pret) {
        return pretEJB.creerPret(pret);
    }

    public Remboursement rembourserPret(Long pretId, Double montant) {
        return pretEJB.rembourserPret(pretId, montant);
    }

    public List<Remboursement> historiqueRemboursements(Long pretId) {
        return pretEJB.historiqueRemboursements(pretId);
    }

    public List<Pret> listerPrets() {
        return pretEJB.listerPrets();
    }

    public List<TypePret> listerTypesPret() {
        return pretEJB.listerTypesPret();
    }
}