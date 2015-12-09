package controller;

import agents.Agent;
import agents.AgentFinal;
import environnement.Block;
import environnement.Grille;
import ui.MainWindow;

public class SolveFinal extends Solve {
    public SolveFinal(MainWindow view) {
        super(view);
    }

    @Override
    protected Agent createAgent(Block b, Grille g) {
        return new AgentFinal(b, g);
    }
}
