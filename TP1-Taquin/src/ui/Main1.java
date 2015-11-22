package ui;

import controller.Solve1;
import environnement.Grille;

public class Main1 {
    public static void main(String[] args) {
        System.out.println("hello world");

        //model
        Grille g = new Grille(5, 5, 60.0);

        //Vue
        MainWindow mw = new MainWindow(g);
        mw.addController(new Solve1(mw));

        //lien
        g.addObserver(mw);

        mw.setVisible(true);
    }
}
