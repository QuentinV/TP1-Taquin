
package environnement;

import ui.Main;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 * @author p1308391
 */
public class Grille extends Observable implements Observer {
    private final Block[][] blocks;
    private final int sizeX;
    private final int sizeY;

    private final double percentFull;

    public Grille(int x, int y, double percentFull) {
        this.blocks = new Block[x][y];
        this.sizeX = x;
        this.sizeY = y;

        if (percentFull > 100)
            this.percentFull = 100;
        else if (percentFull < 0)
            this.percentFull = 0;
        else
            this.percentFull = percentFull;

        setup();
    }

    private void setup() {
        if (Main.DEBUG)
            System.out.println("Setup grille");

        Random r = new Random();

        int nbToGenerate = new Double(this.percentFull * (sizeX * sizeY - 1) / 100).intValue();

        for (int i = 1; i <= nbToGenerate; ++i)
        {
            //generate random x & y until a null case have been found
            int x, y;
            do {
                x = r.nextInt(sizeX);
                y = r.nextInt(sizeY);
            } while(blocks[x][y] != null);

            //Add new block
            Point goal = new Point(0, 0);
            goal.x = i / sizeY;
            goal.y = i - goal.x * sizeY;
            Block b = new Block(i, goal, new Point(x, y));
            b.addObserver(this);
            blocks[x][y] = b;
        }
    }

    public Block getCaseAt(int x, int y)
    {
        if (x > sizeX || x < 0 || y < 0 || y > sizeY)
            return null;
        return blocks[x][y];
    }

    public boolean checkCaseAt(int x, int y)
    {
        return !(x > sizeX || x < 0 || y < 0 || y > sizeY) && blocks[x][y] != null;
    }

    public boolean checkCaseAt(Point p)
    {
        return p != null && checkCaseAt(p.x, p.y);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    /**
     * Verifier si mouvement case possible
     */
    public boolean checkMoveCase(Block c)
    {
        if (c == null) return false;
        if (c.getPrevious() == null || c.getActual() == null) return false;

        Point actual = c.getActual();

        return (this.blocks[actual.x][actual.y] == null);
    }

    /**
     * Update from Block
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == null) return;

        if (o instanceof Block) {
            Block c = (Block) o;

            if (!this.checkMoveCase(c))
                c.rollback();

            //Notifier View
            setChanged();
            notifyObservers();
        }
    }
}
