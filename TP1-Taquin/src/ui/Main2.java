package ui;

import controller.Solve2;
import environnement.Grille;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("hello world v2");

        //model
        Grille g = new Grille(5, 5, 55.0);

        //Vue
        MainWindow mw = new MainWindow(g);
        mw.addController(new Solve2(mw));

        //lien
        g.addObserver(mw);

        mw.setVisible(true);
    }
}
