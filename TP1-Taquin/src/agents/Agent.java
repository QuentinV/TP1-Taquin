
package agents;

import agents.algoPath.AlgoPath;
import agents.algoPath.Perso;
import agents.algoPath.Test;
import communication.*;
import environnement.Block;
import environnement.Grille;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author p1308391
 */
public class Agent extends Thread {
    private final Block b;
    private Map<Block, Agent> environnement;
    private final Grille grille;
    private final AlgoPath aPath;
    
    //boite aux lettres
    public Agent(Block b, Grille grille) {
        this.b = b;
        this.grille = grille;
        this.aPath = new Perso();
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

    public List<Point> listCaseVide()
    {
        List<Point> points = new ArrayList<>();

        Point xplus1 = new Point(b.getActual().x + 1, b.getActual().y);
        if (!grille.checkCaseAt(xplus1))
            points.add(xplus1);

        Point xmoins1 = new Point(b.getActual().x - 1, b.getActual().y);
        if (!grille.checkCaseAt(xmoins1))
            points.add(xmoins1);

        Point yplus1 = new Point(b.getActual().x, b.getActual().y + 1);
        if (!grille.checkCaseAt(yplus1))
            points.add(yplus1);

        Point ymoins1 = new Point(b.getActual().x, b.getActual().y - 1);
        if (!grille.checkCaseAt(ymoins1))
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
                System.out.println("Message content");
                if (mc != null)
                {
                    if (Transaction.REQUEST == mc.getTrans())
                    {
                        System.out.println("REQUEST");
                        if (mc.getNextPos().equals(b.getActual()))
                        { //si position voulu = position actuel de l'agent
                            //Check liste position vide puis prendre celle qui s'eloigne le plus
                            List<Point> empties = this.listCaseVide();
                            if (!empties.isEmpty())
                            { //Position vide on peut bouger
                                System.out.println("Position vide peut bouger");
                            } else {
                                System.out.println("Message requete et aucune position vide");
                            }

                            //** Future idée : S'eloigner de la position but
                        } else {
                            System.out.println("Not position actuel de l'agent");
                        }
                    }
                }
            } else
            { //Aucun message
                if (!b.isSatisfy())
                { //Si l'agent n'est pas satisfait il doit bouger en direction de son but
                    //trouver la case vide qui se rapproche le plus du but
                    Point p = aPath.getNextPos(b, grille, environnement);
                    if (grille.checkCaseAt(p))
                    { //Envoi request

                    } else
                    {
                        b.move(p);
                    }
                } else {
                    System.out.println("is satistfied");
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public String toString() {
        return "Agent "+b;
    }
}
