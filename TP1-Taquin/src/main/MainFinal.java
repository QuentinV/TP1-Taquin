package main;

import agents.Agent;
import ui.controller.SolveFinal;
import environnement.Grille;
import ui.view.MainWindow;

public class MainFinal {
    public static void main(String[] args) {
        System.out.println("Hello final modelisation IA TAQUIN");

        //Regler la vitesse de resolution en fonction du sleeping time des threads
        Agent.REFRESH_THREAD = 20; //milliseconds

        //model
        Grille g = new Grille(5, 5, 80);

        //Vue
        MainWindow mw = new MainWindow(g);
        mw.addController(new SolveFinal(mw));

        //lien
        g.addObserver(mw);

        mw.setVisible(true);
    }
}
