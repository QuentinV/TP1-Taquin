package agents.algoPath;

import agents.algoPath.elements.Edge;
import agents.algoPath.elements.Node;
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
                //System.out.println("edge "+n.getParent().getPos()+" / "+n.getPos());
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

    /**
     * Calculer le plus court chemin d'un point de départ à un but en fonction de la gille
     * et d'arrêtes à éviter
     * @return Node goal, can be use to retrieve the whole path
     */
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

            List<Point> voisins = g.getCaseVoisines(current.getPos());
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

    /**
     * Calculer le plus court chemin et retourner la prochaine position où aller
     * @return next position to go
     */
    public Point nextPos(Point start, Point goal, Grille g, List<Edge> edgeAvoids)
    {
        Node n = shortestPathToGoal(start, goal, g, edgeAvoids);

        //get the next case to move
        while(n.getParent() != null && n.getParent().getParent() != null)
            n = n.getParent();

        //System.out.println(n);

        return n.getPos();
    }

    public Point nextPos(Block b, Grille g, List<Edge> edgeAvoids) {
        return nextPos(b.getActual(), b.getGoal(), g, edgeAvoids);
    }

    public Point nextPos(Block b, Grille g) {
        return nextPos(b, g, null);
    }
}
