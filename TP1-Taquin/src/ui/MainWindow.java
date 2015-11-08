package ui;

import controller.Solve;
import environnement.Case;
import environnement.Grille;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer {
    private final Grille modGrille;
    private JPanel p;

    public MainWindow(Grille modGrille) throws HeadlessException {
        this.modGrille = modGrille;

        setup();
    }

    public Grille getModGrille() {
        return modGrille;
    }

    private void setup()
    {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        JLabel titre = new JLabel("Taquin");
        titre.setHorizontalAlignment(JLabel.CENTER);
        titre.setFont(new Font("serif", Font.PLAIN, 28));

        this.getContentPane().add(titre, BorderLayout.NORTH);

        p = new JPanel();
        p.setLayout(new GridBagLayout());

        this.update();

        this.getContentPane().add(p);

        JButton b = new JButton("Solve");
        b.addActionListener(new Solve(this));

        this.getContentPane().add(b, BorderLayout.SOUTH);

        this.pack();
    }

    private void update()
    {
        System.out.println("Update");

        p.removeAll();
        p.validate();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        for (int x = 0; x < modGrille.getSizeX(); ++x)
            for (int y = 0; y < modGrille.getSizeY(); ++y) {
                c.gridx = x;
                c.gridy = y;

                Case mc = modGrille.getCaseAt(x, y);

                if (mc != null)
                {
                    JLabel l = new JLabel(String.valueOf(mc.getNum()));
                    l.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    l.setHorizontalAlignment(JLabel.CENTER);

                    p.add(l, c);
                }
            }

        p.validate();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.update();
    }
}
