import java.awt.*;

class Bomba {

    public static final int RAGGIO = 20; // raggio del cerchio

    public int x; // centro X
    public int y; // centro Y

    public Bomba(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Disegna la bomba come cerchio rosso con miccia
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Corpo della bomba
        g2.setColor(new Color(200, 50, 50));
        g2.fillOval(x - RAGGIO, y - RAGGIO, RAGGIO * 2, RAGGIO * 2);

        // Bordo luminoso
        g2.setColor(new Color(255, 120, 120));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(x - RAGGIO, y - RAGGIO, RAGGIO * 2, RAGGIO * 2);

        // Miccia (linea curva semplificata)
        g2.setColor(new Color(220, 180, 50));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(x + 8, y - RAGGIO, x + 14, y - RAGGIO - 10);

        // Scintilla in cima alla miccia
        g2.setColor(new Color(255, 240, 100));
        g2.fillOval(x + 12, y - RAGGIO - 13, 5, 5);
    }
}