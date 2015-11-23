package agents.algoPath;

import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Astar {
    private double calculNodeValue(Node n, Grille g, List<Edge> edgeAvoids)
    {
        double v = 1.0;

        if (n.getParent() != null) {
            v += n.getParent().getValue();

            Edge e = new Edge(n.getParent().getPos(), n.getPos());
            if (edgeAvoids != null && edgeAvoids.contains(e)) {
                v *= 50;
                System.out.println("edge "+n.getParent().getPos()+" / "+n.getPos());
            }
        }

        if (g.checkCaseAt(n.getPos())) {
            //System.out.println("pos");
            v *= 3;
        }

        return v;
    }

    private Node bestNode(List<Node> l)
    {
        if (l.isEmpty()) return null;
        Node nBest = l.get(0);
        for (Node n : l)
        {
            if (n.getValue() < nBest.getValue())
                nBest = n;
        }

        return nBest;
    }

    private List<Point> getVoisins(Point p, Grille g) {
        List<Point> voisins = new ArrayList<>();
        int maxX = g.getSizeX();
        int maxY = g.getSizeY();

        if (p.y + 1 < maxY)
            voisins.add(new Point(p.x, p.y + 1));

        if (p.y - 1 >= 0)
            voisins.add(new Point(p.x, p.y - 1));

        if (p.x + 1 < maxX)
            voisins.add(new Point(p.x + 1, p.y));

        if (p.x - 1 >= 0)
            voisins.add(new Point(p.x - 1, p.y));

        return voisins;
    }

    public Node shortestPathToGoal(Point s, Point goal, Grille g, List<Edge> edgeAvoids)
    {
        LinkedList<Node> closedSet = new LinkedList<>();
        LinkedList<Node> openSet = new LinkedList<>();
        openSet.add(new Node(null, s, 0));

        Node nGoal = new Node(null, goal, 0);

        Node current;
        while(!openSet.isEmpty())
        {
            current = this.bestNode(openSet);
            //System.out.println(current+" parent = "+current.getParent());
            if (nGoal.equals(current))
                return current;

            openSet.remove(current);
            closedSet.add(current);

            List<Point> voisins = getVoisins(current.getPos(), g);
            for (Point p : voisins)
            {
                Node v = new Node(current, p, 0);
                if (closedSet.contains(v))
                    continue;

                v.setValue(this.calculNodeValue(v, g, edgeAvoids));

                if (!openSet.contains(v))
                    openSet.add(v);
                else
                {
                    Node nExist = openSet.get(openSet.lastIndexOf(v));
                    if (v.getValue() < nExist.getValue())
                    {
                        nExist.setValue(v.getValue());
                        nExist.setParent(v.getParent());
                    }
                }
            }
        }

        return null;
    }

    public Point nextPos(Block b, Grille g, List<Edge> edgeAvoids) {
        Node n = shortestPathToGoal(b.getActual(), b.getGoal(), g, edgeAvoids);

        while(n.getParent() != null && n.getParent().getParent() != null)
            n = n.getParent();

        //System.out.println(n);

        return n.getPos();
    }

    public Point nextPos(Block b, Grille g) {
        return nextPos(b, g, null);
    }

    public Point nextPosAway(Point s, Point avoid, Grille g) {
        Point nextPos = null;

        List<Point> voisins = this.getVoisins(s, g);
        List<Point> remains = new ArrayList<>();
        for (Point p : voisins)
            if (!g.checkCaseAt(p))
                remains.add(p);

        Random rand = new Random();

        if (remains.isEmpty())
        { //aucune case vide
            do {
                nextPos = voisins.get(rand.nextInt(voisins.size()));
            } while (!nextPos.equals(avoid));
        } else
            nextPos = remains.get(rand.nextInt(remains.size()));

        return nextPos;
    }
}
