package com.centralisateur;

import com.comptecourant.service.ICompteCourantService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;

import com.pret.service.IPretService;
import com.pret.entity.Pret;
import com.pret.entity.TypePret;
import com.pret.entity.Remboursement;

import jakarta.ejb.EJB;
import java.util.List;
import jakarta.ejb.Stateless;

@Stateless
public class CentralisateurService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantService;

    @EJB(lookup = "java:global/pret-1.0-SNAPSHOT/PretService!com.pret.service.IPretService")
    private IPretService pretService;

    @EJB
    private TypeMouvementDAO typeMouvementDAO;

    @EJB
    private ClientDAO clientDAO;

    public Double getSolde(Long compteId) {
        return compteCourantService.getSolde(compteId);
    }

    public CompteCourant creerCompte(Long clientId) {
        return compteCourantService.creerCompte(clientId);
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, int type) {
        return compteCourantService.ajouterMouvement(compteId, montant, type);
    }

    public List<MouvementCourant> listerMouvementsCourant(Long compteId) {
        return compteCourantService.listerMouvements(compteId);
    }

    public List<CompteCourant> listerComptes(){
        return compteCourantService.listerComptes();
    }

    public List<TypeMouvement> listerTypesMouvement() {
        return typeMouvementDAO.findAll();
    }

    public List<Client> listerClients() {
        return clientDAO.findAll();
    }

    ////=====================================> PRET
    public Pret creerPret(Pret pret) {
        return pretService.creerPret(pret);
    }

    public Remboursement rembourserPret(Long pretId, Double montant) {
        return pretService.rembourserPret(pretId, montant);
    }

    public List<Remboursement> historiqueRemboursements(Long pretId) {
        return pretService.historiqueRemboursements(pretId);
    }

    public List<Pret> listerPrets() {
        return pretService.listerPrets();
    }

    public List<TypePret> listerTypesPret() {
        return pretService.listerTypesPret();
    }

}