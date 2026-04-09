import java.awt.*;
import java.util.*;

public class PannelloGioco extends JPanel implements KeyListener {

    // Oggetti di gioco
    private final Cannone cannone;
    private final Bomba    bombaDiTest;   // placeholder visivo
    private final Missile  missileDiTest; // placeholder visivo

    // Velocità di spostamento orizzontale del cannone (pixel per frame)
    private static final int VELOCITA_CANNONE = 5;

    // Stato dei tasti freccia (true = premuto)
    private boolean tastoDestra = false;
    private boolean tastoSinistra = false;

    public PannelloGioco() {
        setBackground(new Color(15, 15, 35)); // sfondo blu notte
        setFocusable(true);                   // indispensabile per KeyListener
        addKeyListener(this);

        // Posiziona il cannone al centro-basso del pannello
        int xCannone = FallingBombs.LARGHEZZA / 2 - Cannone.LARGHEZZA / 2;
        int yCannone = FallingBombs.ALTEZZA  - Cannone.ALTEZZA - 20;
        cannone = new Cannone(xCannone, yCannone);

        // Oggetti di esempio (verranno gestiti dinamicamente nelle fasi successive)
        bombaDiTest    = new Bomba(350, 80);
        missileDiTest  = new Missile(410, 400);

        // Timer Swing: chiama actionPerformed ogni ~16 ms (60 FPS)
        Timer timer = new Timer(FallingBombs.RITARDO_MS, e -> aggiornaFrame());
        timer.start();
    }

    // Chiamato ad ogni tick del timer
    private void aggiornaFrame() {
        aggiornaCannone();
        repaint(); // schedula un ridisegno del pannello
    }

    // Sposta il cannone in base ai tasti premuti, mantenendolo dentro i bordi
    private void aggiornaCannone() {
        if (tastoSinistra) {
            cannone.x = Math.max(0, cannone.x - VELOCITA_CANNONE);
        }
        if (tastoDestra) {
            cannone.x = Math.min(FallingBombs.LARGHEZZA - Cannone.LARGHEZZA,
                                 cannone.x + VELOCITA_CANNONE);
        }
    }

    // ---- Disegno ----
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // cancella il frame precedente

        // Attiva l'anti-aliasing per forme più morbide
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Disegna gli oggetti di gioco
        bombaDiTest.draw(g2);
        missileDiTest.draw(g2);
        cannone.draw(g2);

        // HUD minimale: punteggio / istruzioni
        disegnaHUD(g2);
    }

    // Disegna l'interfaccia utente sovrapposta alla scena
    private void disegnaHUD(Graphics2D g) {
        g.setColor(new Color(180, 180, 220));
        g.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g.drawString("← → per muovere il cannone", 10, 20);
        g.drawString("[Fase 1/4 – struttura base]", 10, 38);
    }

    // ---- KeyListener ----
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> tastoSinistra = true;
            case KeyEvent.VK_RIGHT -> tastoDestra   = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> tastoSinistra = false;
            case KeyEvent.VK_RIGHT -> tastoDestra   = false;
        }
    }

    @Override public void keyTyped(KeyEvent e) { /* non usato */ }
}
