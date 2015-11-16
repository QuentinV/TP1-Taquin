package agents.algoPath;

import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private double calculNodeValue(Point start, Point end, Grille g, List<Edge> edgeAvoids) {
        if (start == null || end == null || g == null) return -1;

        Edge e = new Edge(start, end);

        double value = e.calculValue();

        if (edgeAvoids != null && edgeAvoids.contains(e))
            value *= 10;

        //min ligne ou max ligne plus prioritaire
        //i = 0 ou max i moins prioritaire
        //tous le reste encore moins prioritaire

        if (g.checkCaseAt(end.x, end.y))
            value *= 10;

        return value;
    }

    private List<Point> getVoisins(Point p, Grille g) {
        List<Point> voisins = new ArrayList<>();
        int maxX = g.getSizeX();
        int maxY = g.getSizeY();

        if (p.x + 1 < maxX)
            voisins.add(new Point(p.x + 1, p.y));

        if (p.x - 1 >= 0)
            voisins.add(new Point(p.x - 1, p.y));

        if (p.y + 1 < maxY)
            voisins.add(new Point(p.x, p.y + 1));

        if (p.y - 1 >= 0)
            voisins.add(new Point(p.x, p.y - 1));

        return voisins;
    }

    private Node bestInList(List<Node> list)
    {
        if (list.isEmpty()) return null;
        Node best = list.get(0);

        for (Node n : list)
            if (n.getValue() < best.getValue())
                best = n;

        return best;
    }

    public Node shortestPathToGoal(Point s, Point goal, Grille g, List<Edge> edgeAvoids)
    {
        LinkedList<Node> listOpen = new LinkedList<>();
        LinkedList<Node> listClosed = new LinkedList<>();

        Node start = new Node(null, s, calculNodeValue(s, goal, g, edgeAvoids));
        listOpen.add(start);

        if (start.getPos().equals(goal))
            return start;

        Node n = null;
        Node nGoal = null;
        while(!listOpen.isEmpty())
        {
            n = listOpen.pop();
            listClosed.add(n);

            if (n.getPos().equals(goal))
                nGoal = n;

            //System.out.println(n);

            List<Point> voisins = getVoisins(n.getPos(), g);
            for (Point p : voisins)
            {
                //creation du noeud
                double value = calculNodeValue(n.getPos(), p, g, edgeAvoids) + n.getValue();
                Node v = new Node(n, p, value);

                //System.out.println("Voisins = "+v);

                Node vs = null;
                if (listOpen.contains(v))
                    vs = listOpen.get(listOpen.lastIndexOf(v));
                else if (listClosed.contains(v))
                    vs = listClosed.get(listClosed.lastIndexOf(v));

                if (vs != null)
                {
                    if (v.getValue() < vs.getValue())
                    {
                        vs.setValue(v.getValue());
                        vs.setParent(v.getParent());
                    }
                } else
                    listOpen.add(v);
            }
        }

        return nGoal;
    }

    public Point nextPosToGoal(Point start, Point goal, Grille g, List<Edge> edgeAvoids)
    {
        Node n = shortestPathToGoal(start, goal, g, edgeAvoids);

        while(n.getParent() != null && n.getParent().getParent() != null)
            n = n.getParent();

        return (n != null) ? n.getPos() : null;
    }

    public Point nextPosToGoal(Point start, Point goal, Grille g)
    {
        return nextPosToGoal(start, goal, g, null);
    }
}
