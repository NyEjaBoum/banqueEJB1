package com.comptecourant.session;

import com.comptecourant.entity.Utilisateur;
import com.comptecourant.entity.Direction;
import com.comptecourant.entity.ActionRole;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
import java.io.Serializable;
import java.util.List;

@Stateful
public class SessionUtilisateur implements Serializable {
    private Utilisateur utilisateur;
    private List<Direction> directions;
    private List<ActionRole> actionsRoles;

    public void initSession(Utilisateur utilisateur, List<Direction> directions, List<ActionRole> actionsRoles) {
        this.utilisateur = utilisateur;
        this.directions = directions;
        this.actionsRoles = actionsRoles;
    }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public List<Direction> getDirections() { return directions; }
    public List<ActionRole> getActionsRoles() { return actionsRoles; }

    public boolean isConnecte() { return utilisateur != null; }

    @Remove
    public void logout() {
        utilisateur = null;
        directions = null;
        actionsRoles = null;
    }
}