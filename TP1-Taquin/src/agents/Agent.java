
package agents;

import agents.algoPath.Path;
import agents.algoPath.Edge;
import agents.algoPath.Test;
import communication.*;
import environnement.Block;
import environnement.Grille;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Agent extends Thread {
    protected final Block b;
    protected Map<Block, Agent> environnement;
    protected final Grille grille;

    //boite aux lettres
    public Agent(Block b, Grille grille) {
        this.b = b;
        this.grille = grille;
    }

    public Map<Block, Agent> getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Map<Block, Agent> environnement) {
        this.environnement = environnement;
    }

    public Grille getGrille() {
        return grille;
    }

    public Block getBlock() {
        return b;
    }

    public boolean isEnvSatisfied() {
        for (Map.Entry<Block, Agent> e : environnement.entrySet())
            if (e.getValue() != null && !e.getValue().getBlock().isSatisfy())
                return false;

        return true;
    }

    @Override
    public String toString() {
        return "Agent "+b;
    }
}
