
package environnement;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 * @author p1308391
 */
public class Grille extends Observable {
    private final Case[][] cases;
    private final int sizeX;
    private final int sizeY;

    public Grille(int x, int y) {
        this.cases = new Case[x][y];
        this.sizeX = x;
        this.sizeY = y;

        setup();
    }

    private void setup() {
        System.out.println("Setup grille");

        //generate x * y - 1 cases random
        List<Integer> l = new ArrayList<>();
        for (int i = 1; i < sizeY * sizeY; ++i)
            l.add(i);
        Collections.shuffle(l);

        int index = 0;
        for (int x = 0; x < sizeX; ++x)
            for (int y = 0; y < sizeY; ++y)
                try {
                    int n = l.get(index++);
                    Point goal = new Point(0, 0);
                    goal.x = n / sizeY;
                    goal.y = n - goal.x * sizeY;
                    cases[x][y] = new Case(n, goal);
                } catch (Exception e) {
                    break;
                }
    }

    synchronized public boolean move(Point p, Point p2)
    {
        if (p == null || p2 == null) return false;

        if (cases[p.x][p.y] == null)
        {
            System.out.println(p+" is empty. Nothing to move.");
            return false;
        }

        if(cases[p2.x][p2.y] != null)
        {
            System.out.println(p2+" is not empty");
            return false;
        }

        cases[p2.x][p2.y] = cases[p.x][p.y]; //move

        return true;
    }

    public Case getCaseAt(int x, int y)
    {
        if (x > sizeX || x < 0 || y < 0 || y > sizeY)
            return null;
        return cases[x][y];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
