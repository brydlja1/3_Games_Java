package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GamePanel extends JPanel implements ActionListener, KeyListener {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 700;
    static final int UNIT_SIZE = 25;
    Timer timer;

    final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    char direction = 'R';
    int appleX, appleY;
    boolean gameOver = false;

    JButton znovu, zpet;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        setLayout(null);
        startGame();
    }

    public void startGame() {
        removeAll();
        revalidate();
        repaint();

        for (int i = 0; i < GAME_UNITS; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        bodyParts = 8;
        direction = 'D';
        gameOver = false;

        if (znovu != null) remove(znovu);
        if (zpet != null) remove(zpet);

        newApple();
        timer = new Timer(100, this);
        timer.start();
    }

    public void newApple() {
        appleX = (int) (Math.random() * (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        //Math.random()-Vrací desetinné číslo mezi 0.0 (včetně) a 1.0 (ne včetně)
        //(SCREEN_WIDTH / UNIT_SIZE) - Říká, kolik jednotek (čtverců) se vejde do šířky obrazovky.
        //Dává náhodné desetinné číslo mezi 0 a napr. 32
        //Vynásobí výsledek jednim ctvercem
        appleY = (int) (Math.random() * (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(new Color(20,100,50));
            } else {
                g.setColor(new Color(45, 180, 0));
            }
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Konec hry", (SCREEN_WIDTH - metrics.stringWidth("Konec hry")) / 2, SCREEN_HEIGHT / 2);

            String scoreText = "Skóre: " + (bodyParts - 6);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.setColor(Color.white);
            g.drawString(scoreText, (SCREEN_WIDTH - metrics.stringWidth(scoreText)) / 2, SCREEN_HEIGHT / 2 + 40);
        }
    }

    public void zobrazTlacitkaPoSkonceni() {
        znovu = vytvorTlacitko("HRÁT ZNOVU");
        zpet = vytvorTlacitko("ZPĚT NA MENU");

        znovu.setBounds((SCREEN_WIDTH - 300) / 2, SCREEN_HEIGHT / 2 + 60, 300, 50);
        zpet.setBounds((SCREEN_WIDTH - 300) / 2, SCREEN_HEIGHT / 2 + 120, 300, 50);

        znovu.addActionListener(e -> startGame());
        zpet.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            Main.zobrazMenu();
        });

        add(znovu);
        add(zpet);
        revalidate();
        repaint();
    }

    private static JButton vytvorTlacitko(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U': y[0] -= UNIT_SIZE; break;
            case 'D': y[0] += UNIT_SIZE; break;
            case 'L': x[0] -= UNIT_SIZE; break;
            case 'R': x[0] += UNIT_SIZE; break;
        }

        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                gameOver = true;
                timer.stop();
                zobrazTlacitkaPoSkonceni();
            }
        }

        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            gameOver = true;
            timer.stop();
            zobrazTlacitkaPoSkonceni();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: if (direction != 'R') direction = 'L'; break;
            case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
            case KeyEvent.VK_UP: if (direction != 'D') direction = 'U'; break;
            case KeyEvent.VK_DOWN: if (direction != 'U') direction = 'D'; break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            checkCollisions();
        }
        repaint();
    }
}