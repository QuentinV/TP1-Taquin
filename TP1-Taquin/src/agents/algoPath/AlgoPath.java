package agents.algoPath;

import agents.Agent;
import environnement.Block;
import environnement.Grille;

import java.awt.*;
import java.util.Map;

public interface AlgoPath {
    public Point getNextPos(Block b, Grille g, Map<Block, Agent> environnement);
}
