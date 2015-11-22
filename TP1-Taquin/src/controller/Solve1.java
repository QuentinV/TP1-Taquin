package controller;

import agents.Agent;
import agents.Agent1;
import environnement.Block;
import environnement.Grille;
import ui.MainWindow;

public class Solve1 extends Solve {
    public Solve1(MainWindow view) {
        super(view);
    }

    @Override
    protected Agent createAgent(Block b, Grille g) {
        return new Agent1(b, g);
    }
}
