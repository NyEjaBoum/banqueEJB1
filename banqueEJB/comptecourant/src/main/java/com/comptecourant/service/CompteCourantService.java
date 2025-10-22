package com.comptecourant.service;

import com.comptecourant.entity.*;
import com.comptecourant.dao.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import com.comptecourant.session.SessionUtilisateur;
import jakarta.ejb.Remote;

@Stateless
@Remote(ICompteCourantService.class)
public class CompteCourantService implements ICompteCourantService {
    @Inject
    private CompteCourantDAO compteDAO;

    @Inject
    private MouvementCourantDAO mouvementDAO;

    public CompteCourant creerCompte(Long clientId, SessionUtilisateur session) {
        checkPermission(session, "compte_courant", "CREATE");
        CompteCourant compte = new CompteCourant();
        compte.setClientId(clientId);
        compte.setSolde(0.0);
        compte.setDateMaj(LocalDate.now());
        compteDAO.save(compte);
        return compte;
    }

    public MouvementCourant creerMouvement(CompteCourant compte, Double montant, int typeMouvementId) {
        MouvementCourant mouvement = new MouvementCourant();
        mouvement.setCompte(compte);
        mouvement.setMontant(montant);
        mouvement.setTypeMouvementId(typeMouvementId);
        mouvement.setDateMouvement(LocalDate.now());
        mouvement.setStatut(0); // EN_ATTENTE
        mouvementDAO.save(mouvement);
        return mouvement;
    }

    public boolean effectuerVirement(Long compteDebiteurId, Long compteCrediteurId, Double montant, SessionUtilisateur session){
        checkPermission(session, "mouvement_courant", "CREATE");
        CompteCourant compteDebiteur = compteDAO.findById(compteDebiteurId);
        CompteCourant compteCrediteur = compteDAO.findById(compteCrediteurId);

        if (compteDebiteur == null || compteCrediteur == null) {
            throw new BusinessException("Compte(s) introuvable(s)");
        }
        if (montant <= 0) {
            throw new BusinessException("Montant doit être positif");
        }
        if (compteDebiteur.getSolde() < montant) {
            throw new BusinessException("Solde insuffisant pour effectuer le virement");
        }

        creerMouvement(compteDebiteur, montant, 3); // VIREMENT_SORTANT
        creerMouvement(compteCrediteur, montant, 4); // VIREMENT_ENTRANT

        return true;
    }

    public List<CompteCourant> findByClientId(Long clientId, SessionUtilisateur session){
        checkPermission(session, "compte_courant", "READ");
        List<CompteCourant> list = new ArrayList<>();
        List<CompteCourant> all = listerComptes(session);
        for (CompteCourant c : all) {
            if (c.getClientId() != null && c.getClientId().equals(clientId)) list.add(c);
        }
        return list;
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, int type, SessionUtilisateur session) {
        checkPermission(session, "mouvement_courant", "CREATE");
        CompteCourant compte = compteDAO.findById(compteId);
        if (compte == null) throw new BusinessException("Compte introuvable");
        if (montant == null || montant <= 0) throw new BusinessException("Montant doit être positif");
        if (type != 1 && type != 2) throw new BusinessException("Type de mouvement non autorisé");
        if (type == 2 && compte.getSolde() < montant) {
            throw new BusinessException("Solde insuffisant pour effectuer ce retrait");
        }
        return creerMouvement(compte, montant, type);
    }

    public Double getSolde(Long compteId, SessionUtilisateur session) {
        checkPermission(session, "compte_courant", "READ");
        CompteCourant compte = compteDAO.findById(compteId);
        if (compte == null) throw new BusinessException("Compte introuvable");
        List<MouvementCourant> mouvements = mouvementDAO.findByCompteOrderByDate(compte);
        double solde = 0.0;
        for (MouvementCourant mvt : mouvements) {
            if (mvt.getStatut() == 1) { // Seulement les mouvements validés
                switch (mvt.getTypeMouvementId()) {
                    case 1: // DEPOT
                    case 4: // VIREMENT_ENTRANT
                        solde += mvt.getMontant();
                        break;
                    case 2: // RETRAIT
                    case 3: // VIREMENT_SORTANT
                        solde -= mvt.getMontant();
                        break;
                    default:
                        break;
                }
            }
        }
        return solde;
    }

    public List<MouvementCourant> listerMouvements(Long compteId, SessionUtilisateur session) {
        checkPermission(session, "mouvement_courant", "READ");
        CompteCourant compte = compteDAO.findById(compteId);
        if (compte == null) throw new BusinessException("Compte introuvable");
        return mouvementDAO.findByCompteOrderByDate(compte);
    }

    public List<CompteCourant> listerComptes(SessionUtilisateur session) {
        checkPermission(session, "compte_courant", "READ");
        return compteDAO.findAll();
    }

    // ====================================> Permissions 

    public boolean hasPermission(SessionUtilisateur session, String table, String action){
        if (session == null || !session.isConnecte()) {
            throw new SecurityException("Utilisateur non connecté");
        }
        Utilisateur user = session.getUtilisateur();
        List<ActionRole> actionsRoles = session.getActionsRoles();
        for(ActionRole actionRole : actionsRoles){
            if(actionRole.getNomTable().equals(table) && actionRole.getAction().equals(action)){
                if(user.getRole() >= actionRole.getRole()){
                    return true;
                }
            }
        }
        return false;
    }

    public void checkPermission(SessionUtilisateur session, String table, String action) {
        if (!hasPermission(session, table, action)) {
            throw new SecurityException(
                String.format("Accès refusé : vous n'avez pas les droits pour l'action '%s' sur la table '%s'. " +
                             "Rôle requis plus élevé que votre rôle (%d)", 
                             action, table, session.getUtilisateur().getRole())
            );
        }
    }

    // Nouvelle méthode flexible pour valider ou refuser un mouvement
    public void updateMouvement(Long mouvementId, int nouveauStatut, SessionUtilisateur session) {
        if (nouveauStatut == 1) { // VALIDE
            checkPermission(session, "mouvement_courant", "VALIDATE");
        } else if (nouveauStatut == 2) { // REFUSE
            checkPermission(session, "mouvement_courant", "UPDATE");
        } else if (nouveauStatut == 0) {
            checkPermission(session, "mouvement_courant", "UPDATE");
        }

        MouvementCourant mouvement = mouvementDAO.findById(mouvementId);
        if (mouvement == null || mouvement.getStatut() != 0) {
            throw new BusinessException("Mouvement introuvable ou déjà traité");
        }
        mouvement.setStatut(nouveauStatut);
        mouvementDAO.save(mouvement);

        // Mise à jour du solde du compte uniquement si validé
        if (nouveauStatut == 1) {
            CompteCourant compte = mouvement.getCompte();
            double nouveauSolde = compte.getSolde();
            switch (mouvement.getTypeMouvementId()) {
                case 1: // DEPOT
                case 4: // VIREMENT_ENTRANT
                    nouveauSolde += mouvement.getMontant();
                    break;
                case 2: // RETRAIT
                case 3: // VIREMENT_SORTANT
                    nouveauSolde -= mouvement.getMontant();
                    break;
                default:
                    break;
            }
            compte.setSolde(nouveauSolde);
            compte.setDateMaj(LocalDate.now());
            compteDAO.save(compte);
        }
    }

    public List<MouvementCourant> listerMouvementsEnAttente(SessionUtilisateur session) {
        checkPermission(session, "mouvement_courant", "VALIDATE");
        List<MouvementCourant> tous = mouvementDAO.findAll();
        List<MouvementCourant> enAttente = new ArrayList<>();
        for (MouvementCourant mvt : tous) {
            if (mvt.getStatut() == 0) { // EN_ATTENTE
                enAttente.add(mvt);
            }
        }
        return enAttente;
    }
}