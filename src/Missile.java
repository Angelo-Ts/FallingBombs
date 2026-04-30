import java.awt.*; // Aggiunto import fondamentale

class Missile {
    public static final int LARGHEZZA = 1000;
    public static final int ALTEZZA   = 20;

    public int x; 
    public int y; 

    public Missile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255, 220, 50));
        g2.fillRoundRect(x - LARGHEZZA / 2, y, LARGHEZZA, ALTEZZA, 3, 3);

        int[] px = { x - LARGHEZZA / 2, x + LARGHEZZA / 2, x };
        int[] py = { y, y, y - 10 };
        g2.setColor(new Color(255, 140, 30));
        g2.fillPolygon(px, py, 3);

        g2.setColor(new Color(255, 80, 20, 180));
        g2.fillOval(x - 3, y + ALTEZZA - 2, 6, 8);
    }
}