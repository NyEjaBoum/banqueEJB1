package com.pret.service;

import com.pret.entity.Pret;
import com.pret.entity.TypePret;
import com.pret.entity.Remboursement;
import com.pret.dao.PretDAO;
import com.pret.dao.RemboursementDAO;
import com.pret.dao.TypePretDAO;
import jakarta.ejb.Stateless;
import jakarta.ejb.Remote;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Stateless
@Remote(IPretService.class)
public class PretService implements IPretService {

    @Inject
    private PretDAO pretDAO;
    @Inject
    private TypePretDAO typePretDAO;
    @Inject
    private RemboursementDAO remboursementDAO;

    @Override
    public Pret creerPret(Pret pret) {
        TypePret typePret = typePretDAO.findById(pret.getTypePretId());
        if (typePret == null) throw new IllegalArgumentException("Type de prêt introuvable");
        // pret.setMontant(typePret.getMontant()); // montant défini par le type
        pret.setTauxInteret(typePret.getInteret()); // taux défini par le type
        pret.setEtat("ENCOURS");
        return pretDAO.save(pret);
    }

    @Override
    public Remboursement rembourserPret(Long pretId, Double montant, Double interetPayes, Double capitalRembourse) {
        Remboursement r = new Remboursement();
        r.setPretId(pretId);
        r.setMontant(montant);
        r.setInteretPayes(interetPayes);
        r.setCapitalRembourse(capitalRembourse);
        r.setDateRemboursement(LocalDate.now());
        remboursementDAO.save(r);
        // TODO: mettre à jour l'état du prêt si remboursé
        return r;
    }

    @Override
    public List<Remboursement> historiqueRemboursements(Long pretId) {
        return remboursementDAO.findByPretId(pretId);
    }

    @Override
    public List<Pret> listerPrets() {
        return pretDAO.findAll();
    }

    public List<TypePret> listerTypesPret(){
        return typePretDAO.findAll();
    }
}