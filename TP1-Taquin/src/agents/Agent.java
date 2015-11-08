
package agents;

import communication.Action;
import communication.BoiteAuxLettres;
import communication.Message;
import environnement.Case;
import environnement.Grille;
import java.awt.Point;
import java.util.Map;

/**
 *
 * @author p1308391
 */
public class Agent extends Thread {
    private final Point but;
    private Point actual;
    private Map<Agent, Point> environnement;
    private final Grille grille;
    
    //boite aux lettres
    public Agent(Point but, Point actual, Grille grille) {
        this.but = but;
        this.actual = actual;
        this.grille = grille;
    }

    public Point getActual() {
        return actual;
    }

    public void setActual(Point actual) {
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

    public Point getBut() {
        return but;
    }
    
    public boolean isSatisfy()
    {
        return but.equals(actual);
    }

    @Override
    public void run() {
        for (;;) {
            System.out.println(this);

           /* Message m = BoiteAuxLettres.pollFirst(this);
            if (m != null)
            { //do message
                if (m.getContent().getAction() == Action.MOVE)
                {

                }
            }*/
            break;
        }
    }

    @Override
    public String toString() {
        return "Agent "+but;
    }
}
