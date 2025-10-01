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
        if (typePret == null) {
            throw new IllegalArgumentException("Type de prêt introuvable");
        }
        if (pret.getMontant() == null || pret.getMontant() <= 0) {
            throw new IllegalArgumentException("Montant du prêt invalide");
        }
        if (pret.getMontant() > typePret.getMontant()) {
            throw new IllegalArgumentException("Montant demandé supérieur au montant maximal autorisé pour ce type de prêt");
        }
        pret.setTauxInteret(typePret.getInteret());
        if (pret.getDateDebut() == null) {
            throw new IllegalArgumentException("Date de début du prêt requise");
        }
        pret.setDateFin(pret.getDateDebut().plusMonths(typePret.getNbMoisRemboursement()));
        pret.setEtat("ENCOURS");
        return pretDAO.save(pret);
    }

    @Override
    public Remboursement rembourserPret(Long pretId, Double montant) {
        Pret lePret = pretDAO.findById(pretId);
        if (lePret == null) {
            throw new IllegalArgumentException("Prêt introuvable");
        }
        double capitalRestant;
        List<Remboursement> remboursements = remboursementDAO.findByPretId(pretId);
        if (remboursements != null && !remboursements.isEmpty()) { //raha misy remboursement efa natao
            Double totalRembourse = remboursementDAO.totalCapitalRemboursementByPretId(pretId);
            capitalRestant = lePret.getMontant() - totalRembourse;
        } else { //raha tsy misy remboursement 
            capitalRestant = lePret.getMontant();
        }
        double tauxMensuel = lePret.getTauxInteret() / 100 / 12;
        double interetPayes = capitalRestant * tauxMensuel;
        double capitalRembourse = montant - interetPayes;

        Remboursement remboursement = new Remboursement();
        remboursement.setPretId(pretId);
        remboursement.setMontant(montant);
        remboursement.setInteretPayes(interetPayes);
        remboursement.setCapitalRembourse(capitalRembourse);
        remboursement.setDateRemboursement(LocalDate.now());
        remboursementDAO.save(remboursement);

        double capitalRembourseTotal = remboursementDAO.totalCapitalRemboursementByPretId(pretId);
        if (capitalRembourseTotal >= lePret.getMontant()) {
            lePret.setEtat("REMBOURSE");
            pretDAO.save(lePret);
        }
        return remboursement;
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