package environnement;

import java.awt.*;

/**
 *
 * @author p1308391
 */
public class Case {
    private int num;
    private final Point goal;

    public Case(int num, Point goal) {
        this.num = num;
        this.goal = goal;
    }

    public int getNum() {
        return num;
    }

    public Point getGoal() {
        return goal;
    }
}
