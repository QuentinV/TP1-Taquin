package controller;

import agents.Agent;
import environnement.Case;
import environnement.Grille;
import ui.MainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solve implements ActionListener {
    private final MainWindow view;

    public List<Agent> agents;
    private Map<Agent, Point> env;

    public Solve(MainWindow view) {
        this.view = view;
        agents = new ArrayList<>();
        env = new HashMap<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Grille g = view.getModGrille();

        //creation des agents
        for (int x = 0; x < g.getSizeX(); ++x)
            for (int y = 0; y < g.getSizeY(); ++y)
            {
                //1 agent pour chaque case non nulle
                Case c = g.getCaseAt(x, y);
                if (c != null)
                {
                    Point posActual = new Point(x, y);

                    Agent a = new Agent(c.getGoal(), posActual, g);
                    env.put(a, posActual);
                    a.setEnvironnement(env);

                    agents.add(a);
                }
            }

        //Demarrer tous les agents
        for (Agent a : agents)
            a.start();
    }
}
