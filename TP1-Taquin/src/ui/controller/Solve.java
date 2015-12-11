package ui.controller;

import agents.Agent;
import environnement.Block;
import environnement.Grille;
import ui.view.MainWindow;
import ui.view.TimeStr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Solve implements ActionListener {
    private final MainWindow view;

    public List<Agent> agents;
    private Map<Block, Agent> env;

    public Solve(MainWindow view) {
        this.view = view;
        agents = new ArrayList<>();
        env = new HashMap<>();
    }

    protected abstract Agent createAgent(Block c, Grille g);

    @Override
    public void actionPerformed(ActionEvent e) {
        long timeStart = System.currentTimeMillis();

        view.getbSolve().setEnabled(false);
        view.getbSolve().setText("Solving...");

        Grille g = view.getModGrille();

        //creation des agents
        for (int x = 0; x < g.getSizeX(); ++x)
            for (int y = 0; y < g.getSizeY(); ++y)
            {
                //1 agent pour chaque case non nulle
                Block c = g.getCaseAt(x, y);
                if (c != null)
                {
                    Agent a = this.createAgent(c, g);
                    env.put(c, a);
                    a.setEnvironnement(env);

                    agents.add(a);
                }
            }

        //Demarrer tous les agents
        for (Agent a : agents)
            a.start();

        //Attendre la fin des agents
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Agent a : agents)
                    try {
                        a.join();
                    } catch (InterruptedException e1) {
                    }

                int time = (int)((System.currentTimeMillis() - timeStart) / 1000);

                view.addTime(time);
                view.getbSolve().setText(String.valueOf(view.addNbTime())+" - Solved in "+new TimeStr(time));
                view.getbSolve().setBackground(Color.green);
                view.getlAvTime().setText(String.valueOf(view.getNbTime())+" fois : "+new TimeStr((double)view.getSumTime()/(double)view.getNbTime()).toString());

                g.reset();
                agents.clear();
                view.getbSolve().setEnabled(true);

                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e1)
                {
                    e1.printStackTrace();
                }

                view.getbSolve().doClick();
            }
        }).start();
    }
}
