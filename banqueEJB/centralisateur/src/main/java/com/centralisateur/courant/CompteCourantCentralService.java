package com.centralisateur.courant;

import com.comptecourant.service.ICompteCourantService;
import com.comptecourant.entity.CompteCourant;
import com.comptecourant.entity.MouvementCourant;
import com.centralisateur.TypeMouvement;
import com.centralisateur.Client;
import com.centralisateur.TypeMouvementDAO;
import com.centralisateur.ClientDAO;
import com.comptecourant.session.SessionUtilisateur;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class CompteCourantCentralService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantEJB;

    @EJB
    private TypeMouvementDAO typeMouvementDAO;

    @EJB
    private ClientDAO clientDAO;

    public Double getSolde(Long compteId, SessionUtilisateur session) {
        return compteCourantEJB.getSolde(compteId, session);
    }

    public CompteCourant creerCompte(Long clientId, SessionUtilisateur session) {
        return compteCourantEJB.creerCompte(clientId, session);
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, int type, SessionUtilisateur session) {
        return compteCourantEJB.ajouterMouvement(compteId, montant, type, session);
    }

    public List<MouvementCourant> listerMouvementsCourant(Long compteId, SessionUtilisateur session) {
        return compteCourantEJB.listerMouvements(compteId, session);
    }

    public List<CompteCourant> listerComptes(SessionUtilisateur session) {
        return compteCourantEJB.listerComptes(session);
    }

    public List<CompteCourant> findByClientId(Long clientId, SessionUtilisateur session){
        return compteCourantEJB.findByClientId(clientId, session);
    }

    public List<TypeMouvement> listerTypesMouvement() {
        return typeMouvementDAO.findAll();
    }

    public List<Client> listerClients() {
        return clientDAO.findAll();
    }

    public boolean effectuerVirement(Long compteDebiteurId, Long compteCrediteurId, Double montant, SessionUtilisateur session) {
        return compteCourantEJB.effectuerVirement(compteDebiteurId, compteCrediteurId, montant, session);
    }
}