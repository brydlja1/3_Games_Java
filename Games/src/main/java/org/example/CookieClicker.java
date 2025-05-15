package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import static org.example.Main.frame;

public class CookieClicker extends JPanel {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 700;
    private static int pocetKliknuti = 0;
    private static boolean autoKlikerZakoupen = false;
    private static JLabel labelKliky;
    private static JButton btnAutoKliker;
    private static final String SCORE_FILE = "cookie_score.txt";

    public CookieClicker() {
        pocetKliknuti = nactiSkore();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout(new GridLayout(0, 1));
        setBackground(Color.BLACK);

        labelKliky = new JLabel("Kliknutí: " + pocetKliknuti, SwingConstants.CENTER);
        labelKliky.setFont(new Font("Monospaced", Font.BOLD, 24));
        labelKliky.setForeground(Color.WHITE);
        add(labelKliky);

        JButton btnKlik = new JButton("Klikni na cookie!");
        btnKlik.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnKlik.setBackground(Color.DARK_GRAY);
        btnKlik.setForeground(Color.WHITE);
        btnKlik.setFocusPainted(false);

        btnKlik.addActionListener(e -> {
            pocetKliknuti++;
            aktualizujLabel();
            ulozSkore();
            if (pocetKliknuti >= 50 && !autoKlikerZakoupen) {
                btnAutoKliker.setEnabled(true);
            }
        });

        btnAutoKliker = new JButton("Koupit Auto Clicker (50)");
        btnAutoKliker.setFont(new Font("Monospaced", Font.BOLD, 20));
        btnAutoKliker.setBackground(new Color(80, 80, 80));
        btnAutoKliker.setForeground(Color.GREEN);
        btnAutoKliker.setEnabled(pocetKliknuti >= 50); // povolíme, pokud skóre už bylo nahrané
        btnAutoKliker.addActionListener(e -> koupitAutoKliker());

        JButton btnZpet = new JButton("ZPĚT NA MENU");
        btnZpet.setFont(new Font("Monospaced", Font.BOLD, 20));
        btnZpet.setBackground(Color.GRAY);
        btnZpet.setForeground(Color.WHITE);
        btnZpet.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            Main.zobrazMenu();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.BLACK);
        btnPanel.add(btnKlik);
        btnPanel.add(btnAutoKliker);
        btnPanel.add(btnZpet);

        add(btnPanel);
    }

    private void aktualizujLabel() {
        labelKliky.setText("Kliknutí: " + pocetKliknuti);
    }

    private void koupitAutoKliker() {
        if (pocetKliknuti >= 50 && !autoKlikerZakoupen) {
            pocetKliknuti -= 50;
            autoKlikerZakoupen = true;
            btnAutoKliker.setEnabled(false);
            aktualizujLabel();
            ulozSkore();

            Timer autoKlikTimer = new Timer(5000, (ActionEvent e) -> {
                pocetKliknuti++;
                aktualizujLabel();
                ulozSkore();
            });
            autoKlikTimer.start();
        }
    }

    private void ulozSkore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            writer.write(String.valueOf(pocetKliknuti));
        } catch (IOException e) {
            System.err.println("Chyba při ukládání skóre: " + e.getMessage());
        }
    }

    private int nactiSkore() {
        File file = new File(SCORE_FILE);
        if (!file.exists()) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Chyba při načítání skóre: " + e.getMessage());
            return 0;
        }
    }

    public static void spustCoC() {
        frame.dispose();
        frame.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.setBackground(Color.black);
        JFrame CoCFrame = new JFrame("Cookie Clicker");
        CookieClicker panel = new CookieClicker();
        CoCFrame.add(panel);
        CoCFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CoCFrame.setResizable(false);
        CoCFrame.pack();
        CoCFrame.setLocationRelativeTo(null);
        CoCFrame.setVisible(true);
    }
}
