import java.awt.*;

class Cannone {

    //dimensioni del cannone
    public static final int LARGHEZZA = 60;
    public static final int ALTEZZA   = 35;

    // Posizione corrente 
    public int x;
    public int y;

    public Cannone(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Disegna il cannone come corpo + canna
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // rettangolo principale
        g2.setColor(new Color(60, 160, 60));
        g2.fillRoundRect(x, y + 12, LARGHEZZA, ALTEZZA - 12, 10, 10);

        // Canna cannone 
        int canna_x = x + LARGHEZZA / 2 - 6;
        int canna_y = y;
        g2.setColor(new Color(40, 120, 40));
        g2.fillRoundRect(canna_x, canna_y, 12, 20, 4, 4);

        // Bordo
        g2.setColor(new Color(100, 220, 100));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(x, y + 12, LARGHEZZA, ALTEZZA - 12, 10, 10);
    }
}