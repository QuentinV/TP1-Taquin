package main;

import agents.Agent;
import ui.controller.SolveTest;
import environnement.Grille;
import ui.view.MainWindow;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("Hello modelisation test IA TAQUIN");

        //pour Test
        Agent.REFRESH_THREAD = 500;

        //model
        Grille g = new Grille(5, 5, 35.0);

        //Vue
        MainWindow mw = new MainWindow(g);
        mw.addController(new SolveTest(mw));

        //lien
        g.addObserver(mw);

        mw.setVisible(true);
    }
}
