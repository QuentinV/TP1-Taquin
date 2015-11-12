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

    public void setActual(Point actual) {
        this.actual = actual;
        setChanged();
        notifyObservers();
    }

    public void rollback()
    {
        Point p = this.actual;
        this.actual = this.previous;
        this.previous = null;
    }

    public Point getPrevious() {
        return previous;
    }

    public void setPrevious(Point previous) {
        this.previous = previous;
    }

    public boolean isSatisfy() {
        return goal.equals(actual);
    }
}
