import javax.swing.*;
//  Contiene: FallingBombs (main), PannelloGioco, Cannone,Bomba, Missile


public class FallingBombs extends JFrame {

    //Costanti finestra 
    public static final int LARGHEZZA  = 800;
    public static final int ALTEZZA    = 600;
    public static final int FPS        = 60;
    public static final int RITARDO_MS = 1000 / FPS; // ~16 ms
    PannelloGioco pannello;


    public FallingBombs() {
        super("FallingBombs 💣");

        // Impostazioni base del JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(LARGHEZZA, ALTEZZA);
        setLocationRelativeTo(null); // centra a schermo

        // Aggiunge il pannello di gioco alla finestra
        pannello = new PannelloGioco();
        add(pannello);

        // Rende visibile la finestra
        setVisible(true);

        // Il focus è necessario per ricevere gli eventi tastiera
        pannello.requestFocusInWindow();
    }

    // Punto di ingresso dell'applicazione
    public static void main(String[] args) {
        // Crea la finestra nel thread dedicato alle GUI (EDT)
        SwingUtilities.invokeLater(FallingBombs::new);
    }
}