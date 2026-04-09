class Missile {

    public static final int LARGHEZZA = 8;
    public static final int ALTEZZA   = 20;

    public int x; // centro X
    public int y; // punta superiore

    public Missile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Disegna il missile come rettangolo giallo con punta
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Corpo del missile
        g2.setColor(new Color(255, 220, 50));
        g2.fillRoundRect(x - LARGHEZZA / 2, y, LARGHEZZA, ALTEZZA, 3, 3);

        // Punta triangolare
        int[] px = { x - LARGHEZZA / 2, x + LARGHEZZA / 2, x };
        int[] py = { y, y, y - 10 };
        g2.setColor(new Color(255, 140, 30));
        g2.fillPolygon(px, py, 3);

        // Scia di fuoco in fondo
        g2.setColor(new Color(255, 80, 20, 180));
        g2.fillOval(x - 3, y + ALTEZZA - 2, 6, 8);
    }
}