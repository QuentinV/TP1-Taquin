
package agents;

import environnement.Case;
import environnement.Grille;
import java.awt.Point;
import java.util.Map;

/**
 *
 * @author p1308391
 */
public class Agent extends Thread {
    private final Case but;
    private Case actual;
    private Map<Agent, Point> environnement;
    private final Grille grille;
    
    //boite aux lettres
    public Agent(Case but, Case actual, Map<Agent, Point> environnement, Grille grille) {
        this.but = but;
        this.actual = actual;
        this.environnement = environnement;
        this.grille = grille;
    }

    public Case getActual() {
        return actual;
    }

    public void setActual(Case actual) {
        this.actual = actual;
    }

    public Map<Agent, Point> getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Map<Agent, Point> environnement) {
        this.environnement = environnement;
    }

    public Grille getGrille() {
        return grille;
    }

    public Case getBut() {
        return but;
    }
    
    public boolean isSatisfy()
    {
        return but.equals(actual);
    }

    @Override
    public void run() {
       
    }
}
