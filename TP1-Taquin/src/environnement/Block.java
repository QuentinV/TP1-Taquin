package environnement;

import java.awt.*;
import java.util.Observable;

public class Block extends Observable {
    private final int num;
    private final Point goal; //where to go
    private Point actual;     //where I am
    private Point previous;   //where I was - only use in rollback

    private int priority;

    public Block(int num, Point goal, Point actual, int priority) {
        this.num = num;
        this.goal = goal;
        this.actual = actual;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
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
        return String.valueOf(this.num)+" ["+getActual().x+";"+getActual().y+"]";
    }
}
