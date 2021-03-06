package ui.view;

import ui.controller.Solve;
import environnement.Block;
import environnement.Grille;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer {
    private final Grille modGrille;
    private JPanel p;
    private JButton bSolve;
    private JLabel lAvTime;

    private int nbTime;
    private int sumTime;

    public MainWindow(Grille modGrille) throws HeadlessException {
        this.modGrille = modGrille;
        nbTime = 0;
        sumTime = 0;

        setup();
    }

    public int getNbTime()
    {
        return nbTime;
    }

    public int addNbTime()
    {
        return ++nbTime;
    }

    public int getSumTime()
    {
        return sumTime;
    }

    public void addTime(int t)
    {
        sumTime += t;
    }

    public JLabel getlAvTime()
    {
        return lAvTime;
    }

    public Grille getModGrille() {
        return modGrille;
    }

    private void setup()
    {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.PAGE_AXIS));

        JLabel titre = new JLabel("Taquin");
        titre.setHorizontalAlignment(JLabel.CENTER);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("serif", Font.PLAIN, 28));

        pNorth.add(titre);

        lAvTime = new JLabel("Average time");
        lAvTime.setHorizontalAlignment(JLabel.CENTER);
        lAvTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        lAvTime.setFont(new Font("serif", Font.PLAIN, 14));
        pNorth.add(lAvTime);

        this.getContentPane().add(pNorth, BorderLayout.NORTH);

        p = new JPanel();
        p.setBorder(new EmptyBorder(10, 50, 10, 50));
        p.setLayout(new GridBagLayout());

        this.update();

        this.getContentPane().add(p);

        bSolve = new JButton("Solve");
        this.getContentPane().add(bSolve, BorderLayout.SOUTH);

        this.pack();
    }

    public void addController(Solve s)
    {
        bSolve.addActionListener(s);
    }

    public JButton getbSolve() {
        return bSolve;
    }

    public void addController()
    {

    }

    private void update()
    {
        p.removeAll();
        p.validate();
        p.repaint();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        for (int x = 0; x < modGrille.getSizeX(); ++x)
            for (int y = 0; y < modGrille.getSizeY(); ++y) {
                c.gridx = y;
                c.gridy = x;

                Block mc = modGrille.getCaseAt(x, y);

                //label
                String str = (mc != null) ? String.valueOf(mc.getNum()) : "";

                JLabel l = new JLabel(str);
                l.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setPreferredSize(new Dimension(40, 40));

                p.add(l, c);
            }

        p.validate();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.update();
    }
}
