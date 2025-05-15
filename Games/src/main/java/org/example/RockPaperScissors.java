package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static org.example.Main.vytvorTlacitko;

public class RockPaperScissors {
    private static final Random random = new Random();
    private static int bodyHrac = 0;
    private static int bodyPocitac = 0;

    private static final Font FONT = new Font("Monospaced", Font.BOLD, 20);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BG_COLOR = Color.BLACK;
    private static JFrame frame;

    public static void spustHru(JFrame reusedFrame) {
        frame = reusedFrame;
        zobrazVolby();
    }

    public static void zobrazVolby() {
        frame.getContentPane().removeAll();
        frame.setTitle("Kámen - Nůžky - Papír");
        frame.setLayout(new GridLayout(0, 1));
        frame.getContentPane().setBackground(BG_COLOR);

        JLabel label1 = new JLabel("VYBER SVOU VOLBU", SwingConstants.CENTER);
        nastavLabel(label1);
        frame.add(label1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setLayout(new FlowLayout());

        JButton kamen = vytvorTlacitko("KÁMEN");
        JButton nuzky = vytvorTlacitko("NŮŽKY");
        JButton papir = vytvorTlacitko("PAPÍR");

        kamen.addActionListener(e -> hrajKolo("kámen"));
        nuzky.addActionListener(e -> hrajKolo("nůžky"));
        papir.addActionListener(e -> hrajKolo("papír"));

        buttonPanel.add(kamen);
        buttonPanel.add(nuzky);
        buttonPanel.add(papir);
        frame.add(buttonPanel);

        frame.revalidate();
        frame.repaint();
    }

    private static void hrajKolo(String hrac) {
        String[] moznosti = {"kámen", "nůžky", "papír"};
        String pocitac = moznosti[random.nextInt(moznosti.length)];
        String vysledek;

        if (hrac.equals(pocitac)) {
            vysledek = "REMÍZA";
            bodyHrac++;
            bodyPocitac++;
        } else if ((hrac.equals("kámen") && pocitac.equals("nůžky")) ||
                (hrac.equals("nůžky") && pocitac.equals("papír")) ||
                (hrac.equals("papír") && pocitac.equals("kámen"))) {
            vysledek = "VYHRÁL JSI";
            bodyHrac++;
        } else {
            vysledek = "PROHRÁL JSI";
            bodyPocitac++;
        }

        zobrazVysledek(hrac, pocitac, vysledek);
    }

    private static void zobrazVysledek(String hrac, String pocitac, String vysledek) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(0, 1));
        frame.getContentPane().setBackground(BG_COLOR);

        JLabel v1 = new JLabel("TVOJE VOLBA: " + hrac.toUpperCase(), SwingConstants.CENTER);
        JLabel v2 = new JLabel("POČÍTAČOVA VOLBA: " + pocitac.toUpperCase(), SwingConstants.CENTER);
        JLabel vysledekLabel = new JLabel("VÝSLEDEK: " + vysledek, SwingConstants.CENTER);
        JLabel skore = new JLabel("SKÓRE: HRÁČ " + bodyHrac + " : POČÍTAČ " + bodyPocitac, SwingConstants.CENTER);

        nastavLabel(v1);
        nastavLabel(v2);
        nastavLabel(vysledekLabel);
        nastavLabel(skore);

        frame.add(v1);
        frame.add(v2);
        frame.add(vysledekLabel);
        frame.add(skore);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setLayout(new FlowLayout());

        JButton znovu = vytvorTlacitko("HRÁT ZNOVU");
        JButton zpet = vytvorTlacitko("ZPĚT NA MENU");

        znovu.addActionListener(e -> zobrazVolby());
        zpet.addActionListener(e -> Main.zobrazMenu());

        buttonPanel.add(znovu);
        buttonPanel.add(zpet);
        frame.add(buttonPanel);

        frame.revalidate();
        frame.repaint();
    }

    private static void nastavLabel(JLabel label) {
        label.setForeground(TEXT_COLOR);
        label.setFont(FONT);
    }

}

