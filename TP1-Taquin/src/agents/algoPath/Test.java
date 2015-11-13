package agents.algoPath;

import agents.Agent;
import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Necrolight on 13/11/2015.
 */
public class Test implements AlgoPath {
    @Override
    public Point getNextPos(Block b, Grille g, Map<Block, Agent> environnement) {
        int offsetx = b.getGoal().x - b.getActual().x;
        int offsety = b.getGoal().y - b.getActual().y;

        if (offsetx >= offsety)
            offsetx = 0;
        else
            offsety = 0;

        if (offsetx != 0)
            offsetx = offsetx / Math.abs(offsetx);
        if (offsety != 0)
            offsety = offsety / Math.abs(offsety);

        if (!g.checkCaseAt(b.getActual().x + offsetx, b.getActual().y + offsety))
        {
            System.out.println("Move case autour calcule");
            return new Point(b.getActual().x+offsetx, b.getActual().y + offsety);
        } else {
            List<Point> points = new ArrayList<>();

            Point xplus1 = new Point(b.getActual().x + 1, b.getActual().y);
            if (!g.checkCaseAt(xplus1))
                points.add(xplus1);

            Point xmoins1 = new Point(b.getActual().x - 1, b.getActual().y);
            if (!g.checkCaseAt(xmoins1))
                points.add(xmoins1);

            Point yplus1 = new Point(b.getActual().x, b.getActual().y + 1);
            if (!g.checkCaseAt(yplus1))
                points.add(yplus1);

            Point ymoins1 = new Point(b.getActual().x, b.getActual().y - 1);
            if (!g.checkCaseAt(ymoins1))
                points.add(ymoins1);

            if (!points.isEmpty()) {  //case vide autour
                Random r = new Random();
                int index = r.nextInt(points.size());
                System.out.println("Move case autour random");
                return points.get(index);
            } else {
                System.out.println("Aucune case vide autour");
            }
        }
        return null;
    }
}
