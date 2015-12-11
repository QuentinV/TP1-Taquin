package agents.algoPath;

import environnement.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Away {
    /**
     * Obtenir la prochaine case
     * Priviligie une case libre aléatoire 80% du temps
     * Sinon choisi une case aléatoire occupée
     * @return Point next case
     */
    public Point nextPos(Point s, Grille g) {
        Point nextPos = null;

        java.util.List<Point> voisins = g.getCaseVoisines(s);
        java.util.List<Point> free = new ArrayList<>();
        for (Point p : voisins)
            if (!g.checkCaseAt(p))
                free.add(p);

        Random rand = new Random();
        int chance = rand.nextInt(100);

        nextPos = (free.isEmpty() || chance > 80)
                ? voisins.get(rand.nextInt(voisins.size()))
                : free.get(rand.nextInt(free.size()));

        return nextPos;
    }
}
