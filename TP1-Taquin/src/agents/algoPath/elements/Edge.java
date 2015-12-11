package agents.algoPath.elements;

import java.awt.*;

public class Edge {
    private Point start;
    private Point end;

    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public double calculValue()
    {
        return start.distance(end.x, end.y);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Edge)
                && ((Edge)obj).start.equals(this.start) && ((Edge)obj).end.equals(this.end);
    }

    @Override
    public int hashCode() {
        return start.hashCode() + end.hashCode();
    }
}
