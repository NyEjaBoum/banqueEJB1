package com.comptecourant.service;

import com.comptecourant.entity.*;
import com.comptecourant.dao.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Positive;
import java.util.List;
import jakarta.ejb.Remote;

@Stateless
@Remote(ICompteCourantService.class)
public class CompteCourantService implements ICompteCourantService {
    @Inject
    private CompteCourantDAO compteDAO;
    @Inject
    private ClientDAO clientDAO;
    @Inject
    private MouvementCourantDAO mouvementDAO;

    public CompteCourant creerCompte(Long clientId) {
        Client client = clientDAO.findById(clientId);
        if (client == null) throw new IllegalArgumentException("Client introuvable");
        CompteCourant compte = new CompteCourant();
        compte.setClient(client);
        compte.setSolde(0.0);
        compte.setDateMaj(java.time.LocalDate.now());
        compteDAO.save(compte);
        return compte;
    }

    public MouvementCourant ajouterMouvement(Long compteId, Double montant, MouvementCourant.TypeMouvement type) {
        CompteCourant compte = compteDAO.findById(compteId);
        if (compte == null) throw new BusinessException("Compte introuvable");
        if (montant == null || montant <= 0) throw new BusinessException("Montant doit être positif");
        if (type == MouvementCourant.TypeMouvement.SORTIE && compte.getSolde() < montant) {
            throw new BusinessException("Solde insuffisant pour effectuer ce retrait");
        }

        MouvementCourant mouvement = new MouvementCourant();
        mouvement.setCompte(compte);
        mouvement.setMontant(montant);
        mouvement.setType(type.name());
        mouvement.setDateMouvement(java.time.LocalDate.now());
        mouvementDAO.save(mouvement);
        // Mise à jour du solde
        double nouveauSolde = compte.getSolde();
        if (type == MouvementCourant.TypeMouvement.ENTREE) {
            nouveauSolde += montant;
        } else {
            nouveauSolde -= montant;
        }
        compte.setSolde(nouveauSolde);
        compte.setDateMaj(java.time.LocalDate.now());
        compteDAO.save(compte);
        return mouvement;
    }

    public Double getSolde(Long compteId) {
        CompteCourant compte = compteDAO.findById(compteId);
        if (compte == null) throw new IllegalArgumentException("Compte introuvable");
        return compte.getSolde();
    }

    public List<MouvementCourant> listerMouvements(Long compteId) {
        CompteCourant compte = compteDAO.findById(compteId);
        if (compte == null) throw new IllegalArgumentException("Compte introuvable");
        return mouvementDAO.findByCompteOrderByDate(compte);
    }

    public List<CompteCourant> listerComptes() {
        return compteDAO.findAll();
    }
}
