package environnement;

import java.awt.*;
import java.util.Observable;

/**
 *
 * @author p1308391
 */
public class Block extends Observable {
    private final int num;
    private final Point goal;
    private Point actual;
    private Point previous;

    public Block(int num, Point goal, Point actual) {
        this.num = num;
        this.goal = goal;
        this.actual = actual;
    }

    public int getNum() {
        return num;
    }

    public Point getGoal() {
        return goal;
    }

    public Point getActual() {
        return actual;
    }

    public void move(int offsetx, int offsety)
    {
        this.previous = new Point(this.actual.x, this.actual.y);
        this.actual.x += offsetx;
        this.actual.y += offsety;

        setChanged();
        notifyObservers();
    }

    public void move(Point p)
    {
        if (p == null) return;

        this.previous = new Point(this.actual.x, this.actual.y);
        this.actual = p;

        setChanged();
        notifyObservers();
    }

    public Point getPrevious() {
        return previous;
    }

    public void rollback()
    {
        if (this.previous == null) return;

        this.actual = this.previous;
        this.previous = null;
    }

    public boolean isSatisfy() {
        return goal.equals(actual);
    }

    @Override
    public String toString() {
        return String.valueOf(this.num)+" "+getActual();
    }
}
