package agents.algoPath;

import java.awt.*;

public class Node {
    private Point pos;
    private double value;
    private Node parent;

    public Node(Node parent, Point pos, double value) {
        this.parent = parent;
        this.pos = pos;
        this.value = value;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof  Node) && (((Node)obj).getPos().equals(this.getPos()));
    }

    @Override
    public int hashCode() {
        return pos.hashCode();
    }

    @Override
    public String toString() {
        return "Node ["+pos.x+";"+pos.y+"; "+value+"]";
    }
}
