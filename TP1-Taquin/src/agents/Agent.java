
package agents;

import environnement.Block;
import environnement.Grille;

import java.util.Map;

public abstract class Agent extends Thread {
    public static int REFRESH_THREAD = 20;

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
    public abstract void run();

    @Override
    public String toString() {
        return "Agent "+b;
    }
}
