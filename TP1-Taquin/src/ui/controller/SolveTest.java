package ui.controller;

import agents.Agent;
import agents.AgentTest;
import environnement.Block;
import environnement.Grille;
import ui.view.MainWindow;

public class SolveTest extends Solve {
    public SolveTest(MainWindow view) {
        super(view);
    }

    @Override
    protected Agent createAgent(Block b, Grille g) {
        return new AgentTest(b, g);
    }
}
