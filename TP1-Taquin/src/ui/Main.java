package ui;

import environnement.Grille;

public class Main {
    public static boolean DEBUG = false;

    public static void main(String[] args) {
        System.out.println("hello world");

        //model
        Grille g = new Grille(5, 5, 60.0);

        //Vue
        MainWindow mw = new MainWindow(g);

        //lien
        g.addObserver(mw);

        mw.setVisible(true);
    }
}
