
package agents;

import communication.*;
import environnement.Block;
import environnement.Grille;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author p1308391
 */
public class Agent extends Thread {
    private final Block c;
    private Map<Agent, Block> environnement;
    private final Grille grille;
    
    //boite aux lettres
    public Agent(Block c, Grille grille) {
        this.c = c;
        this.grille = grille;
    }

    public Map<Agent, Block> getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Map<Agent, Block> environnement) {
        this.environnement = environnement;
    }

    public Grille getGrille() {
        return grille;
    }

    public Block getCase() {
        return c;
    }

    public List<Point> listCaseVide()
    {
        List<Point> points = new ArrayList<>();

        Point xplus1 = new Point(c.getActual().x + 1, c.getActual().y);
        if (grille.checkCaseAt(xplus1))
            points.add(xplus1);

        Point xmoins1 = new Point(c.getActual().x - 1, c.getActual().y);
        if (grille.checkCaseAt(xmoins1))
            points.add(xmoins1);

        Point yplus1 = new Point(c.getActual().x, c.getActual().y + 1);
        if (grille.checkCaseAt(yplus1))
            points.add(yplus1);

        Point ymoins1 = new Point(c.getActual().x, c.getActual().y - 1);
        if (grille.checkCaseAt(ymoins1))
            points.add(ymoins1);

        return points;
    }

    @Override
    public void run() {
        for (;;) {
            System.out.println(this);

            Message m = BoiteAuxLettres.pollFirst(this);
            if (m != null) {
                MessageContent mc = m.getContent();
                if (mc != null)
                {
                    if (Transaction.REQUEST == mc.getTrans())
                    {
                        if (mc.getNextPos().equals(c.getActual()))
                        { //si position voulu = position actuel de l'agent
                            //Check liste position vide puis prendre celle qui s'eloigne le plus
                            List<Point> empties = this.listCaseVide();
                            if (!empties.isEmpty())
                            { //Position vide on peut bouger

                            }

                            //** Future idée : S'eloigner de la position but
                        }
                    }
                }
            }

        }
    }

    @Override
    public String toString() {
        return "Agent "+c;
    }
}
