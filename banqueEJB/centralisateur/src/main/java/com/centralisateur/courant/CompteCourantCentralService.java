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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NamingEnumeration;
import javax.naming.NameClassPair;
@Stateless
public class CompteCourantCentralService {
    @EJB(lookup = "java:global/comptecourant-1.0-SNAPSHOT/CompteCourantService!com.comptecourant.service.ICompteCourantService")
    private ICompteCourantService compteCourantEJB;

    @EJB
    private TypeMouvementDAO typeMouvementDAO;

    @EJB
    private ClientDAO clientDAO;

    // Supprimez cette ligne qui cause l'erreur :
    // @EJB(lookup = "ejb:change-1.0-SNAPSHOT/ChangeService!com.change.IChangeService")
    // private IChangeService changeService;

    // Méthode pour obtenir le service de change depuis Docker
private IChangeService getChangeService() {
    try {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8081");
        props.put(Context.SECURITY_PRINCIPAL, "nyeja"); // Remplace par ton user WildFly Docker
        props.put(Context.SECURITY_CREDENTIALS, "nyeja"); // Remplace par ton mot de passe Docker
        props.put("jboss.naming.client.ejb.context", true);
props.put("jboss.sasl.policy.noanonymous", "true");
props.put("jboss.sasl.policy.noplaintext", "false");
props.put("jboss.sasl.policy.nodictionary", "true");
props.put("jboss.sasl.policy.noactive", "true");
props.put("jboss.sasl.policy.forward_secrecy", "true");
props.put("jboss.sasl.policy.credentials", "true");

        Context context = new InitialContext(props);

        // Liste les JNDI disponibles pour debug
        NamingEnumeration<NameClassPair> list = context.list("");
        while (list.hasMore()) {
            NameClassPair nc = list.next();
            System.out.println("JNDI: " + nc.getName() + " -> " + nc.getClassName());
        }

Object obj = context.lookup("change-1.0-SNAPSHOT/ChangeService!com.change.IChangeService");
        System.out.println("Type retourné par le lookup : " + obj.getClass().getName());
        return (IChangeService) obj;
    } catch (Exception e) {
        System.err.println("Impossible de se connecter au service de change Docker : " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}

    public Double getSolde(Long compteId, SessionUtilisateur session) {
        List<MouvementCourant> mouvements = compteCourantEJB.listerMouvements(compteId, session);
        double solde = 0.0;
        for (MouvementCourant mvt : mouvements) {
    if (mvt.getStatut() == 1) {
        double montantAriary;
        try {
            IChangeService changeServ = getChangeService();
            System.out.println("Devise du mouvement: " + mvt.getDevise());
            if (changeServ != null) {
                montantAriary = changeServ.convertirEnAriary(mvt.getDevise(), mvt.getMontant(), mvt.getDateMouvement());
                System.out.println("Conversion via EJB: " + mvt.getMontant() + " " + mvt.getDevise() + " => " + montantAriary + " Ar");
            } else {
                System.out.println("Service de change INDISPONIBLE, montant utilisé: " + mvt.getMontant());
                montantAriary = mvt.getMontant();
            }
        } catch (Exception e) {
            montantAriary = mvt.getMontant();
            System.err.println("Erreur conversion devise : " + e.getMessage());
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

    // Méthode helper pour obtenir les devises disponibles
    public List<String> listerDevises() {
        try {
            IChangeService changeServ = getChangeService();
            return changeServ != null ? changeServ.listerDevises() : List.of("MGA", "EUR", "USD");
        } catch (Exception e) {
            return List.of("MGA", "EUR", "USD"); // Devises par défaut
        }
    }
}