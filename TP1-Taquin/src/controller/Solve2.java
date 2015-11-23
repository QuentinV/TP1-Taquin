package controller;

import agents.Agent;
import agents.Agent2;
import environnement.Block;
import environnement.Grille;
import ui.MainWindow;

public class Solve2 extends Solve {

    public Solve2(MainWindow view) {
        super(view);
    }

    @Override
    protected Agent createAgent(Block b, Grille g) {
        return new Agent2(b, g);
    }
}
