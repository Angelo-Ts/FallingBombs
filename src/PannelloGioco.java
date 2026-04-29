import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PannelloGioco extends JPanel implements KeyListener {

    private final Cannone cannone;
    // Liste per gestire oggetti multipli
    private final ArrayList<Bomba> bombe = new ArrayList<>();
    private final ArrayList<Missile> missili = new ArrayList<>();
    
    private int punteggio = 0;
    private boolean gameOver = false;
    private final Random random = new Random();

    private static final int VELOCITA_CANNONE = 7;
    private boolean tastoDestra = false;
    private boolean tastoSinistra = false;

    public PannelloGioco() {
        setBackground(new Color(15, 15, 35));
        setFocusable(true);
        addKeyListener(this);

        int xCannone = FallingBombs.LARGHEZZA / 2 - Cannone.LARGHEZZA / 2;
        int yCannone = FallingBombs.ALTEZZA - Cannone.ALTEZZA - 40;
        cannone = new Cannone(xCannone, yCannone);

        // Timer principale per l'aggiornamento grafico (60 FPS)
        new javax.swing.Timer(FallingBombs.RITARDO_MS, e -> aggiornaLogica()).start();

        // Thread per la generazione delle bombe (Requisito Multithreading)
        new Thread(() -> {
            while (!gameOver) {
                try {
                    Thread.sleep(1500); // Genera una bomba ogni 1.5 secondi
                    generaSquadraBomba();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }

    private synchronized void generaSquadraBomba() {
        int xCasuale = random.nextInt(FallingBombs.LARGHEZZA - Bomba.RAGGIO * 2) + Bomba.RAGGIO;
        int velCasuale = random.nextInt(3) + 2; // Velocità casuale tra 2 e 5
        bombe.add(new Bomba(xCasuale, -Bomba.RAGGIO, velCasuale));
    }

    private void aggiornaLogica() {
        if (gameOver) return;

        aggiornaCannone();

        synchronized (this) {
            // Muovi missili
            Iterator<Missile> itMissili = missili.iterator();
            while (itMissili.hasNext()) {
                Missile m = itMissili.next();
                m.y -= 8; // Velocità fissa missile
                if (m.y < -20) itMissili.remove();
            }

            // Muovi bombe e controlla collisioni
            Iterator<Bomba> itBombe = bombe.iterator();
            while (itBombe.hasNext()) {
                Bomba b = itBombe.next();
                b.y += b.velocita; // Caduta a velocità casuale

                // Controllo collisione terreno (Game Over)
                if (b.y + Bomba.RAGGIO > FallingBombs.ALTEZZA - 20) {
                    gameOver = true;
                }

                // Controllo collisione con missili
                for (int i = 0; i < missili.size(); i++) {
                    Missile m = missili.get(i);
                    if (checkCollision(m, b)) {
                        itBombe.remove();
                        missili.remove(i);
                        punteggio += 10;
                        break;
                    }
                }
            }
        }
        repaint();
    }

    private boolean checkCollision(Missile m, Bomba b) {
        // Calcolo distanza tra centro bomba e missile
        double dist = Math.sqrt(Math.pow(m.x - b.x, 2) + Math.pow(m.y - b.y, 2));
        return dist < Bomba.RAGGIO + 5;
    }

    private void aggiornaCannone() {
        if (tastoSinistra) cannone.x = Math.max(0, cannone.x - VELOCITA_CANNONE);
        if (tastoDestra) cannone.x = Math.min(FallingBombs.LARGHEZZA - Cannone.LARGHEZZA, cannone.x + VELOCITA_CANNONE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        cannone.draw(g2);
        synchronized (this) {
            for (Bomba b : bombe) b.draw(g2);
            for (Missile m : missili) m.draw(g2);
        }

        disegnaHUD(g2);
    }

    private void disegnaHUD(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Punteggio: " + punteggio, 20, 30);
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", FallingBombs.LARGHEZZA/2 - 150, FallingBombs.ALTEZZA/2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  tastoSinistra = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) tastoDestra = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            // Sparo: il missile parte dal centro del cannone
            missili.add(new Missile(cannone.x + Cannone.LARGHEZZA / 2, cannone.y));
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  tastoSinistra = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) tastoDestra = false;
    }
    @Override public void keyTyped(KeyEvent e) {}
}