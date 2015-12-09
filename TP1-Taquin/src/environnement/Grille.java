
package environnement;

import ui.Main1;

import java.awt.*;
import java.util.*;

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
        Random r = new Random();

        int nbToGenerate = new Double(this.percentFull * (sizeX * sizeY - 1) / 100).intValue();

        for (int i = 0; i < nbToGenerate; ++i)
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

            int priority;
            if ((goal.x == 0 && goal.y == 0) || (goal.x == sizeX-1 && goal.y == sizeY-1))
            { //Coins
                priority = 1;
            } else if (goal.x == sizeX-1 || goal.x == 0 || goal.y == 0 || goal.y == sizeY-1)
            { //lignes
                priority = 2;
            } else
            { //milieu
                priority = 3;
            }

            Block b = new Block(i, goal, new Point(x, y), priority);
            b.addObserver(this);
            blocks[x][y] = b;
        }
    }

    public void reset()
    {
        for (int x = 0; x < sizeX; ++x)
            for (int y = 0; y < sizeY; ++y)
                if (blocks[x][y] != null)
                {
                    blocks[x][y].deleteObservers();
                    blocks[x][y] = null;
                }
        setup();
    }

    public Block getCaseAt(int x, int y)
    {
        if (x > sizeX || x < 0 || y < 0 || y > sizeY)
            return null;
        return blocks[x][y];
    }

    public boolean checkCaseAt(int x, int y)
    {
        return !(x >= sizeX || x < 0 || y < 0 || y >= sizeY) && blocks[x][y] != null;
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
    private boolean checkMoveCase(Block b)
    {
        if (b == null) return false;
        if (b.getPrevious() == null || b.getActual() == null) return false;

        Point actual = b.getActual();

        try {
            return (this.blocks[actual.x][actual.y] == null);
        } catch(Exception e)
        {
            return false;
        }
    }

    public synchronized boolean moveBlock(Block b)
    {
        if (!checkMoveCase(b))
        {
            //System.out.println("Rollback "+b);
            b.rollback();
            return false;
        }

        this.blocks[b.getActual().x][b.getActual().y] = b;
        this.blocks[b.getPrevious().x][b.getPrevious().y] = null;

        return true;
    }

    /**
     * Update from Block
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == null) return;

        if (o instanceof Block) {
            Block b = (Block) o;

            if (moveBlock(b)) {
                //Notifier View
                setChanged();
                notifyObservers();
            } else {
                //System.out.println("CONFLICT : ROLLBACK");
            }
        }
    }
}
