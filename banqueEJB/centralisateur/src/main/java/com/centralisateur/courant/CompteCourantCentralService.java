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
import com.change.IChangeService;
@Stateless
public class CompteCourantCentralService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantEJB;

    @EJB
    private TypeMouvementDAO typeMouvementDAO;

    @EJB
    private ClientDAO clientDAO;

    @EJB(lookup = "java:global/change-1.0-SNAPSHOT/ChangeService!com.change.IChangeService")
    private IChangeService changeService;

    public Double getSolde(Long compteId, SessionUtilisateur session) {
        List<MouvementCourant> mouvements = compteCourantEJB.listerMouvements(compteId, session);
        double solde = 0.0;
        for (MouvementCourant mvt : mouvements) {
            if (mvt.getStatut() == 1) { // validé
                double montantAriary;
                try {
                    montantAriary = changeService.convertirEnAriary(mvt.getDevise(), mvt.getMontant(), mvt.getDateMouvement());
                } catch (Exception e) {
                    montantAriary = 0.0; // ou loguer l'erreur
                }
                switch (mvt.getTypeMouvementId()) {
                    case 1: // DEPOT
                    case 4: // VIREMENT_ENTRANT
                        solde += montantAriary;
                        break;
                    case 2: // RETRAIT
                    case 3: // VIREMENT_SORTANT
                        solde -= montantAriary;
                        break;
                    default:
                        break;
                }
            }
        }
        return solde;
    }

    public CompteCourant creerCompte(Long clientId, SessionUtilisateur session) {
        return compteCourantEJB.creerCompte(clientId, session);
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, int type, String devise, SessionUtilisateur session) {
        return compteCourantEJB.ajouterMouvement(compteId, montant, type, devise, session);
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

    public void updateMouvement(Long mouvementId, int nouveauStatut, SessionUtilisateur session) {
        compteCourantEJB.updateMouvement(mouvementId, nouveauStatut, session);
    }

    public boolean hasPermission(SessionUtilisateur session, String table, String action){
        return compteCourantEJB.hasPermission(session, table, action);
    }
    public List<MouvementCourant> listerMouvementsEnAttente(SessionUtilisateur session) {
        return compteCourantEJB.listerMouvementsEnAttente(session);
    }
}