package org.example;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static final JFrame frame = new JFrame("Výběr hry");
    private static final Font FONT = new Font("Monospaced", Font.BOLD, 20);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BG_COLOR = Color.BLACK;

    public static void main(String[] args) {
        zobrazMenu();
    }

    public static void zobrazMenu() {
        frame.getContentPane().removeAll();
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("VYBER SI HRU", SwingConstants.CENTER);
        label.setForeground(TEXT_COLOR);
        frame.getContentPane().setBackground(BG_COLOR);
        label.setFont(FONT);
        frame.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setLayout(new FlowLayout());

        JButton btnSnake = vytvorTlacitko("HRA SNAKE");
        JButton btnRPS = vytvorTlacitko("KÁMEN–NŮŽKY–PAPÍR");
        JButton btnCoC = vytvorTlacitko("COOKIE CLICKER");

        btnSnake.addActionListener(e -> spustSnake());
        btnRPS.addActionListener(e -> RockPaperScissors.spustHru(frame));
        btnCoC.addActionListener(e -> CookieClicker.spustCoC());

        buttonPanel.add(btnSnake);
        buttonPanel.add(btnRPS);
        buttonPanel.add(btnCoC);
        frame.add(buttonPanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static JButton vytvorTlacitko(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 50));
        button.setFont(FONT);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(TEXT_COLOR);
        return button;
    }

    private static void spustSnake() {
        frame.dispose();
        JFrame snakeFrame = new JFrame("Snake");
        GamePanel panel = new GamePanel();
        snakeFrame.add(panel);
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeFrame.setResizable(false);
        snakeFrame.pack();
        snakeFrame.setLocationRelativeTo(null);
        snakeFrame.setVisible(true);
    }
}

