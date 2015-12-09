package ui;

import controller.SolveFinal;
import environnement.Grille;

public class MainFinal {
    public static void main(String[] args) {
        System.out.println("Hello final modelisation IA TAQUIN");

        //model
        Grille g = new Grille(5, 5, 60.0);

        //Vue
        MainWindow mw = new MainWindow(g);
        mw.addController(new SolveFinal(mw));

        //lien
        g.addObserver(mw);

        mw.setVisible(true);
    }
}
