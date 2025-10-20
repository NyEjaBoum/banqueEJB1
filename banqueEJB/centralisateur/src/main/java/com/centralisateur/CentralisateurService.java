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
    // Supprimez ces lignes car CompteCourantCentralService gère maintenant les comptes courants
    // @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    // private ICompteCourantService compteCourantService;

    @EJB(lookup = "java:global/pret-1.0-SNAPSHOT/PretService!com.pret.service.IPretService")
    private IPretService pretService;

    @EJB
    private TypeMouvementDAO typeMouvementDAO;

    @EJB
    private ClientDAO clientDAO;

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

    ////=>>>> CLIENT
    public void enregistrerClient(Client client) throws Exception {
        // Vérifie si un client existe déjà avec même nom, prénom, email, téléphone
        List<Client> existants = clientDAO.findByInfos(client.getNom(), client.getPrenom(), client.getEmail(), client.getTelephone());
        if (!existants.isEmpty()) {
            throw new Exception("Un client avec ces informations existe déjà !");
        }
        clientDAO.save(client);
    }
}